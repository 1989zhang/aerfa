package com.zhangysh.accumulate.pojo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 *BaseDataObj是各种do的基类
 *主要对象建议都要有这四个字段，标准来源阿里数据库设计规范
 *@author 创建者：zhangysh
 */
public class BaseDataObj implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** id标识 **/
	protected Long id;
	/** 创建者 */
	protected String createBy;
	/** 创建时间 */
	protected Date createTime;
	 /** 更新者 */
	protected String updateBy;
	/** 更新时间 */
	protected Date updateTime;
	
    /** 请求查询的条件参数：放到里面方便@Mapper对应的xml判断且独立开来*/
	protected Map<String, Object> params;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
