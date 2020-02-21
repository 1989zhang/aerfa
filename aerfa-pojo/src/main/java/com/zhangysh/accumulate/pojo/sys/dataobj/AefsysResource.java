package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 *系统菜单按钮的资源集合模型VO
 *@author zhangysh
 *@date 2018年8月27日
 */
public class AefsysResource extends BaseDataObj{

	private static final long serialVersionUID = 1L;

	/** 父级id **/
	private Long parentId;
	/** 资源名称 **/
	private String resourceName;
	/** 资源类型scmb **/
	private String resourceType;
	/** 可见状态1显示0隐藏 **/
	private Long status;
	/** 权限控制1是0否 **/
	private Long authority;
	/** 资源权限标示例system:user:add **/
	private String identify;
	/** 访问路径默认# **/
	private String url;
	/** 资源图标 **/
	private String icon;
	/** 排序号 **/
	private Long orderNo;
	/** 备注描述 **/
	private String remark;


	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getAuthority() {
		return authority;
	}
	public void setAuthority(Long authority) {
		this.authority = authority;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AefsysResource [id=" + id + ",parentId=" + parentId + ",resourceName=" + resourceName + ",resourceType=" + resourceType + ",status=" + status + ",authority=" + authority + ",identify=" + identify + ",url=" + url + ",icon=" + icon + ",orderNo=" + orderNo + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
	}
}
