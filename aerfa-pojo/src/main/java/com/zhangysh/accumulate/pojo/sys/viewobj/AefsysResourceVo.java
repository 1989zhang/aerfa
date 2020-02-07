package com.zhangysh.accumulate.pojo.sys.viewobj;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;

/*****
 * 展示修改资源的模型对象，因为多了个父级资源名称，所以拓展此VO
 * @author zhangysh
 * @date 2018年8月29日
 *****/
public class AefsysResourceVo extends AefsysResource{

	private static final long serialVersionUID = 1L;
	
	private String parentName;//父级资源名称

	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	
}
