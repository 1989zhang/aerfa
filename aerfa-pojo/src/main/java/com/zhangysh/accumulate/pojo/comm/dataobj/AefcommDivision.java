package com.zhangysh.accumulate.pojo.comm.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 行政区划数据对象，对应表： aefcomm_division
 * 
 * @author zhangysh
 * @date 2019年05月25日
 */
public class AefcommDivision extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 区划代码 **/
	private String code;
	/** 区划名称 **/
	private String name;
	/** 父级区划id **/
	private Long parentId;
	/** 父级区划代码 **/
	private String parentCode;
	/** 父级区划名称 **/
	private String parentName;
	/** 区划简称 **/
	private String shortName;
	/** 区划等级:0国家级;1省级;2市级 ;3区县级;4乡镇街道级 **/
	private Long level;
	/** 标示:1首都;2省会;3市直管区等 **/
	private Long mark;
	/** 备注描述 **/
	private String remark;


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	public Long getMark() {
		return mark;
	}
	public void setMark(Long mark) {
		this.mark = mark;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AefcommDivision [id=" + id + ", code=" + code + ", name=" + name + ", parentId=" + parentId
				+ ", parentCode=" + parentCode + ", parentName=" + parentName + ", shortName=" + shortName + ", level="
				+ level + ", mark=" + mark + ", remark=" + remark + "]";
	}

}
