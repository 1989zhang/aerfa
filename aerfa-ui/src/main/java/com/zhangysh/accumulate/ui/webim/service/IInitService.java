package com.zhangysh.accumulate.ui.webim.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;

/*****
 * 获取webim初始化信息
 * @author zhangysh
 * @date 2019年10月8日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IInitService {

	/****
	 * 获取初始显示信息:包括个人信息，好友组，群组信息
	 * @param sid 取person个人的id
	 ***/
	@RequestMapping(value = "/webim/init/user_data",method = RequestMethod.POST)
	public String getUserData(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long sid);

	/****
	 * 获取群组下面的人员列表信息
	 * @param aerfatoken token对象
	 * @param id 群组的id
	 ***/
	@RequestMapping(value = "/webim/init/group_members",method = RequestMethod.POST)
	public String getGroupMembers(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	
}
