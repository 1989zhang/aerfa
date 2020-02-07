package com.zhangysh.accumulate.pojo.webim.viewobj;

import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;

/*****
 * 即时通webim消息盒子显示对象
 * @author zhangysh
 * @date 2019年10月22日
 *****/
public class AefwebimMsgboxVo {

	private Long id;//消息id
	private Long uid;//消息接收人id
	private String content;//消息内容
	private Long from;//消息来源id
	private Long from_group;//
	private Long type;//消息类型
	private String remark;//备注信息
	private String href;//
	private Long read;//
	private String time;//时间
	private AefwebimFriendVo user;//好友申请好友信息
	
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
	public Long getFrom_group() {
		return from_group;
	}
	public void setFrom_group(Long from_group) {
		this.from_group = from_group;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
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
	public Long getRead() {
		return read;
	}
	public void setRead(Long read) {
		this.read = read;
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
