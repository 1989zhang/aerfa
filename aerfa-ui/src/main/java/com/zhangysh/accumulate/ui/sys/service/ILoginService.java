package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysLoginDto;

/*****
 * 登录相关调用后台方法
 * @author zhangysh
 * @date 2019年3月28日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface ILoginService {

	/****
	 *校验人员登录信息
	 *@param loginDto 登录条件对象
	 ***/
	@RequestMapping(value = "/sys/login/check",method = RequestMethod.POST)
	public String checkLoginInfo(@RequestBody AefsysLoginDto loginDto);
	
	/***
	 *根据token获取人员存到redis的各种session信息 
	 *@param aerfatoken 设置在前台cookie的token对象
	 ****/
	@RequestMapping(value = "/sys/login/session",method = RequestMethod.POST)
	public String getSessionByToken(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);
	
	/***
	 *根据token获取人员存到redis的各种session信息 
	 *@param aerfatoken 前台cookie的token对象
	 ****/
	@RequestMapping(value = "/sys/login/refresh_session",method = RequestMethod.POST)
	public String refreshSessionByToken(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);
	
}
