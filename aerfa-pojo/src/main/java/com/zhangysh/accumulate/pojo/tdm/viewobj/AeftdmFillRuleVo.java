package com.zhangysh.accumulate.pojo.tdm.viewobj;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;

/**
 * 模板填充规则前台展示对象
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmFillRuleVo extends AeftdmFillRule{

	private static final long serialVersionUID = 1L;
	
    /** 根据字段id展示字段名称 **/
	private String fieldName;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
