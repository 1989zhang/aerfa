package com.zhangysh.accumulate.back.manage.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.common.constant.UnitConstant;
import com.zhangysh.accumulate.common.util.StringUtil;

@Service
@SuppressWarnings(value= {"rawtypes","unchecked"})
public class RedisServiceImpl implements IRedisService{
	
	private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

	private RedisTemplate redis;
	
	@Resource(name = "redisTemplate")
	public void setRedis(RedisTemplate redis) {
		this.redis = redis;
		
		//必须更改对应的序列化方案，不然存储通过工具看起来是乱码
		redis.setKeySerializer(new StringRedisSerializer());
		redis.setValueSerializer(new StringRedisSerializer());
		redis.setHashKeySerializer(new StringRedisSerializer());
		redis.setHashValueSerializer(new StringRedisSerializer());
	}
	
	@Override
	public <T> boolean setRedis(String prefix, String key, T value, Long expiresHour) {
		String realKey  = (StringUtil.isEmpty(prefix)?"":prefix)  + (StringUtil.isEmpty(key)?"":key);
		Long expires = expiresHour;
		logger.info("添加redis对象的key为:{}",realKey);
		if(expires<0) {
			redis.boundValueOps(realKey).set(value);
		}else {
			redis.boundValueOps(realKey).set(value, expires, TimeUnit.HOURS);
		}
		return true;
	}
	@Override
	public <T> boolean setRedis(String prefix, String key, T value, Long expiresTime,String timeOutUnit) {
		String realKey  = (StringUtil.isEmpty(prefix)?"":prefix)  + (StringUtil.isEmpty(key)?"":key);
		Long expires = expiresTime;
		logger.info("添加redis对象的key为:{}",realKey);
		if(expires<0) {
			redis.boundValueOps(realKey).set(value);
		}else {
			if(UnitConstant.TIME_UNIT_DAYS.equals(timeOutUnit)) {
				redis.boundValueOps(realKey).set(value, expires, TimeUnit.DAYS);
			}else if(UnitConstant.TIME_UNIT_HOURS.equals(timeOutUnit)) {
				redis.boundValueOps(realKey).set(value, expires, TimeUnit.HOURS);
			}else if(UnitConstant.TIME_UNIT_MINUTES.equals(timeOutUnit)) {
				redis.boundValueOps(realKey).set(value, expires, TimeUnit.MINUTES);
			}else if(UnitConstant.TIME_UNIT_SECONDS.equals(timeOutUnit)) {
				redis.boundValueOps(realKey).set(value, expires, TimeUnit.SECONDS);
			}else if(UnitConstant.TIME_UNIT_MILLISECONDS.equals(timeOutUnit)) {
				redis.boundValueOps(realKey).set(value, expires, TimeUnit.MILLISECONDS);
			}else if(UnitConstant.TIME_UNIT_MICROSECONDS.equals(timeOutUnit)) {
				redis.boundValueOps(realKey).set(value, expires, TimeUnit.MICROSECONDS);
			}else if(UnitConstant.TIME_UNIT_NANOSECONDS.equals(timeOutUnit)) {
				redis.boundValueOps(realKey).set(value, expires, TimeUnit.NANOSECONDS);
			}
		}
		return true;
	}
	
	@Override
	public <T> T getRedis(String prefix,String key,Class<T> clazz) {
		String realKey  = prefix + key;
		Object obj = redis.boundValueOps(realKey).get();
		if (obj != null) {
			return (T)obj;
		}
		return null;
	}
	
	@Override
	public <T> T getRedisByRealKey(String key, Class<T> clazz) {
		Object obj = redis.boundValueOps(key).get();
		if (obj != null) {
			return (T)obj;
		}
		return null;
	}
	
	@Override
	public boolean delRedis(String prefix,String key) {
		String realKey  = prefix + key;
		logger.info("删除redis对象的key为:{}",realKey);
		redis.delete(realKey);
		return true;
	}
	
	@Override
	public boolean delRedisByRealKey(String key) {
		logger.info("删除redis对象的key为:{}",key);
		redis.delete(key);
		return true;
	}
	
	@Override
	public Long getRedisIncrement(String prefix,String key,Long expireHour) {
		String realKey  = prefix + key;
		RedisAtomicLong counter = new RedisAtomicLong(realKey, redis.getConnectionFactory());
		Long increment = counter.getAndIncrement();
		if(increment==0) {
			increment = counter.getAndIncrement();
		}
		counter.expire(expireHour, TimeUnit.HOURS);
		logger.info("redis获取{}自增长为:{}",realKey,increment);
	    return increment;
	}

	@Override
	public List<String> getRedisContainKeys(String containKey){
		//因为要重复，所有要Set<Object>形式取出已有keys
		Set<Object> retKeySet=(Set<Object>)redis.execute(new RedisCallback() {
			@Override
			public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				String patternKey="*";
				if(StringUtil.isNotEmpty(containKey)&&!patternKey.equals(containKey)) {
					patternKey="*"+containKey+"*";
				}
				Set<Object> binaryKeys = new HashSet<>();
				Cursor<byte[]> cursor = connection.scan( new ScanOptions.ScanOptionsBuilder().match(patternKey).build());
			    while (cursor.hasNext()) {
			    	String getStr=new String(cursor.next());
		            binaryKeys.add(getStr);
			    }
				return binaryKeys;
			}
		});
		List<String> keysList=new ArrayList<String>();
        //再进行返回格式的组装,for循环遍历： 
		for (Object objectKey: retKeySet) {  
			keysList.add(objectKey.toString());
		}  
		return keysList;
	}
	
	@Override
    public Set<Map.Entry<Object, Object>> getRedisInfo(){
		return (Set<Map.Entry<Object, Object>>) redis.execute(new RedisCallback(){
			@Override
			public Set<Map.Entry<Object, Object>> doInRedis(RedisConnection connection) throws DataAccessException {
				Properties properties = connection.info();
            	Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
				return entrySet;
			}
    	});
    }
	
	@Override
    public Long getRedisDatabases() {
    	return (Long)redis.execute(new RedisCallback() {
			@Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
            	Properties properties =connection.getConfig("databases");
            	Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
            	for (Map.Entry<Object, Object> entry : entrySet) {
                    return  Long.parseLong(entry.getValue().toString());
                }
            	return 0L;
            }
        });
    }
	
	@Override
    public Long getRedisExpireTimeByRealKey(String key) {
    	if(StringUtil.isEmpty(key)){return 0l;}
    	return redis.getExpire(key);
    }
	
	@Override
    public Long cleanUpRedisPattern(String patternKey) {
		if(StringUtil.isNotEmpty(patternKey)&&!"*".equals(patternKey)) {
			patternKey="*"+patternKey+"*";
		}
		Set cleanUpKeys=redis.keys(patternKey);
		if (cleanUpKeys.size() > 0){
			return redis.delete(cleanUpKeys);
		}
		return 0L;
	}
}
