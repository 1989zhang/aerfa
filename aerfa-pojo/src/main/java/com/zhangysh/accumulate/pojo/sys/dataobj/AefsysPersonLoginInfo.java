package com.zhangysh.accumulate.pojo.sys.dataobj;

import java.io.Serializable;
import java.util.Date;

/**
 *人员登陆信息，登陆状态模型VO
 *@author zhangysh
 *@date 2018年8月27日
 */
public class AefsysPersonLoginInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long personId;
	private String personName;
	private String nickName;
	private String loginVoucher;
	/** 登录客户端IP **/
	private String loginIp;
	/** 登录服务器UI端IP **/
	private String serverUiIp;
	/** 登录地点 **/
	private String loginLocation;
	private Date loginInTime;
	private Date loginOutTime;
	private String browserType;
	private String osType;
	private Long passFailNum;
	private Date lastAccessTime;
	private Long expireTime;
	private Long loginStatus;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getLoginVoucher() {
		return loginVoucher;
	}
	public void setLoginVoucher(String loginVoucher) {
		this.loginVoucher = loginVoucher;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public String getServerUiIp() {
		return serverUiIp;
	}
	public void setServerUiIp(String serverUiIp) {
		this.serverUiIp = serverUiIp;
	}
	public String getLoginLocation() {
		return loginLocation;
	}
	public void setLoginLocation(String loginLocation) {
		this.loginLocation = loginLocation;
	}
	public Date getLoginInTime() {
		return loginInTime;
	}
	public void setLoginInTime(Date loginInTime) {
		this.loginInTime = loginInTime;
	}
	public Date getLoginOutTime() {
		return loginOutTime;
	}
	public void setLoginOutTime(Date loginOutTime) {
		this.loginOutTime = loginOutTime;
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
	public Long getPassFailNum() {
		return passFailNum;
	}
	public void setPassFailNum(Long passFailNum) {
		this.passFailNum = passFailNum;
	}
	public Date getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	public Long getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(Long loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	
	@Override
	public String toString() {
		return "AefsysPersonLoginInfo [id=" + id + ", personId=" + personId + ", personName=" + personName
				+ ", nickName=" + nickName + ", loginVoucher=" + loginVoucher + ", loginIp=" + loginIp
				+ ", loginLocation=" + loginLocation + ", loginInTime=" + loginInTime + ", loginOutTime=" + loginOutTime
				+ ", browserType=" + browserType + ", osType=" + osType + ", passFailNum=" + passFailNum
				+ ", lastAccessTime=" + lastAccessTime + ", expireTime=" + expireTime + ", loginStatus=" + loginStatus
				+ "]";
	}

}
