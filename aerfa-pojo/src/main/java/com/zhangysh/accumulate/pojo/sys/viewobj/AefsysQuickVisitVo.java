package com.zhangysh.accumulate.pojo.sys.viewobj;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;

/**
 * 常用功能快速访问前台展示对象
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
public class AefsysQuickVisitVo extends AefsysQuickVisit{

	private static final long serialVersionUID = 1L;

	//快捷方式管理要展示菜单资源名称
 	/** 资源名称 **/
	private String resourceName;
	/** 资源访问路径 **/
	private String url;

	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
