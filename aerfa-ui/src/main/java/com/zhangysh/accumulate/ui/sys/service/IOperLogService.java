package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysOperLogDto;


/*****
 * 操作日志相关调用后台方法
 * @author zhangysh
 * @date 2019年05月20日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IOperLogService {

	/****
	 *分页显示操作日志信息
	 *@param aerfatoken token对象
	 *@param OperLogDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/oper_log/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysOperLogDto OperLogDto);

	/****
	 *获取单个操作日志信息
	 *@param aerfatoken token对象
	 *@param id 操作日志的id
	 ***/
	@RequestMapping(value = "/sys/oper_log/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *删除操作日志对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的操作日志ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/oper_log/delete",method = RequestMethod.POST)
	public String deleteOperLogByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

}