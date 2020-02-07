package com.zhangysh.accumulate.back.support.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.RedisParam;
import com.zhangysh.accumulate.pojo.support.transobj.RedisParamDto;

/**
 *redis管理相关方法类
 *@author 创建者：zhangysh
 *@date 2018年9月16日
 */
@Controller
@RequestMapping("/support/redis")
public class RedisManageController extends BaseController{

	@Autowired
	private IRedisRelatedService redisRelatedService;
	
	/****
	 *获取redis的配置信息
	 *@return 配置转的JSON对象 
	 **/
	@RequestMapping(value="/config",method = RequestMethod.POST)
    @ResponseBody
	public String getRedisConfig(HttpServletRequest request,@RequestBody RedisParamDto redisParamDto) {
		BsTablePageInfo pageInfo=redisParamDto.getPageInfo();
		BsTableDataInfo tableInfo=redisRelatedService.getPageRedisConfig(pageInfo);
		return JSON.toJSONString(tableInfo);
	}
	
	/****
	 *获取redis的运行状态信息，实际也是配置的某些对象
	 *@return 配置转的JSON对象 
	 **/
	@RequestMapping(value="/states",method = RequestMethod.POST)
    @ResponseBody
	public String getRedisStates(HttpServletRequest request) {
		return redisRelatedService.getHandleRedisStates();
	}
	
	/****
	 *获取redis的key和对应的values
	 *@return key和value分页对象转的JSON对象 
	 **/
	@RequestMapping(value="/storage",method = RequestMethod.POST)
    @ResponseBody
	public String getRedisStorage(HttpServletRequest request,@RequestBody RedisParamDto redisParamDto) {
		BsTablePageInfo pageInfo=redisParamDto.getPageInfo();
		RedisParam redisParam=redisParamDto.getRedisParam();
		BsTableDataInfo tableInfo=redisRelatedService.getPageRedisStorage(pageInfo,redisParam);
		return JSON.toJSONString(tableInfo);
	}
	
	/**
	 * 保存redis键值信息 
	 * @param aerfatoken token对象
	 * @param redisParam 保存的键值对象
	 ******/
	@RequestMapping(value="/save_add_storage",method = RequestMethod.POST)
    @ResponseBody
    public String saveAddStorage(HttpServletRequest request,@RequestBody RedisParam redisParam) {
		return toHandlerResultStr(redisRelatedService.saveRedisStorage(redisParam));
	}
	
	/****
	 *获取展示单个redis存储信息
	 *@param request 请求对象
	 *@param redisKey redis的键key
	 *@return 获取到的redis存储对象
	 ****/
	@RequestMapping(value="/storage_single",method = RequestMethod.POST)
    @ResponseBody
	public String getStorageSingle(HttpServletRequest request,@RequestBody String redisKey) {
		return JSON.toJSONString(redisRelatedService.getRedisStorage(redisKey));
	}
	
	/****
	 *删除redis存储对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param redisKeys 要删除的个人redis的key集合
	 ***/
	@RequestMapping(value = "/remove_storage",method = RequestMethod.POST)
	@ResponseBody
	public String deleteRedisStorageByKeys(HttpServletRequest request,@RequestBody String redisKeys) {
		return toHandlerResultStr(redisRelatedService.deleteRedisStorageByKeys(redisKeys));
	}
	
	/**
	 * 清理redis缓存，必须值清理部分key,因为是重要操作所以必须记录日志 
	 * @param aerfatoken token对象
	 ****/
	@Log(system="后台管理系统",module="系统监控",menu="缓存管理",button="清除缓存",saveParam=false)
	@RequestMapping(value = "/clean_up",method = RequestMethod.POST)
	@ResponseBody
	public String cleanUpRedis(HttpServletRequest request) {
		return toHandlerResultStr(redisRelatedService.cleanUpRedis());
	}
}
