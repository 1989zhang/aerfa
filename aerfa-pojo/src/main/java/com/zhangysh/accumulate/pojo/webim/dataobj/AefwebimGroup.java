package com.zhangysh.accumulate.pojo.webim.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 群组数据对象，对应表： aefwebim_group
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
public class AefwebimGroup extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 群组名称 **/
	private String groupName;
	/** 群类型1好友群2普通群 **/
	private Long groupType;
	/** 所属拥有者id **/
	private Long ownerId;
	/** 排序号 **/
	private Long orderNo;
	/** 群组头像路径 **/
	private String avatar;
	/** 批准加群:1需要验证0无需验证 **/
	private Long approval;
	/** 上限人数 **/
	private Long limitNumber;
	/** 简介 **/
	private String introduction;


	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Long getGroupType() {
		return groupType;
	}
	public void setGroupType(Long groupType) {
		this.groupType = groupType;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Long getApproval() {
		return approval;
	}
	public void setApproval(Long approval) {
		this.approval = approval;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Long getLimitNumber() {
		return limitNumber;
	}
	public void setLimitNumber(Long limitNumber) {
		this.limitNumber = limitNumber;
	}

	@Override
	public String toString() {
		return "AefwebimGroup [id=" + id + ",groupName=" + groupName + ",groupType=" + groupType + ",ownerId=" + ownerId + ",orderNo=" + orderNo + ",avatar=" + avatar + ",approval=" + approval + ",introduction=" + introduction + ",limitNumber=" + limitNumber + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
	}
}
