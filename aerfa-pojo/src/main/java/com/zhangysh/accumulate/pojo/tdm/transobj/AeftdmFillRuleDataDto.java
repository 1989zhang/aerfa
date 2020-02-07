package com.zhangysh.accumulate.pojo.tdm.transobj;

import java.io.Serializable;

/*****
 * 保存填写的模板填充规则数据传输对象
 * @author zhangysh
 * @date 2019年7月7日
 *****/
public class AeftdmFillRuleDataDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tableData;
	private String locationMark;
	private Long templateId;
	
	public String getTableData() {
		return tableData;
	}
	public void setTableData(String tableData) {
		this.tableData = tableData;
	}
	public String getLocationMark() {
		return locationMark;
	}
	public void setLocationMark(String locationMark) {
		this.locationMark = locationMark;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	
}
