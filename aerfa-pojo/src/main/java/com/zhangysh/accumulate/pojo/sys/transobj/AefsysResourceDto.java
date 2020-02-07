package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;

/*****
 * 到后台资源模型vo,因为树形结构不分页所以只有一个对象，列出来方便以后拓展
 * @author zhangysh
 * @date 2019年4月6日
 *****/
public class AefsysResourceDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private AefsysResource resource;

	public AefsysResource getResource() {
		return resource;
	}

	public void setResource(AefsysResource resource) {
		this.resource = resource;
	}

}
