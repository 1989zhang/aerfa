package com.zhangysh.accumulate.pojo.adi.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.adi.dataobj.AefadiDataSource;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 数据源数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年07月08日
 */
public class AefadiDataSourceDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefadiDataSource dataSource;
	private BsTablePageInfo pageInfo;
	
	public AefadiDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(AefadiDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
