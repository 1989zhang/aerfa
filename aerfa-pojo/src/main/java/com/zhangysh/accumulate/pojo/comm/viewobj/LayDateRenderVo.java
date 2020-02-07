package com.zhangysh.accumulate.pojo.comm.viewobj;

import java.io.Serializable;
import java.util.Map;

/**
 *初始化laydate的显示模型VO
 */
public class LayDateRenderVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String year;//年
	private String month;//月
	private String monthMin;//月最小日期
	private String monthMax;//月最大日期
	private Map<String,String> mark;//月不上班日期标记
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthMin() {
		return monthMin;
	}
	public void setMonthMin(String monthMin) {
		this.monthMin = monthMin;
	}
	public String getMonthMax() {
		return monthMax;
	}
	public void setMonthMax(String monthMax) {
		this.monthMax = monthMax;
	}
	public Map<String, String> getMark() {
		return mark;
	}
	public void setMark(Map<String, String> mark) {
		this.mark = mark;
	}
	
}
