package com.zhangysh.accumulate.pojo.base;

import java.io.Serializable;

/**
 *BaseParmObj是参数配置，键值映射的基类，后面有需要拓展的用extends
 *@author 创建者：zhangysh
 *@date 2018年10月12日
 */
public class BaseParamObj implements Serializable{

	private static final long serialVersionUID = 1L;

	/***参数名称***/
	private String parmCode;
	/***参数值***/
	private String parmValue;
	/***参数说明***/
	private String remark;
	
	public String getParmCode() {
		return parmCode;
	}
	public void setParmCode(String parmCode) {
		this.parmCode = parmCode;
	}
	public String getParmValue() {
		return parmValue;
	}
	public void setParmValue(String parmValue) {
		this.parmValue = parmValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
