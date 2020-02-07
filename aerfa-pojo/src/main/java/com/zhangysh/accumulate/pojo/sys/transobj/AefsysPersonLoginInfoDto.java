package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonLoginInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 个人登录数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年05月20日
 */
public class AefsysPersonLoginInfoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysPersonLoginInfo personLoginInfo;
	private BsTablePageInfo pageInfo;
	
	public AefsysPersonLoginInfo getPersonLoginInfo() {
		return personLoginInfo;
	}
	public void setPersonLoginInfo(AefsysPersonLoginInfo personLoginInfo) {
		this.personLoginInfo = personLoginInfo;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
