package com.zhangysh.accumulate.pojo.sys.dataobj;

import java.util.Date;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 操作日志模型VO
 * @author zhangysh
 * @date 2018年8月20日
 */
public class AefsysOperLog extends BaseDataObj{

	private static final long serialVersionUID = 1L;

	private String logType;
	private String channel;
	private String logContent;
	private String operName;
	private String orgName;
	private Date operTime;
	private String operUrl;
	private String operSystem;
	private String operModule;
	private String operMenu;
	private String operButton;	
	private String operIp;
	private String operLocation;
	private String operMethod;
	private String operParam;
	
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getOperUrl() {
		return operUrl;
	}
	public void setOperUrl(String operUrl) {
		this.operUrl = operUrl;
	}
	public String getOperSystem() {
		return operSystem;
	}
	public void setOperSystem(String operSystem) {
		this.operSystem = operSystem;
	}
	public String getOperModule() {
		return operModule;
	}
	public void setOperModule(String operModule) {
		this.operModule = operModule;
	}
	public String getOperMenu() {
		return operMenu;
	}
	public void setOperMenu(String operMenu) {
		this.operMenu = operMenu;
	}
	public String getOperButton() {
		return operButton;
	}
	public void setOperButton(String operButton) {
		this.operButton = operButton;
	}
	public String getOperIp() {
		return operIp;
	}
	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}
	public String getOperLocation() {
		return operLocation;
	}
	public void setOperLocation(String operLocation) {
		this.operLocation = operLocation;
	}
	public String getOperMethod() {
		return operMethod;
	}
	public void setOperMethod(String operMethod) {
		this.operMethod = operMethod;
	}
	public String getOperParam() {
		return operParam;
	}
	public void setOperParam(String operParam) {
		this.operParam = operParam;
	}
	
	
	@Override
	public String toString() {
		return "AefsysOperLog [id=" + id + ", logType=" + logType + ", channel=" + channel + ", logContent="
				+ logContent + ", operName=" + operName + ", orgName=" + orgName + ", operTime=" + operTime
				+ ", operUrl=" + operUrl + ", operSystem=" + operSystem + ", operModule=" + operModule + ", operMenu="
				+ operMenu + ", operButton=" + operButton + ", operIp=" + operIp + ", operLocation=" + operLocation
				+ ", operMethod=" + operMethod + ", operParam=" + operParam + "]";
	}
}
