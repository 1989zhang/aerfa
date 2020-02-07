package com.zhangysh.accumulate.back.webim.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.webim.service.IFriendService;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;

/*****
 * 即时通讯webim好友相关方法
 * @author zhangysh
 * @date 2019年10月14日
 *****/
@Controller
@RequestMapping("/webim/friend")
public class FriendController extends BaseController{
	
	@Autowired
	private IFriendService friendService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
	
	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);
	
	/***
	 * 获取系统推荐好友
	 * @param request 请求对象
	 * @param id 人员id
	 * @return 推荐成员对象
	 *****/
	@RequestMapping(value="/recommend",method=RequestMethod.POST)
	@ResponseBody
	public String getRecommendFriend(HttpServletRequest request,@RequestBody Long personId) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request); 
			logger.info("getRecommendFriend参数{}",personId);
			List<AefwebimFriendVo> recommendList=friendService.listRecommendFriend(personId);
			return toHandlerResultStr(true, recommendList, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR, e.getMessage());
		}
	}
	
	/****
	 * 保存新增和修改的好友信息 
	 * @param request 请求对象
	 * @param apply 保存的对象
	 ****/
	@RequestMapping(value="/save_apply",method = RequestMethod.POST)
    @ResponseBody
	public String saveApply(HttpServletRequest request,@RequestBody AefwebimApplyDto apply) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		return friendService.insertFriendAndAddTipsInfo(apply,operPerson);
	}
	
	/***
	 * 获取查找信息，包括查找好友条数
	 * @param request 请求对象
	 * @param searchDto 查询条件
	 * @return 页面显示数量
	 *****/
	@RequestMapping(value="/search_page",method=RequestMethod.POST)
	@ResponseBody
	public String getSearchPage(HttpServletRequest request,@RequestBody AefwebimSearchDto searchDto) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request);
			Map<String,Object> pageRetMap=friendService.getSearchPage(searchDto);
			return toHandlerResultStr(true, pageRetMap, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR, e.getMessage());
		}
	}
	
	/***
	 * 获取查找信息，包括查找好友详细信息
	 * @param request 请求对象
	 * @param searchDto 查询条件
	 * @return 好友详细信息
	 *****/
	@RequestMapping(value="/search_info",method=RequestMethod.POST)
	@ResponseBody
	public String getSearchInfo(HttpServletRequest request,@RequestBody AefwebimSearchDto searchDto) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request); 
			List<AefwebimFriendVo> searchInfoList=friendService.getSearchInfo(searchDto);
			return toHandlerResultStr(true, searchInfoList, null, null);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR, e.getMessage());
		}
	}
}
