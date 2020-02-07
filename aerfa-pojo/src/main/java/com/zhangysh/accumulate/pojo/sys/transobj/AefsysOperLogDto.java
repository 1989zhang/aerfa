package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOperLog;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 操作日志数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年05月20日
 */
public class AefsysOperLogDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysOperLog operLog;
	private BsTablePageInfo pageInfo;
	
	public AefsysOperLog getOperLog() {
		return operLog;
	}
	public void setOperLog(AefsysOperLog operLog) {
		this.operLog = operLog;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
