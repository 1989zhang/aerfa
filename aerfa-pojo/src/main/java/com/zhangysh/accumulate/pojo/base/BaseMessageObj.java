package com.zhangysh.accumulate.pojo.base;

import java.util.Date;

/*****
 * 消息对象基础类
 * @author zhangysh
 * @date 2019年11月3日
 *****/
public class BaseMessageObj extends BaseDataObj{

	private static final long serialVersionUID = 1L;

	/** 消息来源 **/
	protected Long fromPersonId;
	/** 消息目标 **/
	protected Long toPersonId;
	/** 消息类型 **/
	protected String type;
	/** 消息内容 **/
	protected String content;
	/** 消息状态0未处理1已同意或处理2已拒绝 **/
	protected Long status;
	/** 消息发送时间 **/
	protected Date sendTime;
	/** 消息处理时间 **/
	protected Date handleTime;
	
	public Long getFromPersonId() {
		return fromPersonId;
	}
	public void setFromPersonId(Long fromPersonId) {
		this.fromPersonId = fromPersonId;
	}
	public Long getToPersonId() {
		return toPersonId;
	}
	public void setToPersonId(Long toPersonId) {
		this.toPersonId = toPersonId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
}
