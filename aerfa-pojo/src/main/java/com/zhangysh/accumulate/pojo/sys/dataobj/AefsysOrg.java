package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 单位组织信息VO模型,单位组织数据库基础模型
 * @author zhangysh
 * @date 2018年8月20日
 */
public class AefsysOrg extends BaseDataObj{
	
	private static final long serialVersionUID = 1L;
	
	private Long parentId;
	private String fullName;
	private String shortName;
	private Long orderNo;
	private String cardType;
	private String cardNo;
	private String phoneNo;
	private String address;
	private Long status;
	private String remark;
	

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
		return "AefsysOrg [id=" + id + ", parentId=" + parentId + ", fullName=" + fullName + ", shortName=" + shortName
				+ ", orderNo=" + orderNo + ", cardType=" + cardType + ", cardNo=" + cardNo + ", phoneNo=" + phoneNo
				+ ", address=" + address + ", status=" + status + ", remark=" + remark + ", createBy=" + createBy
				+ ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + "]";
	}
}
