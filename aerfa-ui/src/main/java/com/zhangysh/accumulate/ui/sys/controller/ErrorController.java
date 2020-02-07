package com.zhangysh.accumulate.ui.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/*****
 * 跳转到出错页面方法
 * @author zhangysh
 * @date 2019年6月4日
 *****/
@Controller
public class ErrorController {

	/****
	 * 跳转到没找到资源sys404页面
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param modelMap spring的mvc返回对象
	 * @return thymeleaf在templates下的页面名称登陆界面 
	 ****/
	@RequestMapping(value="/404")
	public String toNotFoundError(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		return "/error/404";
	}
	/****
	 * 跳转到服务器内部错误sys500页面
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param modelMap spring的mvc返回对象
	 * @return thymeleaf在templates下的页面名称登陆界面 
	 ****/
	@RequestMapping(value="/500")
	public String toServerHandleError(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) {
		return "/error/500";
	}
}
