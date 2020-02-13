package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import com.zhangysh.accumulate.back.sys.util.JobScheduleUtil;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
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
	    return jobDao.insertJob(job);
	}
	
	@Override
	public int updateJob(AefsysJob job){
	    return jobDao.updateJob(job);
	}
	
	@Override
	public int deleteJobById(Long id){
		return jobDao.deleteJobById(id);
	}
	
	@Override
	public int deleteJobByIds(String ids){
		return jobDao.deleteJobByIds(ConvertUtil.toStrArray(ids));
	}
	
}
