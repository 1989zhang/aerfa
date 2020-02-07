package com.zhangysh.accumulate.pojo.iqa.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 知识类别数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
public class AefiqaCategoryDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefiqaCategory category;
	private BsTablePageInfo pageInfo;
	
	public AefiqaCategory getCategory() {
		return category;
	}
	public void setCategory(AefiqaCategory category) {
		this.category = category;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
