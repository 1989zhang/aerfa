package com.zhangysh.accumulate.pojo.tdm.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmTemplate;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 模板定义数据传输查询条件和分页对象
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public class AeftdmTemplateDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AeftdmTemplate template;
	private BsTablePageInfo pageInfo;
	
	public AeftdmTemplate getTemplate() {
		return template;
	}
	public void setTemplate(AeftdmTemplate template) {
		this.template = template;
	}
	
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
