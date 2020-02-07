package com.zhangysh.accumulate.pojo.sys.dataobj;

import java.io.Serializable;

/**
 *角色对应的资源关系，细化到菜单下的按钮
 *@author zhangysh
 *@date 2018年8月27日
 */
public class AefsysRoleResource implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long roleId;
	private Long resourceId;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	
	@Override
	public String toString() {
		return "AefsysRoleResource [roleId=" + roleId + ", resourceId=" + resourceId + "]";
	}
	
}
