package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务数据对象，对应表： aefsys_job
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
public class AefsysJob extends BaseDataObj {

	private static final long serialVersionUID = 1L;

	/** 任务名称 **/
	private String jobName;
	/** 调用目标字符串 **/
	private String invokeTarget;
	/** cron执行表达式 **/
	private String cronExpression;
	/** 状态1正常0暂停 **/
	private Long status;
	/** 执行策略1等待触发执行2立即触发执行 **/
	private Long executePolicy;
	/** 备注描述 **/
	private String remark;

	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getInvokeTarget() {
		return invokeTarget;
	}
	public void setInvokeTarget(String invokeTarget) {
		this.invokeTarget = invokeTarget;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getExecutePolicy() {
		return executePolicy;
	}
	public void setExecutePolicy(Long executePolicy) {
		this.executePolicy = executePolicy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AefsysJob [" +
				"id=" + id +
				", jobName='" + jobName + '\'' +
				", invokeTarget='" + invokeTarget + '\'' +
				", cronExpression='" + cronExpression + '\'' +
				", status=" + status +
				", executePolicy=" + executePolicy +
				", remark='" + remark + '\'' +
				", createBy='" + createBy + '\'' +
				", createTime=" + createTime +
				", updateBy='" + updateBy + '\'' +
				", updateTime=" + updateTime +
				']';
	}
}
