package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

/*****
 * 登录时候输入账号密码等信息对象
 * @author zhangysh
 * @date 2019年3月31日
 *****/
public class AefsysLoginDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String account;
	private String password;
	
	/** 登录请求地址 **/
	private String clientIp;
	/** 登录服务器UI地址 **/
	private String serverIp;
	/** 请求地址浏览器类型 **/
	private String browserType;
	/** 请求操作系统**/
	private String osType;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getBrowserType() {
		return browserType;
	}
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	
}
