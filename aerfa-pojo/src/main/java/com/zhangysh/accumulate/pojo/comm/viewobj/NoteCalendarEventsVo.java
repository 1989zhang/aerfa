package com.zhangysh.accumulate.pojo.comm.viewobj;

import java.io.Serializable;
import java.util.Date;

/*****
 * NoteCalendar的FullCalendar对应的events的json数据模型对象
 * @author zhangysh
 * @date 2019年10月13日
 *****/
public class NoteCalendarEventsVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Date start;
	private Date end;
	private Long personId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
}
