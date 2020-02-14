package com.zhangysh.accumulate.ui.sys.controller;

import javax.servlet.http.HttpServletRequest;

import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysJobLogDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysJobLogVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysJobVo;
import com.zhangysh.accumulate.ui.sys.service.IJobLogService;
import com.zhangysh.accumulate.ui.sys.service.IJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOperLog;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonLoginInfo;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysOperLogDto;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonLoginInfoDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysOperLogVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonLoginInfoVo;
import com.zhangysh.accumulate.ui.sys.service.IOperLogService;
import com.zhangysh.accumulate.ui.sys.service.IPersonLoginInfoService;


/*****
 * 日志相关方法,后面把其他日志查看也合并到此处
 * @author zhangysh
 * @date 2018年8月17日
 *****/
@Controller
@RequestMapping("/sys/log")
public class LogController {
	
	private String prefix="/sys/log";//返回界面路径即前缀
	private static final Logger logger=LoggerFactory.getLogger(LogController.class);
	
	@Autowired
	private IOperLogService operLogService;
	@Autowired
	private IPersonLoginInfoService personLoginInfoService;
	@Autowired
	private IJobService jobService;
	@Autowired
	private IJobLogService jobLogService;

	/**
	 *跳转到sys下操作日志页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequestMapping(value="/to_oper_log")
	public String toSysOperLog(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/oper_log";
	}
	/****
	 *获取展示操作日志列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/oper_log_list")
    @ResponseBody
	public String getOperLogList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysOperLog operLog) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysOperLogDto operLogDto=new AefsysOperLogDto();
		operLogDto.setPageInfo(pageInfo);operLogDto.setOperLog(operLog);
		return operLogService.getList(aerfatoken,operLogDto);
	}
	/**
	 *跳转到查看操作日志详细页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequestMapping(value="/to_view_oper_log/{id}")
	public String toViewOperLog(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retOperLogStr=operLogService.getSingle(aerfatoken, id);
		AefsysOperLogVo operLog=JSON.parseObject(retOperLogStr,AefsysOperLogVo.class);
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("operLog",operLog);
		return prefix+"/view_oper_log";
	}
	/***
	 *删除操作日志对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove_oper_log/{ids}")
    @ResponseBody
    public String removeOperLog(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){
		String aerfatoken=HttpStorageUtil.getToken(request);
		return operLogService.deleteOperLogByIds(aerfatoken, ids);
    }
	
	
	/**
	 *跳转到sys个人登录日志信息页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的个人登录页面
	 ****/
	@RequestMapping(value="/to_login_info")
	public String toSysLoginInfo(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/login_info";
	}
	
	/****
	 *获取展示个人登录日志信息列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/login_info_list")
    @ResponseBody
	public String getLoginInfoList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysPersonLoginInfo personLoginInfo) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysPersonLoginInfoDto personLoginInfoDto=new AefsysPersonLoginInfoDto();
		personLoginInfoDto.setPageInfo(pageInfo);personLoginInfoDto.setPersonLoginInfo(personLoginInfo);
		return personLoginInfoService.getList(aerfatoken,personLoginInfoDto);
	}
	/**
	 *跳转到查看登录日志详细页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequestMapping(value="/to_view_login_info/{id}")
	public String toViewLoginInfo(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retLoginInfoStr=personLoginInfoService.getSingle(aerfatoken, id);
		AefsysPersonLoginInfoVo loginInfo=JSON.parseObject(retLoginInfoStr,AefsysPersonLoginInfoVo.class);
		loginInfo.setLoginInTimeStr(DateOperate.UtilDatetoString(loginInfo.getLoginInTime(),UtilConstant.MOST_MIDDLE_DATE));
		loginInfo.setLoginOutTimeStr(DateOperate.UtilDatetoString(loginInfo.getLoginOutTime(),UtilConstant.MOST_MIDDLE_DATE));
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("loginInfo",loginInfo);
		return prefix+"/view_login_info";
	}


	/**
	 * 跳转到sys定时任务日志页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的定时任务日志页面
	 ****/
	@RequestMapping(value="/to_job_log")
	public String toSysJobLog(HttpServletRequest request, ModelMap modelMap,Long jobId) {
		AefsysJobVo sysJob=new AefsysJobVo();
		if(StringUtil.isNotNull(jobId)){
			String aerfatoken=HttpStorageUtil.getToken(request);
			String jobJsonStr=jobService.getSingle(aerfatoken,jobId);
			sysJob=JSON.parseObject(jobJsonStr,AefsysJobVo.class);
		}
		modelMap.addAttribute("sysJob", sysJob);
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/job_log";
	}
	/****
	 * 获取展示定时任务日志列表，且分页显示
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/job_log_list")
	@ResponseBody
	public String getJobLogList(HttpServletRequest request, ModelMap modelMap, BsTablePageInfo pageInfo, AefsysJobLog jobLog) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysJobLogDto jobLogDto=new AefsysJobLogDto();
		jobLogDto.setPageInfo(pageInfo);jobLogDto.setJobLog(jobLog);
		return jobLogService.getList(aerfatoken,jobLogDto);
	}
	/**
	 *跳转到查看任务日志详细页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequestMapping(value="/to_view_job_log/{id}")
	public String toViewJobLog(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retJobLogStr=jobLogService.getSingle(aerfatoken, id);
		AefsysJobLogVo jobLog=JSON.parseObject(retJobLogStr,AefsysJobLogVo.class);
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("jobLog",jobLog);
		return prefix+"/view_job_log";
	}
	/***
	 *删除任务日志对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove_job_log/{ids}")
	@ResponseBody
	public String removeJobLog(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){
		String aerfatoken=HttpStorageUtil.getToken(request);
		return jobLogService.deleteJobLogByIds(aerfatoken, ids);
	}
}
