package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 个人联系地址数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
public class AefsysPersonAddressDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysPersonAddress personAddress;
	private BsTablePageInfo pageInfo;
	
	public AefsysPersonAddress getPersonAddress() {
		return personAddress;
	}
	public void setPersonAddress(AefsysPersonAddress personAddress) {
		this.personAddress = personAddress;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
