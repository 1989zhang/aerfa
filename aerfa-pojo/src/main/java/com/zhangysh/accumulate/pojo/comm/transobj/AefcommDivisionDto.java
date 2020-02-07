package com.zhangysh.accumulate.pojo.comm.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 行政区划数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年05月25日
 */
public class AefcommDivisionDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefcommDivision division;
	private BsTablePageInfo pageInfo;
	
	public AefcommDivision getDivision() {
		return division;
	}
	public void setDivision(AefcommDivision division) {
		this.division = division;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
