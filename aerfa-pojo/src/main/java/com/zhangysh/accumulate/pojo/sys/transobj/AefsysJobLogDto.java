package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 定时任务日志数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
public class AefsysJobLogDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysJobLog jobLog;
	private BsTablePageInfo pageInfo;
	
	public AefsysJobLog getJobLog() {
		return jobLog;
	}
	public void setJobLog(AefsysJobLog jobLog) {
		this.jobLog = jobLog;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
