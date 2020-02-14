package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import com.zhangysh.accumulate.back.sys.dao.JobLogDao;
import com.zhangysh.accumulate.back.sys.service.IJobLogService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 定时任务日志 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
@Service
public class JobLogServiceImpl implements IJobLogService {

	@Autowired
	private JobLogDao jobLogDao;

    @Override
	public AefsysJobLog getJobLogById(Long id){
	    return jobLogDao.getJobLogById(id);
	}
	
	@Override
	public BsTableDataInfo listPageJobLog(BsTablePageInfo pageInfo,AefsysJobLog jobLog){
	    //pagehelper方法调用
		Page<AefsysJobLog> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		jobLogDao.listJobLog(jobLog);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysJobLog> listBypksJobLog(String ids){
		return jobLogDao.listBypksJobLog(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefsysJobLog> listJobLog(AefsysJobLog jobLog){
		return jobLogDao.listJobLog(jobLog);
	}

	@Override
	public int insertJobLog(AefsysJobLog jobLog){
	    return jobLogDao.insertJobLog(jobLog);
	}
	
	@Override
	public int updateJobLog(AefsysJobLog jobLog){
	    return jobLogDao.updateJobLog(jobLog);
	}
	
	@Override
	public int deleteJobLogById(Long id){
		return jobLogDao.deleteJobLogById(id);
	}
	
	@Override
	public int deleteJobLogByIds(String ids){
		return jobLogDao.deleteJobLogByIds(ConvertUtil.toStrArray(ids));
	}
	
}
