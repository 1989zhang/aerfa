package com.zhangysh.accumulate.pojo.support.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseParamObj;

/*****
 * redis参数对象模型vo
 * @author zhangysh
 * @date 2019年5月19日
 *****/
public class RedisParam extends BaseParamObj{

	private static final long serialVersionUID = 1L;
	
	/**键值对应的值类型**/
	private String type;
	/**键值对应的过期时间**/
	private Long expireTime;
	//redis键parmCode;
	//redis值parmValue;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	
	
}
