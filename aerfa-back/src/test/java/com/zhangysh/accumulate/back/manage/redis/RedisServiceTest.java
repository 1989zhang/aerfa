package com.zhangysh.accumulate.back.manage.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.back.manage.redis.IRedisService;
import com.zhangysh.accumulate.common.pojo.TokenModel;

/**
 *测试redis
 *@author 创建者：zhangysh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {
	
	  @Autowired
	  private IRedisService redisService;
	  
	  @Test
	  public void TestMain() throws Exception{
		  
		            //used_memory=1.26M
	            	//db0=keys=242,expires=241,avg_ttl=299489139   String ssi = value.substring(5, value.indexOf(","));
	            	//connected_clients=2
	            	//total_commands_processed=91 查询总数
	            	//keyspace_hits=3 keyspace_misses=0 int keyspace_hits_scope = 0;
	               /* if (keyspace_misses + keyspace_hits > 0) {
	                  keyspace_hits_scope = Math.round(keyspace_hits * 100 / (keyspace_misses + keyspace_hits));
	                  if (keyspace_hits_scope < 0) {
	                    keyspace_hits_scope = 0;
	                  }
	                }*/
		  
	            	//used_memory_peak
	            	//used_memory
	            	//used_cpu_sys=0.36	
	            	//total_commands_processed=91 每10秒算  新减去旧的  每秒查询多少次
		  
		  /*TokenModel tokenModel=new TokenModel();
		  tokenModel.setPersonId(1L);
		  tokenModel.setToken("afwdw2018091212569fefpw");*/
          //用JSON存
		  //redisService.setRedis(CacheConstant.REDIS_AERFATOKEN_PREFIX, "1", JSON.toJSONString(tokenModel), CacheConstant.REDIS_AERFATOKEN_EXPIRES_HOUR);
		/* List retList= redisService.getRedisContainKeys("");
		 System.out.println(retList.get(0));*/
		 // redisService.getRedisKeys();
		  
		  Long ret=redisService.getRedisExpireTimeByRealKey("aerfatoken1");

	            System.out.println(ret);
	       
	  }
}
