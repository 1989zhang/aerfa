package com.zhangysh.accumulate.pojo.sys.dataobj;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务日志数据对象，对应表： aefsys_job_log
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
public class AefsysJobLog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** id标识 **/
	private Long id;
	/** 任务ID **/
	private Long jobId;
	/** 任务名称 **/
	private String jobName;
	/** 调用目标字符串 **/
	private String invokeTarget;
	/** 执行日志 **/
	private String logContent;
	/** 日志类型 **/
	private String logType;
	/** 执行状态1成功0失败 **/
	private Long executeStatus;
	/** 耗时单位毫秒 **/
	private Long costTime;
	/** 创建时间 **/
	private Date createTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
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
	public String getLogContent() {
		return logContent;
	}
	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public Long getExecuteStatus() {
		return executeStatus;
	}
	public void setExecuteStatus(Long executeStatus) {
		this.executeStatus = executeStatus;
	}
	public Long getCostTime() {
		return costTime;
	}
	public void setCostTime(Long costTime) {
		this.costTime = costTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AefsysJobLog [id=" + id + ",jobId=" + jobId + ",jobName=" + jobName + ",invokeTarget=" + invokeTarget + ",logContent=" + logContent + ",logType=" + logType + ",executeStatus=" + executeStatus + ",costTime=" + costTime + ",createTime=" + createTime + ",]";
    }
}
