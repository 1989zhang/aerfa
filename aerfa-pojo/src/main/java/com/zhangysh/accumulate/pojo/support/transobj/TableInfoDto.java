package com.zhangysh.accumulate.pojo.support.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.TableInfo;

/*****
 * 表对象查询传输对象
 * @author zhangysh
 * @date 2019年5月11日
 *****/
public class TableInfoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TableInfo tableInfo;
	private BsTablePageInfo pageInfo;
	
	public TableInfo getTableInfo() {
		return tableInfo;
	}
	public void setTableInfo(TableInfo tableInfo) {
		this.tableInfo = tableInfo;
	}
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
	
	
}
