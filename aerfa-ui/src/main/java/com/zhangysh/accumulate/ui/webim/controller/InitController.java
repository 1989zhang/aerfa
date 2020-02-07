package com.zhangysh.accumulate.ui.webim.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.ui.webim.service.IInitService;

/*****
 * 获取webim初始化信息
 * @author zhangysh
 * @date 2019年9月30日
 *****/
@Controller
@RequestMapping("/webim/init")
public class InitController {
	
	@Resource
	private IInitService initService;

	/**
	 * 获取初始显示信息:包括个人信息，好友组，群组信息
	 * @param sid 取person个人的id
	 * @return 返回信息json
	 ****/
	@RequestMapping(value="/user_data")
	@ResponseBody
	public String getUserData(HttpServletRequest request,Long sid) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return initService.getUserData(aerfatoken,sid);
	}
	
	/**
	 * 获取群组下面的人员列表信息
	 * @param id 群组id
	 * @return 返回信息json
	 ****/
	@RequestMapping(value="/group_members")
	@ResponseBody
	public String getGroupMembers(HttpServletRequest request,Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return initService.getGroupMembers(aerfatoken,id);
	}
	
}
