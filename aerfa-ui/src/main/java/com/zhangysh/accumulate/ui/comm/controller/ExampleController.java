package com.zhangysh.accumulate.ui.comm.controller;

import javax.servlet.http.HttpServletRequest;

import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/*****
 * 各种例子跳转页面
 * @author zhangysh
 * @date 2019年7月6日
 *****/
@Controller
@RequestMapping("/comm/example")
public class ExampleController {

	private String prefix="/comm/example";//返回界面路径即前缀

	/**
	 *跳转到common下例子jquery电子签名页面
	 ****/
	@RequestMapping(value="/to_jquery_signature")
	public String toExampleJquerySignature(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/jquery_signature";
	}
	
	/**
	 *跳转到common下例子智能问答机器人页面
	 ****/
	@RequestMapping(value="/to_iqa_automatic")
	public String toExampleIqaAutomatic(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		//特殊的token值便于websocket连接使用
		modelMap.put(WebimDefineConstant.WEBSOCKET_TOKEN_NAME_AUTO, WebimDefineConstant.WEBSOCKET_TOKEN_VALUE_AUTO);
		return prefix+"/iqa_automatic";
	}
	
	/**
	 *跳转到common下例子智能问答人工客服页面
	 ****/
	@RequestMapping(value="/to_iqa_manual")
	public String toExampleIqaManual(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		//特殊的token值便于websocket连接使用
		modelMap.addAttribute(WebimDefineConstant.WEBSOCKET_TOKEN_NAME_MANUAL_CUSTOMER, WebimDefineConstant.WEBSOCKET_TOKEN_VALUE_MANUAL_CUSTOMER);
		return prefix+"/iqa_manual";
	}
	
	
}
