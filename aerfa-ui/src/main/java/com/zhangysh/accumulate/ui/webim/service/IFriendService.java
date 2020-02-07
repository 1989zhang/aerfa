package com.zhangysh.accumulate.ui.webim.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;

/*****
 * 即时通讯webim好友相关方法
 * @author zhangysh
 * @date 2019年10月20日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IFriendService {

	/****
	 * 获取系统推荐好友
	 * @param sid 取person个人的id
	 ***/
	@RequestMapping(value = "/webim/friend/recommend",method = RequestMethod.POST)
	public String getRecommendFriend(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long sid);

	/****
	 * 保存新增的好友信息 
	 * @param aerfatoken token对象
	 * @param Friend 要保存的好友对象
	 ***/
	@RequestMapping(value = "/webim/friend/save_apply",method = RequestMethod.POST)
	public String saveApply(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimApplyDto apply);
	
	/****
	 * 获取查找信息，包括查找好友条数
	 * @param searchDto 查询条件对象
	 ***/
	@RequestMapping(value = "/webim/friend/search_page",method = RequestMethod.POST)
	public String getSearchPage(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimSearchDto searchDto);

	/****
	 * 获取查找信息，包括查找好友详细信息
	 * @param searchDto 查询条件对象
	 ***/
	@RequestMapping(value = "/webim/friend/search_info",method = RequestMethod.POST)
	public String getSearchInfo(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimSearchDto searchDto);

}
