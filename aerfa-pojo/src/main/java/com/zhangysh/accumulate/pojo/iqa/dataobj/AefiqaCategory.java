package com.zhangysh.accumulate.pojo.iqa.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 知识类别数据对象，对应表： aefiqa_category
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
public class AefiqaCategory extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 分类名称 **/
	private String categoryName;
	/** 所属单位 **/
	private Long belongOrgId;
	/** 排序号 **/
	private Long orderNo;

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getBelongOrgId() {
		return belongOrgId;
	}
	public void setBelongOrgId(Long belongOrgId) {
		this.belongOrgId = belongOrgId;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "AefiqaCategory [id=" + id + ",categoryName=" + categoryName + ",belongOrgId=" + belongOrgId + ",orderNo=" + orderNo + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
