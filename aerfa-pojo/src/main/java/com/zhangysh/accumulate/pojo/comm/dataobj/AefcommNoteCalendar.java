package com.zhangysh.accumulate.pojo.comm.dataobj;

import java.util.Date;
import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 日历记事本数据对象，对应表： aefcomm_note_calendar
 * 
 * @author zhangysh
 * @date 2019年10月12日
 */
public class AefcommNoteCalendar extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 个人id **/
	private Long personId;
	/** 标注 **/
	private String mark;
	/** 开始日期 **/
	private Date startDate;
	/** 结束日期 **/
	private Date endDate;
	/** 排序号 **/
	private Long orderNo;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "AefcommNoteCalendar [id=" + id + ",personId=" + personId + ",mark=" + mark + ",startDate=" + startDate + ",endDate=" + endDate + ",orderNo=" + orderNo + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
