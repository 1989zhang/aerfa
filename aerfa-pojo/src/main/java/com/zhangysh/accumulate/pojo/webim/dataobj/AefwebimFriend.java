package com.zhangysh.accumulate.pojo.webim.dataobj;
import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 好友数据对象，对应表： aefwebim_friend
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
public class AefwebimFriend extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 个人id **/
	private Long personId;
	/** 好友id **/
	private Long friendId;
	/** 关系状态0刚添加1已通过2黑名单 **/
	private Long relationStatus;
	/** 好友标注 **/
	private String mark;
	/** 所属群组id **/
	private Long groupId;
	/** 排序号 **/
	private Long orderNo;

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getFriendId() {
		return friendId;
	}
	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}
	public Long getRelationStatus() {
		return relationStatus;
	}
	public void setRelationStatus(Long relationStatus) {
		this.relationStatus = relationStatus;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "AefwebimFriend [id=" + id + ",personId=" + personId + ",friendId=" + friendId + ",relationStatus=" + relationStatus + ",mark=" + mark + ",groupId=" + groupId + ",orderNo=" + orderNo + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
