package com.zhangysh.accumulate.ui.sys.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhangysh.accumulate.common.constant.*;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.ui.plugins.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.IpUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysLoginDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysConfigDataVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.ui.sys.service.IConfigDataService;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;

import eu.bitwalker.useragentutils.UserAgent;

/*****
 * 登陆相关方法
 * @author zhangysh
 * @date 2018年7月26日
 *****/
@Controller
public class LoginController {

	@Resource
	private ILoginService loginService;
	@Autowired
	private IConfigDataService configDataService;
	
	/****
	 * 跳转到sys默认登陆页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return thymeleaf在templates下的页面名称登陆界面 
	 ****/
	@RequestMapping(value="/")
	public String toSysLogin(HttpServletRequest request, ModelMap modelMap) {
		String allConfigDataJsonStr=configDataService.getAll();
		List<AefsysConfigDataVo> configDataList=JSON.parseArray(allConfigDataJsonStr, AefsysConfigDataVo.class);
		for(AefsysConfigDataVo configData:configDataList) {
			//系统名称添加到前台
			if(SysDefineConstant.CONFIG_DATA_FULL_SYSTEM_NAME.equals(configData.getDataCode())||SysDefineConstant.CONFIG_DATA_SHORT_SYSTEM_NAME.equals(configData.getDataCode())) {
				modelMap.put(configData.getDataCode(), configData.getDataValue());
			}
		}
		return "sys/login";
	}
	/****
	 * 跳转到sys默认登陆页面
	 *@param request 请求对象
	 *@param model spring的mvc返回对象
	 *@return thymeleaf在templates下的页面名称登陆界面 
	 ****/
	@RequestMapping(value="/login")
	public String toSysLoginV1(HttpServletRequest request, Model model) {
		return "redirect:/";
	}
	
	/****
	 * 跳转到sys登陆版本页面,后跟版本号
	 *@param request 请求对象
	 *@param model spring的mvc返回对象
	 *@return thymeleaf在templates下的页面名称登陆界面 
	 ****/
	@RequestMapping(value="/login/{version}")
	public String toSysLoginVersion(HttpServletRequest request, Model model,@PathVariable("version") String version) {
		return "sys/login_"+version;
	}
	
	/****
	 * 主页面退出按钮，返回到登录页面。后台退出操作：清除token等
	 *@param request 请求对象
	 *@param model spring的mvc返回对象
	 *@return thymeleaf在templates下的页面名称
	 ****/
	@RequestMapping(value="/quit")
	public String doSysQuit(HttpServletRequest request, Model model) {
		return "redirect:/";
	}
	
	/****
	 * 登陆账号密码校验 
	 *@param account 账号
	 *@param password 密码
	 *@return ResultVo对象
	 ****/
	@RequestMapping(value="/sys/login/check",method = RequestMethod.POST)
	@ResponseBody
	public String checkLoginInfo(HttpServletRequest request, HttpServletResponse response,String account,String password) {
		Subject subject= ShiroUtils.getSubjct();
		//封装用户数据
        UsernamePasswordToken usernamePassword=new UsernamePasswordToken(account,password);
        //执行登录方法
		try {
			//只要执行login方法，就会去执行UserRealm中的认证逻辑
			subject.login(usernamePassword);
			//如果没有异常，代表登录成功
			//只有获取当前token
			String backToken=ShiroUtils.getToken();
			HttpStorageUtil.setCookieValue(request, response, CacheConstant.COOKIE_NAME_AERFATOKEN, backToken, -1, true);
			return JSON.toJSONString(ResultVo.success(backToken));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			//登录失败
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_ACCOUNT_PASSWORD_WRONG_ERROR));
		}
	}
	
	/**
	 *账号密码校验成功后跳转到sys主页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的主页面
	 ****/
	@RequestMapping(value="/index")
	public String toSysIndex(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);
		String orgObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_ORG)+"";
		AefsysOrg orgVo=JSON.parseObject(orgObjectJson,AefsysOrg.class);

		modelMap.put("person", personVo);
		modelMap.put("org", orgVo);
		//特殊的token值便于websocket连接使用
		modelMap.put(WebimDefineConstant.WEBSOCKET_TOKEN_NAME_WEBIM, WebimDefineConstant.WEBSOCKET_TOKEN_VALUE_WEBIM);

		return "sys/index";
	}
	
	/**
	 *跳转到第一个通用页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的通用页面
	 ****/
	@RequestMapping(value="/current")
	public String toSysCurrent(HttpServletRequest request,ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		modelMap.addAttribute("prefix","/comm/note_calendar");
		return "sys/current";
	}
	
}
