package com.zhangysh.accumulate.back.support.service;

import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.TokenModel;
/****
 * 用到redis相关的方法接口
 * @author zhangysh
 * @date 2018年9月12日
 */
import com.zhangysh.accumulate.pojo.base.BaseParamObj;
import com.zhangysh.accumulate.pojo.support.dataobj.RedisParam;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
public interface IRedisRelatedService {

	/***
	 *设置aerfatoken到redis中，值以json形式存储
	 * @return 
	 ****/
	void setTokenInfo(String token,TokenModel tokenModel);
	
	/*****
	 *根据token获取人员存在redis的TokenModel信息,内含Map session信息
	 *@param token str的token对象
	 *@return Map 对象，包括个人对象,单位对象等
	 ***/
	TokenModel getTokenModelByToken(String token);
	
	/*****
	 *根据token获取人员存在redis的session信息
	 *@param token str的token对象
	 *@return Map 对象，包括个人对象,单位对象等
	 ***/
	Map<String, Object> getRedisSessionMapByToken(String token);
	
	/*****
	 *根据token获取人员存在redis的session里面的个人信息
	 *@param token str的token对象
	 *@return AefsysPerson 个人对象
	 ***/
	AefsysPerson getRedisSessionPersonByToken(String token);
	
	/*****
	 *根据token获取人员存在redis的session里面的人所属单位信息
	 *@param token str的token对象
	 *@return AefsysOrg 单位对象
	 ***/
	AefsysOrg getRedisSessionOrgByToken(String token);
	
	/****
	 *获取redis的配置信息，并返回分页类型的数据 
	 *@param pageInfo 分页参数对象
	 *@return BsTableDataInfo分页结果对象
	 ***/
	BsTableDataInfo getPageRedisConfig(BsTablePageInfo pageInfo);
	
	/***
	 *获取redis运行状态相关的参数属性
	 *包含：内存、cpu、命中等信息
	 *@return  List集合包含相关属性
	 ****/
	List<BaseParamObj> getRedisStates();
	
	/***
	 *对redis状态相关的参数属性，进行运算和封装，方便前台展示
	 *@return json类型的字符串
	 ***/
	String getHandleRedisStates();
	
	/****
	 *获取redis的存储键值信息，并返回分页类型的数据 
	 *@param pageInfo 分页参数对象
	 *@param redisParam key存储的搜索对象
	 *@return BsTableDataInfo分页结果对象
	 ***/
	BsTableDataInfo getPageRedisStorage(BsTablePageInfo pageInfo,RedisParam redisParam);
	
	/**
	 * 保存redis键值信息 
	 * @param redisParam 保存的键值对象
	 ******/
	boolean saveRedisStorage(RedisParam redisParam);
	
	/**
	 * 获取redis存储的对象，默认都是string存储的json对象
	 * @param redisKey redis的键
	 * @param RedisParam 获取到的redis对象并转换为返回对象
	 ******/
	RedisParam getRedisStorage(String redisKey);
	
	/****
	 * 删除redis存储对象，可以删除多个，中间英文,隔开
	 * @param redisKeys 删除的keys
	 **/
	boolean deleteRedisStorageByKeys(String redisKeys);
	
	/***
	 * 清理redis缓存，为了安全记录日志且必须值清理部分key，为避免雪崩系统写死不匹配
	 * @return 操作结果
	 ****/
	boolean cleanUpRedis();
	
}
