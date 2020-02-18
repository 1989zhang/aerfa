package com.zhangysh.accumulate.back.sys.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.sys.service.ILoginService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysLoginDto;

/*****
 * 登陆相关方法
 * @author zhangysh
 * @date 2019年3月26日
 *****/
@Controller
@RequestMapping("/sys/login")
public class LoginController extends BaseController{

	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ILoginService loginService;
	
    
	/****
	 * 登陆账号密码校验 
	 *@param loginDto 登录对象内含账号密码
	 *@return ResultVo操作结果对象
	 ****/
	@RequestMapping(value="/check",method = RequestMethod.POST)
	@ResponseBody
	public String checkLoginInfo(HttpServletRequest request,@RequestBody AefsysLoginDto loginDto) {	
		return loginService.checkLoginInfo(loginDto);
	}
	
	/****
	 * 根据token获取人员存到redis的各种session信息 
	 * @param request 请求对象，header里面含token
	 * @return token对应的缓存对象
	 ****/
	@RequestMapping(value="/session",method = RequestMethod.POST)
	@ResponseBody
	public String getSessionByToken(HttpServletRequest request) {
		String aerfatoken=request.getHeader(CacheConstant.COOKIE_NAME_AERFATOKEN);
		return loginService.getSessionByToken(aerfatoken);
	}
	
	/****
	 * 根据token刷新人员的各种session信息存到redis
	 * @param request 请求对象，header里面含token
	 * @return ResultVo操作结果对象
	 ****/
	@RequestMapping(value="/refresh_session",method = RequestMethod.POST)
	@ResponseBody
	public String refreshSessionByToken(HttpServletRequest request) {
		String aerfatoken=request.getHeader(CacheConstant.COOKIE_NAME_AERFATOKEN);
		return toHandlerResultStr(loginService.refreshSessionByToken(aerfatoken));
	}
}
