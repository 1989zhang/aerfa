package com.zhangysh.accumulate.common.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 *人员信息存redis对象：key为UUID的"aerfatoken"，value为此对象
 *@author zhangysh 
 *@date 2018年9月12日
 */
public class TokenModel implements Serializable{

	private static final long serialVersionUID = 1L;

	// 用户id
	private Long personId;
	// 随机生成的uuid
	private String token;
	// 存储登录之后的用户相关信息,Map获取key对应值的时候，值已经是字符string了，存的时候存的json
	private Map<String, Object> session = new HashMap<String, Object>();

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
	public String toString() {
		return "TokenModel [personId=" + personId + ", token=" + token + ", session=" + session + "]";
	}
}
