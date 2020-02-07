package com.zhangysh.accumulate.ui.webim.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimGroupDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.ui.webim.service.IFriendService;
import com.zhangysh.accumulate.ui.webim.service.IGroupService;

/*****
 * 即时通讯webim群组相关方法
 * @author zhangysh
 * @date 2019年10月20日
 *****/
@Controller
@RequestMapping("/webim/group")
public class GroupController {

	private String prefix="/webim/group";//返回界面路径即前缀
	
	@Resource
	private IGroupService groupService;
	
	/**
	 * 获取条件下好友群组集合
	 * @param request 请求对象
	 * @param sid 会话人员id
	 * @return 返回群组集合信息json
	 ****/
	@RequestMapping(value="/list/{sid}")
	@ResponseBody
	public String getGroupList(HttpServletRequest request,@PathVariable("sid") Long sid) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefwebimGroup group=new AefwebimGroup();
		group.setOwnerId(sid);
		group.setGroupType(WebimDefineConstant.WEBIM_GROUP_TYPE_FRIEND);
		AefwebimGroupDto groupDto=new AefwebimGroupDto();
		groupDto.setGroup(group);
		return groupService.getList(aerfatoken, groupDto);
	}
	
	/**
	 * 保存添加群组申请，因为信息还是记录到friend里面，所以AefwebimApplyDto来接收
	 * @param request 请求对象
	 * @return 返回操作结果信息json
	 ****/
	@RequestMapping(value="/save_apply")
	@ResponseBody
	public String saveApply(HttpServletRequest request,AefwebimApplyDto apply) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return groupService.saveApply(aerfatoken,apply);
	}
	
	/****
	 * 跳转到webim创建普通群页面
	 * @param request 请求对象
	 * @param model spring的mvc返回对象
	 * @return thymeleaf在templates下的页面名称创建普通群界面 
	 ****/
	@RequestMapping(value="/to_create_group/{sid}")
	public String toCreateGroup(HttpServletRequest request,ModelMap modelMap,@PathVariable("sid") Long sid) {
		modelMap.addAttribute("sid",sid);
		modelMap.addAttribute("prefix",prefix);
		return "webim/module/group";
	}
	
	/**
	 * 验证用户已创建普通群组个数，看是否可以继续创建普通群
	 * @param request 请求对象
	 * @param  sid 用户id取session的id
	 * @return 返回操作结果信息json
	 ****/
	@RequestMapping(value="/validate_total/{sid}")
	@ResponseBody
	public String validateTotal(HttpServletRequest request,@PathVariable("sid") Long sid) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return JSON.toJSONString(ResultVo.success(""));
	}
	
	/**
	 * 保存提交新增的普通群组
	 * @param request 请求对象
	 * @param  group 保存的普通群组对象
	 * @return 返回操作结果信息json
	 ****/
	@RequestMapping(value="/save_add")
	@ResponseBody
	public String saveAdd(HttpServletRequest request,AefwebimGroup group) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		group.setId(110L);
		return JSON.toJSONString(ResultVo.success(group));
	}
	
	/***
	 * 点击查看群组基本信息
	 * @param request 请求对象
	 * @param model spring的mvc返回对象
	 * @param id 普通群ID
	 * @param type 查看对象类型应为group
	 ***/
	@RequestMapping(value="/get_information")
	@ResponseBody
	public String getInformation(HttpServletRequest request,ModelMap modelMap,Long id,String type) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		modelMap.addAttribute("prefix",prefix);
		AefwebimSearchDto searchDto=new AefwebimSearchDto();
		searchDto.setType(type);
		searchDto.setValue(id+"");
		return groupService.getInformation(aerfatoken,searchDto);
	}
}
