package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

import java.io.Serializable;
import java.util.Date;

/**
 * 常用功能快速访问数据对象，对应表： aefsys_quick_visit
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
public class AefsysQuickVisit extends BaseDataObj {

	private static final long serialVersionUID = 1L;

	/** 个人id **/
	private Long personId;
	/** 资源id **/
	private Long resourceId;
	/** 排序号 **/
	private Long orderNo;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
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

	@Override
	public String toString() {
		return "AefsysQuickVisit [id=" + id + ",personId=" + personId + ",resourceId=" + resourceId + ",orderNo=" + orderNo + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
