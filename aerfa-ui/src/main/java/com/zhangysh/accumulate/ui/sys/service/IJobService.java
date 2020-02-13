package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysJobDto;


/*****
 * 定时任务相关调用后台方法
 * @author zhangysh
 * @date 2020年02月13日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IJobService {

	/****
	 * 分页显示定时任务信息
	 * @param aerfatoken token对象
	 * @param JobDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/job/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefsysJobDto JobDto);

	/****
	 * 获取单个定时任务信息
	 * @param aerfatoken token对象
	 * @param id 定时任务的id
	 ***/
	@RequestMapping(value = "/sys/job/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody Long id);

	/****
	 * 保存新增的定时任务信息 
	 * @param aerfatoken token对象
	 * @param job 要保存的定时任务对象
	 ***/
	@RequestMapping(value = "/sys/job/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefsysJob job);

	/****
	 * 删除定时任务对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的定时任务ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/job/delete",method = RequestMethod.POST)
	public String deleteJobByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody String ids);

}