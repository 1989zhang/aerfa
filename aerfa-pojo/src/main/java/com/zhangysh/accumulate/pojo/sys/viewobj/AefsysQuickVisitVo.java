package com.zhangysh.accumulate.pojo.sys.viewobj;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;

/**
 * 常用功能快速访问前台展示对象
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
public class AefsysQuickVisitVo extends AefsysQuickVisit{

	private static final long serialVersionUID = 1L;

	//快捷方式管理要展示菜单资源名称
	private AefsysResource resource;

	public AefsysResource getResource() {
		return resource;
	}

	public void setResource(AefsysResource resource) {
		this.resource = resource;
	}
}
