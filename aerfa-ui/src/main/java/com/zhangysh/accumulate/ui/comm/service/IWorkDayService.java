package com.zhangysh.accumulate.ui.comm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;


/*****
 * 工作日相关调用后台方法
 * @author zhangysh
 * @date 2019年06月29日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IWorkDayService {

	/****
	 * 初始化年所在的月份和日期
	 * @param aerfatoken token对象
	 * @param year 年份 
	 ***/
	@RequestMapping(value = "/comm/work_day/render_date",method = RequestMethod.POST)
	public String getRenderDateByYear(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody int year);

	/**
	 * 点击改变日期的上班不上班状态
	 * @param aerfatoken token对象
	 * @param dateStr 改变状态的日期字符串
	 ****/
	@RequestMapping(value = "/comm/work_day/change_status",method = RequestMethod.POST)
	public String changeWorkDayStatus(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String dateStr);
	
	/****
	 *获取第一个单个工作日对象，第一个为工作时间的定义对象
	 *@param aerfatoken token对象
	 *@param id 第一个定义工作时间的id默认1
	 ***/
	@RequestMapping(value = "/comm/work_day/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	
	/****
	 *保存工作时间定义对象，id为1
	 *@param aerfatoken token对象
	 *@param workDay 要保存的对象
	 ***/
	@RequestMapping(value = "/comm/work_day/save",method = RequestMethod.POST)
	public String saveWorkTime(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefcommWorkDay workDay);
    
}