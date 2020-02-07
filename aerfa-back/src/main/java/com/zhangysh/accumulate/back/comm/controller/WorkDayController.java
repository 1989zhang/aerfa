package com.zhangysh.accumulate.back.comm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommWorkDayDto;
import com.zhangysh.accumulate.back.comm.service.IWorkDayService;

/**
 * 工作日调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月29日
 */
@Controller
@RequestMapping("/comm/work_day")
public class WorkDayController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(WorkDayController.class);

	@Autowired
	private IWorkDayService workDayService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/***
	 * 初始化年所在的月份和日期
	 * @param year 年份 
	 ***/
	@RequestMapping(value="/render_date",method = RequestMethod.POST)
    @ResponseBody 
	public String getRenderDateByYear(HttpServletRequest request,@RequestBody int year) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		return JSON.toJSONString(ResultVo.success(workDayService.getRenderDateByYear(operPerson,year)));
	}
	
	/**
	 * 点击改变日期的上班不上班状态
	 * @param request token对象
	 * @param dateStr 改变状态的日期字符串
	 ****/
	@RequestMapping(value="/change_status")
    @ResponseBody
	public String changeWorkDayStatus(HttpServletRequest request,@RequestBody String dateStr) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		return JSON.toJSONString(ResultVo.success(workDayService.changeWorkDayStatus(operPerson,dateStr)));
	}

		
	/****
	 * 获取展示工作日信息列表
	 * @param request 请求对象
	 * @param AefcommWorkDayDto 分页和查询对象
	 * @return 获取到的工作日对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefcommWorkDayDto workDayDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",workDayDto.getPageInfo().getPageNum(),workDayDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=workDayService.listPageWorkDay(workDayDto.getPageInfo(),workDayDto.getWorkDay());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个工作日,以便修改
	 * @param request 请求对象
     * @param id 工作日主键ID
     * @return 工作日信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle工作日主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(workDayService.getWorkDayById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的工作日信息 
	 * @param request 请求对象
	 * @param workDay 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveWorkDay(HttpServletRequest request,@RequestBody AefcommWorkDay workDay) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( workDay.getId()!=null ) {//修改方法
		    workDay.setUpdateTime(DateOperate.getCurrentUtilDate());
			workDay.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(workDayService.updateWorkDay(workDay));
		} else {//新增方法
			workDay.setCreateTime(DateOperate.getCurrentUtilDate());
			workDay.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(workDayService.insertWorkDay(workDay));
		}
	}
	
	/****
	 * 删除工作日对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的工作日ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="系统配置",menu="工作日管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteWorkDayByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(workDayService.deleteWorkDayByIds(ids));
	}
}
