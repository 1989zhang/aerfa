package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 *系统菜单按钮的资源集合模型VO
 *@author zhangysh
 *@date 2018年8月27日
 */
public class AefsysResource extends BaseDataObj implements Comparable{

	private static final long serialVersionUID = 1L;
	
	private String resourceName;
	private Long parentId;
	private String resourceType;
	private Long status;
	private String identify;
	private String url;
	private String icon;
	private Long orderNo;
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
		return "AefsysResource [id=" + id + ", resourceName=" + resourceName + ", parentId=" + parentId
				+ ", resourceType=" + resourceType + ", status=" + status + ", identify=" + identify
				+ ", url=" + url + ", icon=" + icon + ", orderNo=" + orderNo + ", remark=" + remark + ", createBy="
				+ createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime
				+ "]";
	}

	@Override
	public int compareTo(Object o) {
		AefsysResource resource=(AefsysResource) o;
		return this.getOrderNo().compareTo(resource.getOrderNo());
	}
}
