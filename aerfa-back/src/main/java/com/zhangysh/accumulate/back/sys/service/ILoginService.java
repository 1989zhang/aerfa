package com.zhangysh.accumulate.back.sys.service;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysLoginDto;

/*****
 * 登录相关接口方法：因为此方法比较复杂，后面还会继续拓展。
 * 且为了在service用redis显得规范，所以要有此接口
 * @author zhangysh
 * @date 2019年5月27日
 *****/
public interface ILoginService {
	
	/**
	 * 登陆方法的账号密码校验 
	 * @param loginDto 登录验证对象，内含账号密码
	 ***/
	String checkLoginInfo(AefsysLoginDto loginDto);
	
	/**
	 * 根据token获取人员存到redis的各种session信息
	 * @param aerfatoken tiken参数
	 **/
	String getSessionByToken(String aerfatoken);
	
	/****
	 * 根据token刷新人员的各种session信息存到redis
	 * @param aerfatoken token的uuid对象
	 * @return 操作结果对象
	 * */
	boolean refreshSessionByToken(String aerfatoken);
}
