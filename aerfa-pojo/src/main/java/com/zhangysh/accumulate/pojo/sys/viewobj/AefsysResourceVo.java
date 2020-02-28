package com.zhangysh.accumulate.pojo.sys.viewobj;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;

import java.util.List;

/*****
 * 展示修改资源的模型对象，因为多了个父级资源名称，所以拓展此VO
 * @author zhangysh
 * @date 2018年8月29日
 *****/
public class AefsysResourceVo extends AefsysResource{

	private static final long serialVersionUID = 1L;
	
	private String parentName;//父级资源名称
	private List<AefsysResourceVo> children;//首页面要判断后展示子菜单所以拓展此属性
	private Long roleGrant;//角色管理资源，是否和角色相绑定即勾选判断要用

	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<AefsysResourceVo> getChildren() {
		return children;
	}
	public void setChildren(List<AefsysResourceVo> children) {
		this.children = children;
	}

	public Long getRoleGrant() {
		return roleGrant;
	}
	public void setRoleGrant(Long roleGrant) {
		this.roleGrant = roleGrant;
	}
}
