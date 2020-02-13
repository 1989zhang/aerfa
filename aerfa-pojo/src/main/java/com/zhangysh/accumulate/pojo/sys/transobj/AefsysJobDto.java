package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 定时任务数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
public class AefsysJobDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysJob job;
	private BsTablePageInfo pageInfo;
	
	public AefsysJob getJob() {
		return job;
	}
	public void setJob(AefsysJob job) {
		this.job = job;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
