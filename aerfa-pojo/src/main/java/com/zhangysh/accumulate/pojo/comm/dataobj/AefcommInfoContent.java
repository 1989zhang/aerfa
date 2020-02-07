package com.zhangysh.accumulate.pojo.comm.dataobj;

import java.io.Serializable;

/**
 *信息发布大字段内容模型VO
 *@author zhangysh
 *@date 2019年4月4日
 */
public class AefcommInfoContent implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;//id标识同AefcommInfoPublish的id
	private String content;//信息内容
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
