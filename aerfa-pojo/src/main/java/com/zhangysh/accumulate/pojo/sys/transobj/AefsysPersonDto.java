package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

/**
 * 到后台获取人员等信息的，因为@RequestBody只能获取一个对象，所以拓展此DTO
 * @author zhangysh
 * @date 2018年9月12日
 */
public class AefsysPersonDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private AefsysPerson person;
	private BsTablePageInfo pageInfo;
	
	public AefsysPerson getPerson() {
		return person;
	}
	
	public void setPerson(AefsysPerson person) {
		this.person = person;
	}

	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
