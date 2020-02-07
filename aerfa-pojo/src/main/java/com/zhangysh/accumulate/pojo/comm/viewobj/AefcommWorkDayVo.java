package com.zhangysh.accumulate.pojo.comm.viewobj;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;

/**
 * 工作日前台展示对象
 * 
 * @author zhangysh
 * @date 2019年06月29日
 */
public class AefcommWorkDayVo extends AefcommWorkDay{

	private static final long serialVersionUID = 1L;
    //定义工作时间影响范围要用
	private String startDate;
	private String endDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
