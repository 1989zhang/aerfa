package com.zhangysh.accumulate.pojo.tdm.transobj;

import java.io.Serializable;

/*****
 * 获取模板转为pdf展示文件内容 的参数传输对象
 * @author zhangysh
 * @date 2019年7月11日
 *****/
public class AeftdmTemplateParmDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 模板id ***/
	private Long templateId;
	/** 请求模板的其他参数**/
	private String requireParm;
	
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getRequireParm() {
		return requireParm;
	}
	public void setRequireParm(String requireParm) {
		this.requireParm = requireParm;
	}
	
	
}
