package com.zhangysh.accumulate.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;


/*****
 * 获取IP相关方法
 * @author zhangysh
 * @date 2019年10月27日
 *****/
public class IpUtil {
	
   /**
	* 获取服务端（接收请求端）IP地址
	* @return 服务端IP地址
	******/
	public static String getHostIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){

        }
	    return "127.0.0.1";
	}

	/**
	 * 获取服务端（接收请求端）主机名
	 * @return 服务端主机名
	 *****/
    public static String getHostName(){
        try{
            return InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException e){

        }
        return "未知";
    }

}
