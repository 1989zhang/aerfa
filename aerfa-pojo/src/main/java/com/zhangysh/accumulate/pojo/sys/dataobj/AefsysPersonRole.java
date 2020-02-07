package com.zhangysh.accumulate.pojo.sys.dataobj;

import java.io.Serializable;

/**
 *人员角色对应关系VO
 *@author zhangysh
 *@date 2018年8月27日
 */
public class AefsysPersonRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long personId;
	private Long roleId;
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public String toString() {
		return "AefsysPersonRole [personId=" + personId + ", roleId=" + roleId + "]";
	}

}
