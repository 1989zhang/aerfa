package com.zhangysh.accumulate.pojo.webim.viewobj;

import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;

/*****
 * 即时通webim消息盒子显示对象
 * @author zhangysh
 * @date 2019年10月22日
 *****/
public class AefwebimMsgboxVo {

	private Long id;//消息id
	private Long from;//消息来源id
	private Long uid;//消息接收人id
	private String content;//消息内容
	private String type;//消息类型
	/** 备注信息 **/
	private String remark;
	private String href;
	/** 消息状态0未处理1已同意或处理2已拒绝 **/
	protected Long status;
	/** 时间 **/
	private String time;
	/** 好友申请好友信息 **/
	private AefwebimFriendVo user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public AefwebimFriendVo getUser() {
		return user;
	}
	public void setUser(AefwebimFriendVo user) {
		this.user = user;
	}
	
}
