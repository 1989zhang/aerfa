package com.zhangysh.accumulate.pojo.tdm.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 模板填充规则数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmFillRuleDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AeftdmFillRule fillRule;
	private BsTablePageInfo pageInfo;
	
	public AeftdmFillRule getFillRule() {
		return fillRule;
	}
	public void setFillRule(AeftdmFillRule fillRule) {
		this.fillRule = fillRule;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
