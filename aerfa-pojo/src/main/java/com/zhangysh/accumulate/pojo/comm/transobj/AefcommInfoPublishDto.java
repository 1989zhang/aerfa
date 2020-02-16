package com.zhangysh.accumulate.pojo.comm.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 发布数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
public class AefcommInfoPublishDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefcommInfoPublish infoPublish;
	private BsTablePageInfo pageInfo;
	
	public AefcommInfoPublish getInfoPublish() {
		return infoPublish;
	}
	public void setInfoPublish(AefcommInfoPublish infoPublish) {
		this.infoPublish = infoPublish;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
