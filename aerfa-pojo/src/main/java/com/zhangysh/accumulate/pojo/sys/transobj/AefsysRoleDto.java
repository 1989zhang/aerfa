package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 角色数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年05月16日
 */
public class AefsysRoleDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysRole role;
	private BsTablePageInfo pageInfo;
	
	public AefsysRole getRole() {
		return role;
	}
	public void setRole(AefsysRole role) {
		this.role = role;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
