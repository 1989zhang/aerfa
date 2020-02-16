package com.zhangysh.accumulate.pojo.comm.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;
import java.util.Date;

/**
 * 发布数据对象，对应表： aefcomm_info_publish
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
public class AefcommInfoPublish extends BaseDataObj {

	private static final long serialVersionUID = 1L;

	/** 标题 **/
	private String title;
	/** 信息分类 **/
	private String infoType;
	/** 发布日期 **/
	private Date pubDate;
	/** 状态1有效0无效 **/
	private Long status;
	/** 查看内容访问地址 **/
	private String viewUrl;
	/** 排序号 **/
	private Long orderNo;
	/** 创建人员ID **/
	private Long createUserId;
	/** 创建人员单位ID **/
	private Long createOrgId;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getCreateOrgId() {
		return createOrgId;
	}
	public void setCreateOrgId(Long createOrgId) {
		this.createOrgId = createOrgId;
	}

	@Override
	public String toString() {
		return "AefcommInfoPublish [id=" + id + ",title=" + title + ",infoType=" + infoType + ",pubDate=" + pubDate + ",status=" + status + ",viewUrl=" + viewUrl + ",orderNo=" + orderNo + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",createUserId=" + createUserId + ",createOrgId=" + createOrgId + ",]";
    }
}
