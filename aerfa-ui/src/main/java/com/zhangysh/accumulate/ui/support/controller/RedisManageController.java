package com.zhangysh.accumulate.ui.support.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.support.dataobj.RedisParam;
import com.zhangysh.accumulate.pojo.support.transobj.RedisParamDto;
import com.zhangysh.accumulate.ui.support.service.IRedisManageService;

/**
 *redis管理相关方法类
 *@author 创建者：zhangysh
 *@date 2018年9月16日
 */
@Controller
@RequestMapping("/support/redis")
public class RedisManageController {
	
	private String prefix="/support/redis";//返回界面路径即前缀
	private static final Logger logger=LoggerFactory.getLogger(RedisManageController.class);
	
	@Resource
	private IRedisManageService redisManageService;
	
	/****
	 *跳转到redis系统配置界面
	 *@return templates下的页面
	 **/
	@RequestMapping(value="/to_config")
	public String toRedisConfig(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/config";
	}
	
	/****
	 *跳转到redis运行状态界面
	 *@return templates下的页面
	 **/
	@RequestMapping(value="/to_status")
	public String toRedisStatus(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/status";
	}
	
	/****
	 *跳转到redis的键值管理列表页面
	 *@return templates下的页面
	 **/
	@RequestMapping(value="/to_storage")
	public String toRedisStorage(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/storage";
	}

	/****
	 *获取redis的配置信息
	 *@return 配置转的JSON对象 
	 **/
	@RequestMapping(value="/config")
    @ResponseBody
	public String getRedisConfig(HttpServletRequest request,ModelMap modelMap,BsTablePageInfo pageInfo) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		RedisParamDto redisParamDto=new RedisParamDto();
		redisParamDto.setPageInfo(pageInfo);
		return redisManageService.getRedisConfig(aerfatoken, redisParamDto);
	}
	
	/****
	 *获取redis的运行状态信息，实际也是配置的某些对象
	 *@return 配置转的JSON对象 
	 **/
	@RequestMapping(value="/status",method = RequestMethod.POST)
    @ResponseBody
	public String getRedisStatus(HttpServletRequest request,ModelMap modelMap,String lastData) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String redisStatusStr=redisManageService.getRedisStatus(aerfatoken);
		List<RedisParam> redisParamList=JSON.parseArray(redisStatusStr, RedisParam.class);
		Map<String,Object> retMap=new HashMap<String,Object>();
		Integer currentProcessData=0;
		Integer lastProcessData=Integer.parseInt(StringUtil.isEmpty(lastData)?"0":lastData);
		for(RedisParam redisParam:redisParamList) {
			//记录最新的process数量
			if(MarkConstant.MARK_REDIS_STATUS_TOTAL_COMMANDS_PROCESSED.equals(redisParam.getParmCode())) {
				currentProcessData=Integer.parseInt(redisParam.getParmValue());
			}
			retMap.put(redisParam.getParmCode(), redisParam.getParmValue());
		}
		//有一个QPS需要计算出来原理：后台process减前台process由于5s刷新所以乘以12就是一分钟执行命令数，后台为规范就前台算。
		//为了防止突然变大，排除为0的情况
		Integer qps=0;
		if(currentProcessData>0&&lastProcessData>0) {qps=(currentProcessData-lastProcessData)*12;}
		retMap.put(MarkConstant.MARK_REDIS_STATUS_QPS,qps);
		return JSON.toJSONString(retMap);
	}
	
	/****
	 *获取redis的key和对应的values
	 *@return key和value分页对象转的JSON对象 
	 **/
	@RequestMapping(value="/storage",method = RequestMethod.POST)
    @ResponseBody
	public String getRedisStorage(HttpServletRequest request,ModelMap modelMap,BsTablePageInfo pageInfo,RedisParam redisParam) {
		logger.info("key:"+redisParam.getParmCode());
		String aerfatoken=HttpStorageUtil.getToken(request);
		RedisParamDto redisParamDto=new RedisParamDto();
		redisParamDto.setPageInfo(pageInfo);
		redisParamDto.setRedisParam(redisParam);
		return redisManageService.getRedisStorage(aerfatoken, redisParamDto);
	}
	

	/***
	 *跳转到redis保存的键值管理新增界面 
	 ***/
	@RequestMapping(value="/to_add_storage")
	public String toAddStorage(HttpServletRequest request, ModelMap modelMap) {
		modelMap.put("prefix", prefix);
		return prefix+"/add_storage";
	}
	
	/***
	 *保存填写的redis键值信息 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param redisParam 保存的对象
	 ******/
	@RequestMapping(value="/save_add_storage")
    @ResponseBody
    public String saveAddStorage(HttpServletRequest request, ModelMap modelMap,RedisParam redisParam) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return redisManageService.saveAddStorage(aerfatoken,redisParam);
    }
	/***
	 *跳转到redis保存的键值管理修改界面 
	 ***/
	@RequestMapping(value="/to_edit_storage/{id}")
	public String toEditStorage(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") String redisKey) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retRedisStorageStr=redisManageService.getRedisStorageSingle(aerfatoken,redisKey);
		RedisParam storage=JSON.parseObject(retRedisStorageStr,RedisParam.class);
		modelMap.put("prefix", prefix);
		modelMap.put("storage", storage);
		return prefix+"/edit_storage";
	}
	
	/***
	 *删除redis存储对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的redis的ids集合即keys集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove_storage/{ids}")
    @ResponseBody
    public String removeStorage(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String redisKeys){
		String aerfatoken=HttpStorageUtil.getToken(request);
		return redisManageService.deleteRedisStorageByKeys(aerfatoken, redisKeys);
	}
	
	/****
	 * 清理redis缓存，必须值清理部分key,因为是重要操作所以必须记录日志
	 * @param request 请求对象
	 ***/
	@RequestMapping(value="/clean_up")
    @ResponseBody
    public String cleanUpRedis(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return redisManageService.cleanUpRedis(aerfatoken);
	}
}
