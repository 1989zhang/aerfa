package com.zhangysh.accumulate.back.manage.service;

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
	  public void testSetRedis() throws Exception{
		  TokenModel tokenModel=new TokenModel();
		  tokenModel.setPersonId(1L);
		  tokenModel.setToken("afwdw2018091212569fefpw");
          //用JSON存
		  redisService.setRedis(CacheConstant.REDIS_AERFATOKEN_PREFIX, "1", JSON.toJSONString(tokenModel), CacheConstant.REDIS_EXPIRES_SEVEN_DAYS);
	  }
}
