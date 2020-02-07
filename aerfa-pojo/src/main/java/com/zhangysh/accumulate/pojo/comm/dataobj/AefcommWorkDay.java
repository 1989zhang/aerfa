package com.zhangysh.accumulate.pojo.comm.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 工作日数据对象，对应表： aefcomm_work_day
 * id为0的这条数据是工作时间设置
 * @author zhangysh
 * @date 2019年06月29日
 */
public class AefcommWorkDay extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 工作日字符串 **/
	private String workDate;
	/** 上午上班时间 **/
	private String morningDutyTime;
	/** 上午下班时间 **/
	private String morningRushTime;
	/** 下午上班时间 **/
	private String afternoonDutyTime;
	/** 下午下班时间 **/
	private String afternoonRushTime;
	/** 总工作时间（小时） **/
	private Long totleWorkTime;
	/** 是否工作:1上班;0不上班 **/
	private Long workStatus;
	/** 备注 **/
	private String remark;

	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getMorningDutyTime() {
		return morningDutyTime;
	}
	public void setMorningDutyTime(String morningDutyTime) {
		this.morningDutyTime = morningDutyTime;
	}
	public String getMorningRushTime() {
		return morningRushTime;
	}
	public void setMorningRushTime(String morningRushTime) {
		this.morningRushTime = morningRushTime;
	}
	public String getAfternoonDutyTime() {
		return afternoonDutyTime;
	}
	public void setAfternoonDutyTime(String afternoonDutyTime) {
		this.afternoonDutyTime = afternoonDutyTime;
	}
	public String getAfternoonRushTime() {
		return afternoonRushTime;
	}
	public void setAfternoonRushTime(String afternoonRushTime) {
		this.afternoonRushTime = afternoonRushTime;
	}
	public Long getTotleWorkTime() {
		return totleWorkTime;
	}
	public void setTotleWorkTime(Long totleWorkTime) {
		this.totleWorkTime = totleWorkTime;
	}
	public Long getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(Long workStatus) {
		this.workStatus = workStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AefcommWorkDay [id=" + id + ",workDate=" + workDate + ",morningDutyTime=" + morningDutyTime + ",morningRushTime=" + morningRushTime + ",afternoonDutyTime=" + afternoonDutyTime + ",afternoonRushTime=" + afternoonRushTime + ",totleWorkTime=" + totleWorkTime + ",workStatus=" + workStatus + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
