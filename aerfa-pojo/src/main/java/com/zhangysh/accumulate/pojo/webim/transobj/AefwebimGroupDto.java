package com.zhangysh.accumulate.pojo.webim.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 群组数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年10月09日
 */
public class AefwebimGroupDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefwebimGroup group;
	private BsTablePageInfo pageInfo;
	
	public AefwebimGroup getGroup() {
		return group;
	}
	public void setGroup(AefwebimGroup group) {
		this.group = group;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
