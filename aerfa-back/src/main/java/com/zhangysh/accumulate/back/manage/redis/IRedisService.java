package com.zhangysh.accumulate.back.manage.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/****
 *redis缓存相关方法封装,存的对象为泛型，不带JSON互转
 *@author zhangysh
 *@date 2018年9月12日
 ***/
public interface IRedisService {
	
	/****
	 *设置redis的值: 前缀加键组成redis的key，后面为值
	 *@param prefix 前缀标示对象名称，一般用表名
	 *@param key 后缀，键，一般用id
	 *@param value 值泛型对象
	 *@param expiresHour 过期时间小时，小于零则永久有效
	 *@return 设置成功true
	 ***/
	 <T> boolean setRedis(String prefix,String key,T value,Long expiresHour);
	 
	 /**
	  *设置redis的值: 前缀加键组成redis的key，后面为值 
	  * @param prefix 前缀标示对象名称，一般用表名
	  * @param key 键，一般用id
	  * @param value 值泛型对象
	  * @param expiresTime 过期时间
	  * @param timeOutUnit 过期时间的单位，要大写
	  * @return 设置成功true
	  */
	 <T> boolean setRedis(String prefix,String key,T value,Long expiresTime,String timeOutUnit);
	
	 /***
	 *根据key获取redis里面的对象，并转换为Class
     *@param prefix 前缀标示对象名称，一般用表名，和存对应
	 *@param key 键，一般用id，和存对应
	 *@param clazz 要转换的class
	 *@return  T转换得到的对象
	 ***/
	 <T> T getRedis(String prefix,String key,Class<T> clazz);
	
	 /**
	  * 根据存入redis的key从redis中取出数据
	  * @param key 真正的key
	  * @param clazz 要转化的class
	  * @return 转化得到的对象
	  */
	 <T> T getRedisByRealKey(String key,Class<T> clazz);
	 
	/***
	 *根据key删除redis中的值 
	 *@param prefix 前缀标示对象名称，一般用表名，和存对应
	 *@param key 键，一般用id，和存对应
	 *@return 删除成功true
	 ***/
	 boolean delRedis(String prefix,String key);
	 
	 /***
	 *根据key删除redis中的值 
	 *@param key 键
	 *@return 删除成功true
	 ***/
	 boolean delRedisByRealKey(String key);
	 
	 /***
	  *获取redis的自动增长序列
	  *@param prefix 前缀标示
	  *@param key 哪种key的自增,每天不重复就传日期
	  *@param expireHour 过期时间小时，每天不重复就传24
	  *@return 自增长的序列
	  ***/
	 Long getRedisIncrement(String prefix,String key,Long expireHour);	 

	 /****
	  *scan方式获取redis的匹配到的keys，匹配所有传*;返回的key有一定排序规则
	  *@return  List类型的键
	  ***/
	 List<String> getRedisContainKeys(String containKey);
	 
	 /***
	  *获取redis属性配置包括占用资源，基本配置等，如下解析 
	  *for(Map.Entry<Object,Object>entry:entrySet){System.out.println(entry.getKey()+"="+entry.getValue());}
	  *@return Map类型配置信息
	  ******/
	 Set<Map.Entry<Object, Object>> getRedisInfo();
	 
	 /****
	  *获取redis数据库db个数，redis默认正常是16个
	  *@return db的个数
	  ***/
	 Long getRedisDatabases();
	 
	 /****
	  *获取key的剩余过期时间，单位秒 
	  *@param key 键
	  *@return 剩余过期时间s
	  ***/
	 Long getRedisExpireTimeByRealKey(String key);
	 
	 /***
	  * redis清除匹配包含到的key和对应的值
	  * @param patternKey 匹配包含key字符
	  * @return 清除的redis条数
	  *****/
	 Long cleanUpRedisPattern(String patternKey);
}
