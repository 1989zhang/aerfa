package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 数据字典数据对象，对应表： aefsys_data_dic
 * 
 * @author zhangysh
 * @date 2019年04月14日
 */
public class AefsysDataDic extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 类型编码 **/
	private String typeCode;
	/** 类型名称 **/
	private String typeName;
	/** 编码 **/
	private String code;
	/** 全称 **/
	private String fullName;
	/** 简称 **/
	private String shortName;
	/** 排序号 **/
	private Long orderNo;
	/** 是否字典类型1是0否 **/
	private Long isType;
	/** 是否为默认1是0否 **/
	private Long isDefault;
	/** 状态1正常0停用 **/
	private Long status;
	/** 备注描述 **/
	private String remark;

	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getIsType() {
		return isType;
	}
	public void setIsType(Long isType) {
		this.isType = isType;
	}
	public Long getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Long isDefault) {
		this.isDefault = isDefault;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AefsysDataDic [id=" + id + ",typeCode=" + typeCode + ",typeName=" + typeName + ",code=" + code + ",fullName=" + fullName + ",shortName=" + shortName + ",orderNo=" + orderNo + ",isType=" + isType + ",isDefault=" + isDefault + ",status=" + status + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + "]";
    }
}
