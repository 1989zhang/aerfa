package com.zhangysh.accumulate.ui.support.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.support.dataobj.RedisParam;
import com.zhangysh.accumulate.pojo.support.transobj.RedisParamDto;

/*****
 * redis管理相关
 * @author zhangysh
 * @date 2019年5月18日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IRedisManageService {

	/*****
	 * 获取redis配置相关信息
	 * @param aerfatoken token对象
	 * @param redisParamDto 查询条件和分页对象
	 ***/
	@RequestMapping(value = "/support/redis/config",method = RequestMethod.POST)
	public String getRedisConfig(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody RedisParamDto redisParamDto);
	
	/*****
	 * 获取redis的运行状态信息
	 * @param aerfatoken token对象
	 * @param redisParamDto 查询条件和分页对象
	 ***/
	@RequestMapping(value = "/support/redis/states",method = RequestMethod.POST)
	public String getRedisStatus(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);

	/*****
	 * 获取redis存储的key和对应的values
	 * @param aerfatoken token对象
	 * @param redisParamDto 查询条件和分页对象
	 ***/
	@RequestMapping(value = "/support/redis/storage",method = RequestMethod.POST)
	public String getRedisStorage(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody RedisParamDto redisParamDto);

	/***
	 * 保存填写的redis键值信息 
	 * @param aerfatoken token对象
	 * @param redisParam 保存的键值对象
	 ****/
	@RequestMapping(value = "/support/redis/save_add_storage",method = RequestMethod.POST)
	public String saveAddStorage(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody RedisParam redisParam);

	/***
	 * 根据key获取单个redis存储对象
	 * @param aerfatoken token对象
	 * @param redisKey redis存的键
	 ******/
	@RequestMapping(value = "/support/redis/storage_single",method = RequestMethod.POST)
	public String getRedisStorageSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String redisKey);

	/****
	 *删除redis存储对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param redisKeys 要删除的个人redis的key集合
	 ***/
	@RequestMapping(value = "/support/redis/remove_storage",method = RequestMethod.POST)
	public String deleteRedisStorageByKeys(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String redisKeys);

	/****
	 * 清理redis缓存，必须值清理部分key,因为是重要操作所以必须记录日志
	 ***/
	@RequestMapping(value = "/support/redis/clean_up",method = RequestMethod.POST)
	public String cleanUpRedis(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);
}
