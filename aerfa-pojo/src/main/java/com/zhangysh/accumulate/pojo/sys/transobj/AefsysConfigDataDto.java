package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 配置数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年05月24日
 */
public class AefsysConfigDataDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysConfigData configData;
	private BsTablePageInfo pageInfo;
	
	public AefsysConfigData getConfigData() {
		return configData;
	}
	public void setConfigData(AefsysConfigData configData) {
		this.configData = configData;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
