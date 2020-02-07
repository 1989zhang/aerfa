package com.zhangysh.accumulate.common.util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import com.zhangysh.accumulate.common.constant.CacheConstant;

/**
 *http存储工具类：包括处理Cookie和header等
 *@author 创建者：zhangysh
 *@date 2018年9月7日
 */
public class HttpStorageUtil {
    
    /**
     *得到Header里面参数的值 
     * @param request 请求对象
     * @param headerName header里面参数的名称
     * @param isDecoder 是否URLDecoder以UTF-8解码：如果有中文则设置为true，且参数传递前要以URLEncoder.encode("xx","utf-8")编码，不然会乱码。
     * @return header参数值
     ****/
	public static String getHeaderValue(HttpServletRequest request, String headerName, boolean isDecoder) {
		String headerValue=request.getHeader(headerName);
		if(StringUtil.isNull(headerValue)|| headerName == null) {
			return null;
		}
		String retValue = null;
	    try {
	    	if (isDecoder) {
	    		retValue = URLDecoder.decode(headerValue, "UTF-8");
	    	} else {
	    		retValue = headerValue;
	    	}
	    } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;	
		
	}
    
	/*****
	 * 从header或cookie里面获取AERFATOKEN：先从header获取，没有则从cookie获取,再取request的parameter
     * @param request 请求对象
     * @return token的值
	 ***/
    public static String getToken(HttpServletRequest request) {
    	String tokenStr=getHeaderValue(request,CacheConstant.COOKIE_NAME_AERFATOKEN,true);//第一步取header
	    if(StringUtil.isEmpty(tokenStr)) {
	    	tokenStr=getCookieValue(request,CacheConstant.COOKIE_NAME_AERFATOKEN,true);//取cookie
	    	if(StringUtil.isEmpty(tokenStr)) {
	    		tokenStr=request.getParameter(CacheConstant.COOKIE_NAME_AERFATOKEN);//取request参数
	    	}
	    }
	    return tokenStr;
    }
    
    /**
     * 得到Cookie的值
     * @param request 请求对象
     * @param cookieName cookie名称
     * @param isDecoder 是否URLDecoder以UTF-8解码：如果有中文则设置为true，且参数传递前要以URLEncoder.encode("xx","utf-8")编码，不然会乱码。
     * @return cookie值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String retValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        retValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retValue;
    }
    
    /**
     * 设置Cookie的值，带cookie域名
     * @param request 请求对象
     * @param response 响应对象
     * @param cookieName cookie名称
     * @param cookieValue cookie的值
     * @param cookieMaxage cookie生效的最大秒数：大于0才设置生效，否则不设置生效时间就为删除
     * @param isEncoder 是否URLEncoder以UTF-8编码：编码后要接收到要以URLEncoder.decode("xx","utf-8")解码
     */
    public static void setCookieValue(HttpServletRequest request, HttpServletResponse response, String cookieName,String cookieValue,int cookieMaxage,boolean isEncoder) {
    	try {
            if (cookieValue == null) {
                cookieValue = "";
            } else if (isEncoder) {
                cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            }
            Cookie cookie = new Cookie(cookieName, cookieValue);
            if ( cookieMaxage > 0 ) {
                cookie.setMaxAge(cookieMaxage);
            }
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (Exception e) {
        	 e.printStackTrace();
        }
    }
    
    /**
     * 删除Cookie带cookie域名：原理是直接清空
     * @param request 请求对象
     * @param response 响应对象
     * @param cookieName cookie名称
     */
    public static void deleteCookieValue(HttpServletRequest request, HttpServletResponse response,String cookieName) {
    	setCookieValue(request, response, cookieName, "", -1, false);
    }

    /**
     * 根据请求得到域名，未考虑IP的情况
     * @param request 请求对象
     */
    public static final String getDomainName(HttpServletRequest request) {
        String domainName = null;
        String serverName = request.getRequestURL().toString();
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = "." + domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = "." + domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }
        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }
}
