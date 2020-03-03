package com.zhangysh.accumulate.ui.iqa.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/*****
 * 智能问答，工作台相关方法
 * @author zhangysh
 * @date 2019年11月1日
 *****/
@Controller
@RequestMapping("/iqa/work")
public class IqaWorkController {

	@Resource
	private ILoginService loginService;

	private String prefix="/iqa/work";//返回界面路径即前缀
	
	/**
	 *跳转到客服工作空间
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的配置页面
	 ****/
	@RequestMapping(value="/to_space")
	public String toSpace(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken= HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel= JSON.parseObject(sessionInfoStr,TokenModel.class);
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);

		modelMap.addAttribute("prefix",prefix);
		modelMap.addAttribute("personId",personVo.getId());
		modelMap.addAttribute("nickName",personVo.getNickName());
		modelMap.addAttribute(WebimDefineConstant.WEBSOCKET_TOKEN_NAME_MANUAL_WORKER, WebimDefineConstant.WEBSOCKET_TOKEN_VALUE_MANUAL_WORKER);
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

	/***
	 * 获取客服人员列表
	 **/
	@RequestMapping(value="/list_worker")
	@ResponseBody
	public String getListWorker(HttpServletRequest request, ModelMap modelMap, BsTablePageInfo pageInfo){
		List<AefsysPersonVo> workerList=new ArrayList<AefsysPersonVo>();
		for (int i=1;i<=10;i++){
			AefsysPersonVo vo1=new AefsysPersonVo();
			vo1.setId(Long.valueOf(i+""));
			vo1.setPersonName("客服"+(i)+"号");
			vo1.setEmail("8603145"+i+"@soft.com");
			vo1.setPersonSex("0");
			vo1.setPhoneNo("8603145"+i);
			workerList.add(vo1);
		}
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(10);
		tableInfo.setRows(workerList);
		return JSON.toJSONString(tableInfo);
	}
	
}
