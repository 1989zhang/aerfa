package com.zhangysh.accumulate.pojo.sys.viewobj;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;

/*****
 * 展示修改单位的模型对象，因为多了个父级单位名称，所以拓展此VO
 * @author zhangysh
 * @date 2018年8月29日
 *****/
public class AefsysOrgVo extends AefsysOrg{

	private static final long serialVersionUID = 1L;
	
	private String parentFullName;//父级单位名称
	
	public String getParentFullName() {
		return parentFullName;
	}

	public void setParentFullName(String parentFullName) {
		this.parentFullName = parentFullName;
	}
	
}
