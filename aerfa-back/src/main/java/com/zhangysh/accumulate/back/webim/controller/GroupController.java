package com.zhangysh.accumulate.back.webim.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.webim.service.IGroupService;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimGroupDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimGroupVo;

/*****
 * 即时通讯webim群组相关方法
 * @author zhangysh
 * @date 2019年10月20日
 *****/
@Controller
@RequestMapping("/webim/group")
public class GroupController extends BaseController{

	@Resource
	private IGroupService groupService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
	
	/****
	 * 保存新增的加群组申请 
	 * @param request 请求对象
	 * @param apply 保存的对象
	 ****/
	@RequestMapping(value="/save_apply",method = RequestMethod.POST)
    @ResponseBody
	public String saveApply(HttpServletRequest request,@RequestBody AefwebimApplyDto apply) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		return groupService.insertGroupFriendAndAddTipsInfo(apply,operPerson);
	}
	
	/****
	 * 获取展示群组信息列表
	 * @param request 请求对象
	 * @param groupDto 分页和查询对象
	 * @return 获取到的群组对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefwebimGroupDto groupDto) {
		if(groupDto.getPageInfo()!=null) {
			BsTableDataInfo tableInfo=groupService.listPageGroup(groupDto.getPageInfo(),groupDto.getGroup());
			return toHandlerResultStr(true,tableInfo,null,null);
		}else {
			List<AefwebimGroupVo> groupList=groupService.listGroup(groupDto.getGroup());
			return toHandlerResultStr(true,groupList,null,null); 
		}
	}
	
	/***
	 * 获取查找信息，包括查找群组条数
	 * @param request 请求对象
	 * @param searchDto 查询条件
	 * @return 页面显示数量
	 *****/
	@RequestMapping(value="/search_page",method=RequestMethod.POST)
	@ResponseBody
	public String getSearchPage(HttpServletRequest request,@RequestBody AefwebimSearchDto searchDto) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request);
			Map<String,Object> pageRetMap=groupService.getSearchPage(searchDto);
			return toHandlerResultStr(true, pageRetMap, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR, e.getMessage());
		}
	}
	
	/***
	 * 获取查找信息，包括查找群组详细信息
	 * @param request 请求对象
	 * @param searchDto 查询条件
	 * @return 群组详细信息
	 *****/
	@RequestMapping(value="/search_info",method=RequestMethod.POST)
	@ResponseBody
	public String getSearchInfo(HttpServletRequest request,@RequestBody AefwebimSearchDto searchDto) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request); 
			List<AefwebimGroupVo> searchInfoList=groupService.getSearchInfo(searchDto);
			return toHandlerResultStr(true, searchInfoList, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR, e.getMessage());
		}
	}
	
}
