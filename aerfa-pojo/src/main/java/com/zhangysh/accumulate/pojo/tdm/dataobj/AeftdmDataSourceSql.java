package com.zhangysh.accumulate.pojo.tdm.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 数据源SQL定义数据对象，对应表： aeftdm_data_source_sql
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmDataSourceSql extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 模板id **/
	private Long templateId;
	/** 数据源名称和说明用途一样 **/
	private String explainName;
	/** 定义sql内容 **/
	private String sqlText;
	/** 填充类型用来控制是否自动往下加行等 **/
	private String fillType;
	/** 备注描述 **/
	private String remark;

	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getExplainName() {
		return explainName;
	}
	public void setExplainName(String explainName) {
		this.explainName = explainName;
	}
	public String getSqlText() {
		return sqlText;
	}
	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}
	public String getFillType() {
		return fillType;
	}
	public void setFillType(String fillType) {
		this.fillType = fillType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String toString() {
		return "AeftdmDataSourceSql [id=" + id + ",templateId=" + templateId + ",explainName=" + explainName + ",sqlText=" + sqlText + ",fillType=" + fillType + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
