package com.zhangysh.accumulate.back.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysJobLogDto;
import com.zhangysh.accumulate.back.sys.service.IJobLogService;

/**
 * 定时任务日志调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
@Controller
@RequestMapping("/sys/job_log")
public class JobLogController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(JobLogController.class);

	@Autowired
	private IJobLogService jobLogService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示定时任务日志信息列表
	 * @param request 请求对象
	 * @param jobLogDto 分页和查询对象
	 * @return 获取到的定时任务日志对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysJobLogDto jobLogDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",jobLogDto.getPageInfo().getPageNum(),jobLogDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=jobLogService.listPageJobLog(jobLogDto.getPageInfo(),jobLogDto.getJobLog());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个定时任务日志,以便修改
	 * @param request 请求对象
     * @param id 定时任务日志主键ID
     * @return 定时任务日志信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle定时任务日志主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(jobLogService.getJobLogById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的定时任务日志信息 
	 * @param request 请求对象
	 * @param jobLog 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveJobLog(HttpServletRequest request,@RequestBody AefsysJobLog jobLog) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( jobLog.getId()!=null ) {//修改方法
			return toHandlerResultStr(jobLogService.updateJobLog(jobLog));
		} else {//新增方法
			jobLog.setCreateTime(DateOperate.getCurrentUtilDate());
			return toHandlerResultStr(jobLogService.insertJobLog(jobLog));
		}
	}
	
	/****
	 * 删除定时任务日志对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的定时任务日志ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="日志信息",menu="任务日志",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteJobLogByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(jobLogService.deleteJobLogByIds(ids));
	}
}
