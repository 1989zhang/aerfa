package com.zhangysh.accumulate.pojo.comm.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 工作日数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年06月29日
 */
public class AefcommWorkDayDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefcommWorkDay workDay;
	private BsTablePageInfo pageInfo;
	
	public AefcommWorkDay getWorkDay() {
		return workDay;
	}
	public void setWorkDay(AefcommWorkDay workDay) {
		this.workDay = workDay;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
