package com.zhangysh.accumulate.pojo.tdm.dataobj;

import java.util.Date;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 数据源字段映射数据对象，对应表： aeftdm_data_source_field
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmDataSourceField extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 模板id **/
	private Long templateId;
	/** 数据源SQLid **/
	private Long sqlId;
	/** sql执行出来的key名称 **/
	private String fieldName;
	/** sql执行出来的key别名，方便对应显示到模板上，与模板对应 **/
	private String fieldAlias;
	/** 查询出来key的序号，排个序目前没啥用 **/
	private Long indexNumber;


	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Long getSqlId() {
		return sqlId;
	}
	public void setSqlId(Long sqlId) {
		this.sqlId = sqlId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldAlias() {
		return fieldAlias;
	}
	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}
	public Long getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(Long indexNumber) {
		this.indexNumber = indexNumber;
	}

	@Override
	public String toString() {
		return "AeftdmDataSourceField [id=" + id + ",templateId=" + templateId + ",sqlId=" + sqlId + ",fieldName=" + fieldName + ",fieldAlias=" + fieldAlias + ",indexNumber=" + indexNumber + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
