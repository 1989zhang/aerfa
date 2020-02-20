package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import com.zhangysh.accumulate.back.sys.util.JobScheduleUtil;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import com.zhangysh.accumulate.back.sys.dao.JobDao;
import com.zhangysh.accumulate.back.sys.service.IJobService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 定时任务 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
@Service
public class JobServiceImpl implements IJobService {

	@Autowired
	private JobDao jobDao;
	@Autowired
	private Scheduler scheduler;

	private static final Logger logger= LoggerFactory.getLogger(JobServiceImpl.class);

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() throws Exception
	{
		AefsysJob searchSysJob=new AefsysJob();
		List<AefsysJob> sysJobList = jobDao.listJob(searchSysJob);
		for (AefsysJob job : sysJobList) {
			CronTrigger cronTrigger = JobScheduleUtil.getCronTrigger(scheduler, job.getId());
			// 如果不存在，则创建
			if (cronTrigger == null) {
				JobScheduleUtil.createScheduleJob(scheduler, job);
			} else {
				JobScheduleUtil.updateScheduleJob(scheduler, job);
			}
		}
	}



	@Override
	public AefsysJob getJobById(Long id){
	    return jobDao.getJobById(id);
	}
	
	@Override
	public BsTableDataInfo listPageJob(BsTablePageInfo pageInfo,AefsysJob job){
	    //pagehelper方法调用
		Page<AefsysJob> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		jobDao.listJob(job);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysJob> listBypksJob(String ids){
		return jobDao.listBypksJob(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefsysJob> listJob(AefsysJob job){
		return jobDao.listJob(job);
	}

	@Override
	public int insertJob(AefsysJob job){
		int rows = jobDao.insertJob(job);
		if (rows > 0) {
			try {
				JobScheduleUtil.createScheduleJob(scheduler, job);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("insertJob后创建任务失败：{}"+e.getMessage());
			}
		}
		return rows;
	}
	
	@Override
	public int updateJob(AefsysJob job){
		int rows = jobDao.updateJob(job);
		if (rows > 0) {
			try {
				updateSchedulerJob(job);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("insertJob后创建任务失败：{}"+e.getMessage());
			}
		}
	    return rows;
	}
	
	@Override
	@Transactional
	public int deleteJobById(Long id){
		int rows = jobDao.deleteJobById(id);
		if (rows > 0) {
			try {
				scheduler.deleteJob(JobScheduleUtil.getJobKey(id));
			}catch(Exception e){
				e.printStackTrace();
				logger.error("insertJob后创建任务失败：{}"+e.getMessage());
			}
		}
		return rows;
	}
	
	@Override
	public int deleteJobByIds(String ids){
		Long[] jobIds = ConvertUtil.toLongArray(ids);
		for (Long jobId : jobIds) {
			deleteJobById(jobId);
		}
		return jobIds.length;
	}

	/**
	 * 更新执行中的任务
	 *
	 * @param job 任务对象
	 ***/
	public void updateSchedulerJob(AefsysJob job) throws Exception {
		Long jobId = job.getId();
		// 判断是否存在
		JobKey jobKey = JobScheduleUtil.getJobKey(jobId);
		if (scheduler.checkExists(jobKey)) {
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(jobKey);
		}
		JobScheduleUtil.createScheduleJob(scheduler, job);
	}

	@Override
	public boolean checkExpressionValid(String cronExpression){
		return JobScheduleUtil.isValid(cronExpression);
	}

	@Override
	public boolean runOnce(Long id){
		try {
			AefsysJob propertiesJob = getJobById(id);
			// 参数
			JobDataMap dataMap = new JobDataMap();
			dataMap.put(SysDefineConstant.JOB_EXECUTE_PROPERTIES, propertiesJob);
			scheduler.triggerJob(JobScheduleUtil.getJobKey(id), dataMap);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("runJob执行任务失败：{}"+e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean changeStatus(Long id,Long status){
		AefsysJob beforeJob = getJobById(id);
		beforeJob.setStatus(status);
		int rows = jobDao.updateJob(beforeJob);
		if (rows > 0) {
			try {
				//暂停任务
				if(SysDefineConstant.DIC_USEABLE_STATUS_INVALID.equals(status)){
					scheduler.pauseJob(JobScheduleUtil.getJobKey(id));
				}else if(SysDefineConstant.DIC_USEABLE_STATUS_VALID.equals(status)){//恢复任务
					scheduler.resumeJob(JobScheduleUtil.getJobKey(id));
				}
			} catch (Exception e){
				e.printStackTrace();
				logger.error("changeStatus执行任务失败：{}"+e.getMessage());
				return false;
			}
		}
		return true;
	}
}
