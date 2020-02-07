package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 人员信息VO模型,人员数据库基础模型
 * @author zhangysh
 * @date 2018年8月20日
 */
public class AefsysPerson extends BaseDataObj{
	
	private static final long serialVersionUID = 1L;
	
	private Long orgId;
	private String account;
	private String password;
	private String salt;
	private String phoneNo;
	private String email;
	private String personType;
	private String personName;
	private String personSex;
	private String nickName;
	private String headPic;
	private String cardType;
	private String cardNo;
	private Long orderNo;
	private Long status;
	private String remark;
	

	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonSex() {
		return personSex;
	}
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadPic() {
		return headPic;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
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
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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
		return "AefsysPerson [id=" + id + ", orgId=" + orgId + ", account=" + account + ", password=" + password
				+ ", salt=" + salt + ", phoneNo=" + phoneNo + ", email=" + email + ", personType=" + personType
				+ ", personName=" + personName + ", personSex=" + personSex + ", nickName=" + nickName + ", headPic="
				+ headPic + ", cardType=" + cardType + ", cardNo=" + cardNo + ", orderNo=" + orderNo + ", status="
				+ status + ", remark=" + remark + ", createBy=" + createBy + ", createTime=" + createTime
				+ ", updateBy=" + updateBy + ", updateTime=" + updateTime + "]";
	}
}
