package com.zhangysh.accumulate.pojo.tdm.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 数据源SQL定义数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmDataSourceSqlDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AeftdmDataSourceSql dataSourceSql;
	private BsTablePageInfo pageInfo;
	
	public AeftdmDataSourceSql getDataSourceSql() {
		return dataSourceSql;
	}
	public void setDataSourceSql(AeftdmDataSourceSql dataSourceSql) {
		this.dataSourceSql = dataSourceSql;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
