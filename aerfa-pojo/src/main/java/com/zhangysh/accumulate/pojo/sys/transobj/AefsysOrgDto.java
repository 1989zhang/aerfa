package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;

/**
 * 到后台组织部门等信息的，因为树形结构不分页所以只有一个对象，列出来方便以后拓展
 * @author zhangysh
 * @date 2018年9月12日
 */
public class AefsysOrgDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private AefsysOrg org;

	public AefsysOrg getOrg() {
		return org;
	}

	public void setOrg(AefsysOrg org) {
		this.org = org;
	}
	
}
