package com.zhangysh.accumulate.pojo.comm.dataobj;

import java.io.Serializable;

/**
 *信息发布大字段内容模型VO
 *@author zhangysh
 *@date 2019年4月4日
 */
public class AefcommInfoContent implements Serializable{

	private static final long serialVersionUID = 1L;

	/** id标识 **/
	private Long id;
	/** publish的id **/
	private Long publishId;
	/** 信息内容 **/
	private String content;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPublishId() {
		return publishId;
	}
	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "AefcommInfoContent [id=" + id + ",publishId=" + publishId + ",content=" + content + ",]";
	}
	
}
