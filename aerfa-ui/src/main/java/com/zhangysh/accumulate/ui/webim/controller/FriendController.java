package com.zhangysh.accumulate.ui.webim.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;
import com.zhangysh.accumulate.ui.webim.service.IFriendService;
import com.zhangysh.accumulate.ui.webim.service.IGroupService;

/*****
 * 即时通讯webim好友相关方法
 * @author zhangysh
 * @date 2019年10月11日
 *****/
@Controller
@RequestMapping("/webim/friend")
public class FriendController {
	
	private String prefix="/webim/friend";//返回界面路径即前缀
	
	@Resource
	private IFriendService friendService;
	@Resource
	private IGroupService groupService;
	
	/****
	 * 跳转到webim查找好友页面
	 * @param request 请求对象
	 * @param model spring的mvc返回对象
	 * @return thymeleaf在templates下的页面名称查找好友界面 
	 ****/
	@RequestMapping(value="/to_find/{sid}")
	public String toFind(HttpServletRequest request,ModelMap modelMap,@PathVariable("sid") Long sid) {
		modelMap.addAttribute("sid",sid);
		modelMap.addAttribute("prefix",prefix);
		return "webim/module/find";
	}
	
	/**
	 * 获取系统推荐好友
	 * @param request 请求对象
	 * @param sid 会话人员id
	 * @return 返回推荐好友信息json
	 ****/
	@RequestMapping(value="/recommend/{sid}")
	@ResponseBody
	public String getRecommendFriend(HttpServletRequest request,@PathVariable("sid") Long sid) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return friendService.getRecommendFriend(aerfatoken, sid);
	}
	
	/**
	 * 获取查找信息，包括查找好友和群组条数
	 * @param request 请求对象
	 * @param type 搜索类型friend或group
	 * @param value 搜索条件
	 * @return 返回信息json
	 ****/
	@RequestMapping(value="/search_page")
	@ResponseBody
	public String getSearchPage(HttpServletRequest request,String type,String value) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefwebimSearchDto searchDto=new AefwebimSearchDto();
		searchDto.setType(type);
		searchDto.setValue(value);
		if(WebimDefineConstant.WEBIM_INIT_USER_DATA_FRIEND.equals(type)) {//查询好友
			return friendService.getSearchPage(aerfatoken,searchDto);
		}else {//查询群组
			return groupService.getSearchPage(aerfatoken,searchDto);
		}
		
		/*System.out.println(type);
		System.out.println(value);
		Map retMap=new HashMap();
		retMap.put("count", 6);
		retMap.put("limit", 3);
		return JSON.toJSONString(ResultVo.success(retMap));*/
	}
	/**
	 * 获取查找信息，包括查找好友和群组详细信息
	 * @param request 请求对象
	 * @param type 搜索类型friend或group
	 * @param value 搜索条件
	 * @param page 显示信息所在页
	 * @return 返回信息json
	 ****/
	@RequestMapping(value="/search_info")
	@ResponseBody
	public String getSearchInfo(HttpServletRequest request,String type,String value,Long page) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefwebimSearchDto searchDto=new AefwebimSearchDto();
		searchDto.setType(type);
		searchDto.setValue(value);
		searchDto.setPage(page);
		if(WebimDefineConstant.WEBIM_INIT_USER_DATA_FRIEND.equals(type)) {//查询好友
			return friendService.getSearchInfo(aerfatoken,searchDto);
		}else {//查询群组
			return groupService.getSearchInfo(aerfatoken,searchDto);
		}
	}
	
	/**
	 * 保存添加好友申请
	 * @param request 请求对象
	 * @return 返回操作结果信息json
	 ****/
	@RequestMapping(value="/save_apply")
	@ResponseBody
	public String saveApply(HttpServletRequest request,AefwebimApplyDto apply) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return friendService.saveApply(aerfatoken,apply);
	}
	
}
