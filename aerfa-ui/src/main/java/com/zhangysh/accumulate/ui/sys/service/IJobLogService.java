package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysJobLogDto;


/*****
 * 定时任务日志相关调用后台方法
 * @author zhangysh
 * @date 2020年02月13日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IJobLogService {

	/****
	 * 分页显示定时任务日志信息
	 * @param aerfatoken token对象
	 * @param JobLogDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/job_log/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefsysJobLogDto JobLogDto);

	/****
	 * 获取单个定时任务日志信息
	 * @param aerfatoken token对象
	 * @param id 定时任务日志的id
	 ***/
	@RequestMapping(value = "/sys/job_log/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody Long id);

	/****
	 * 保存新增的定时任务日志信息 
	 * @param aerfatoken token对象
	 * @param jobLog 要保存的定时任务日志对象
	 ***/
	@RequestMapping(value = "/sys/job_log/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefsysJobLog jobLog);

	/****
	 * 删除定时任务日志对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的定时任务日志ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/job_log/delete",method = RequestMethod.POST)
	public String deleteJobLogByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody String ids);

}