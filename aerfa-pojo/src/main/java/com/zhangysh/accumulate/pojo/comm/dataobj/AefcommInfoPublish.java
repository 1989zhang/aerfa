package com.zhangysh.accumulate.pojo.comm.dataobj;

import java.util.Date;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 *信息发布模型VO
 *@author zhangysh
 *@date 2019年4月4日
 */
public class AefcommInfoPublish  extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	private String title;//标题
	private String infoType;//信息分类
	private Date pubDate;//发布日期
	private Long status;//状态1有效0无效
	private String viewUrl;//查看内容的html访问地址
	private Long orderNo;//排序号

	
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
	
}
