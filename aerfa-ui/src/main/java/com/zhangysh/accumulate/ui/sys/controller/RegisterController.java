package com.zhangysh.accumulate.ui.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.common.pojo.ResultVo;

/**
 * 注册相关方法
 * @author zhangysh
 * @date 2018年8月17日
 */
@Controller
public class RegisterController {

	private static final Logger logger=LoggerFactory.getLogger(RegisterController.class);
	
	/**
	 *登陆页面注册，跳转到sys注册页面
	 *@param request 请求对象
	 *@param model spring的mvc返回对象
	 *@return templates下的注册页面
	 ****/
	@RequestMapping(value="/register")
	public String toSysRegister(HttpServletRequest request, Model model) {
		return "sys/register/register";
	}
	
	/****
	 * 账号信息注册，创建账号信息 
	 *@param account 账号
	 *@param password 密码
	 *@return ResultVo对象
	 ****/
	@RequestMapping(value="/sys/register/create",method = RequestMethod.POST)
	@ResponseBody
	public Object registerCreateInfo(String account,String password) {
		logger.info("执行注册方法：账号"+account+"密码"+password);
		
		return  ResultVo.success("注册成功!");
	}
}
