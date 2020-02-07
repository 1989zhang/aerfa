package com.zhangysh.accumulate.ui.iqa.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/*****
 * 智能问答，工作台相关方法
 * @author zhangysh
 * @date 2019年11月1日
 *****/
@Controller
@RequestMapping("/iqa/work")
public class IqaWorkController {

	private String prefix="/iqa/work";//返回界面路径即前缀
	
	/**
	 *跳转到客服工作空间
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的配置页面
	 ****/
	@RequestMapping(value="/to_space")
	public String toSpace(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/space";
	}
	
	/**
	 *跳转到客服人员列表信息
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的配置页面
	 ****/
	@RequestMapping(value="/to_worker")
	public String toWorker(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/worker";
	}
	
}
