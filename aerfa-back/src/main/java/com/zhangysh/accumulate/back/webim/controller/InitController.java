package com.zhangysh.accumulate.back.webim.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.back.webim.service.IInitService;

/*****
 * 获取webim初始化信息
 * @author zhangysh
 * @date 2019年9月30日
 *****/
@Controller
@RequestMapping("/webim/init")
public class InitController extends BaseController{
	
	@Resource
	private IInitService initService;
	
	private static final Logger logger = LoggerFactory.getLogger(InitController.class);
	
	/**
	 * 获取初始显示信息:包括个人信息，好友组，群组信息
	 * @param id 取sessionid
	 * @return 返回信息json
	 ****/
	@RequestMapping(value="/user_data",method = RequestMethod.POST)
	@ResponseBody
	public String getUserData(HttpServletRequest request,@RequestBody Long id) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request); 
			logger.info("getUserData参数{}",id);
			Map<String,Object> retMap=initService.getUserData(id);
			return toHandlerResultStr(true, retMap, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR, e.getMessage());
		}
	}
	
	/***
	 * 获取群组下面的人员列表信息
	 * @param request 请求对象
	 * @param id 群组id
	 * @return 群组下面所属成员对象
	 *****/
	@RequestMapping(value="/group_members",method=RequestMethod.POST)
	@ResponseBody
	public String getGroupMembers(HttpServletRequest request,@RequestBody Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		Map<String,Object> retMap=initService.getGroupMembers(id);
		return toHandlerResultStr(true, retMap, null, null);
	}
			
}
