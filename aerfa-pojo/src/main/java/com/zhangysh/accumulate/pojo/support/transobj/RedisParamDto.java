package com.zhangysh.accumulate.pojo.support.transobj;

import java.io.Serializable;

import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.RedisParam;

/*****
 * redis参数配置，键值映射传输对象
 * @author zhangysh
 * @date 2019年5月19日
 *****/
public class RedisParamDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private RedisParam redisParam;
	private BsTablePageInfo pageInfo;
	
	public RedisParam getRedisParam() {
		return redisParam;
	}
	public void setRedisParam(RedisParam redisParam) {
		this.redisParam = redisParam;
	}
	public BsTablePageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(BsTablePageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
