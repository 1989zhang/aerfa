package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 数据权限数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
public class AefsysDataPermissionDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysDataPermission dataPermission;
	private BsTablePageInfo pageInfo;
	
	public AefsysDataPermission getDataPermission() {
		return dataPermission;
	}
	public void setDataPermission(AefsysDataPermission dataPermission) {
		this.dataPermission = dataPermission;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
