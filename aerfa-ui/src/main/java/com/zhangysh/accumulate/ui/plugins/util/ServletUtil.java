package com.zhangysh.accumulate.ui.plugins.util;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 客户端工具类
 *
 * @author zhangysh
 * @date 2020年02月17日
 */
public class ServletUtil {

    /**
     * 获取客户端操作系统
     * ***/
    public static String getClientOperatingSystem(){
        return getUserAgent().getOperatingSystem().getName();
    }

    /**
     * 获取客户端浏览器
     * ***/
    public static String getClientBrowser(){
        return getUserAgent().getBrowser().getName();
    }

    /**
     * 获取用户代理对象
     **/
    public static UserAgent getUserAgent(){
        return UserAgent.parseUserAgentString(getRequest().getHeader("User-Agent"));
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /***
     * 获取持有上下文的Request容器参数
     **/
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }


    /**
     * 获取客户端（发送请求端）IP地址
     * @return 客户端IP地址
     ****/
    public static String getClientIpAddr(){
        HttpServletRequest request=getRequest();
        if (request == null){
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
