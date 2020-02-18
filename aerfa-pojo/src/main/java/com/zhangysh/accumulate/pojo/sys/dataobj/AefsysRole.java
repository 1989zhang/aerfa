package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 角色数据对象，对应表： aefsys_role
 * 
 * @author zhangysh
 * @date 2019年05月16日
 */
public class AefsysRole extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 角色名称 **/
	private String roleName;
	/** 标识编码 **/
	private String roleCode;
	/** 状态1正常0停用 **/
	private Long status;
	/** 排序号 **/
	private Long orderNo;
	/** 备注描述 **/
	private String remark;


	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	@Override
	public String toString() {
		return "AefsysRole [id=" + id + ", roleName=" + roleName + ", roleCode=" + roleCode + ", status=" + status
				+ ", orderNo=" + orderNo + ", remark=" + remark + ", createBy=" + createBy + ", createTime="
				+ createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + "]";
	}
}
