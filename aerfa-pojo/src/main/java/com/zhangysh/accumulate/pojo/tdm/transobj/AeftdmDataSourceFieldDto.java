package com.zhangysh.accumulate.pojo.tdm.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 数据源字段映射数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmDataSourceFieldDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AeftdmDataSourceField dataSourceField;
	private BsTablePageInfo pageInfo;
	
	public AeftdmDataSourceField getDataSourceField() {
		return dataSourceField;
	}
	public void setDataSourceField(AeftdmDataSourceField dataSourceField) {
		this.dataSourceField = dataSourceField;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
