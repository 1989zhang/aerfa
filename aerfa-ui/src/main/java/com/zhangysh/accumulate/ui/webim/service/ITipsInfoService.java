package com.zhangysh.accumulate.ui.webim.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoDto;


/*****
 * 提示消息相关调用后台方法
 * @author zhangysh
 * @date 2019年10月22日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface ITipsInfoService {

	/****
	 * 分页显示人员的消息提醒列表
	 * @param aerfatoken token对象
	 * @param tipsInfoDto 查询条件
	 ***/
	@RequestMapping(value = "/webim/tips_info/msg_box",method = RequestMethod.POST)
	public String getWebimMsgbox(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimTipsInfoDto tipsInfoDto);

	/****
	 * 处理提示消息,接收邀请信息
	 * @param aerfatoken token对象
	 * @param tipsInfo 要保存的提示消息对象
	 ***/
	@RequestMapping(value = "/webim/tips_info/accept_invite",method = RequestMethod.POST)
	public String acceptInvite(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimTipsInfo tipsInfo);

	/****
	 * 处理提示消息,拒绝邀请信息
	 * @param aerfatoken token对象
	 * @param tipsInfo 要保存的提示消息对象
	 ***/
	@RequestMapping(value = "/webim/tips_info/refuse_invite",method = RequestMethod.POST)
	public String refuseInvite(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimTipsInfo tipsInfo);

}