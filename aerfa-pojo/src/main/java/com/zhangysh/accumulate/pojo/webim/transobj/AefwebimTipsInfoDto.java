package com.zhangysh.accumulate.pojo.webim.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 提示消息数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
public class AefwebimTipsInfoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefwebimTipsInfo tipsInfo;
	private BsTablePageInfo pageInfo;
	
	public AefwebimTipsInfo getTipsInfo() {
		return tipsInfo;
	}
	public void setTipsInfo(AefwebimTipsInfo tipsInfo) {
		this.tipsInfo = tipsInfo;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
