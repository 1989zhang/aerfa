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
	 * 分页显示提示消息信息
	 * @param aerfatoken token对象
	 * @param TipsInfoDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/tips_info/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimTipsInfoDto TipsInfoDto);

	/****
	 * 获取单个提示消息信息
	 * @param aerfatoken token对象
	 * @param id 提示消息的id
	 ***/
	@RequestMapping(value = "/sys/tips_info/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 * 保存新增的提示消息信息 
	 * @param aerfatoken token对象
	 * @param TipsInfo 要保存的提示消息对象
	 ***/
	@RequestMapping(value = "/sys/tips_info/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimTipsInfo tipsInfo);

	/****
	 * 删除提示消息对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的提示消息ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/tips_info/delete",method = RequestMethod.POST)
	public String deleteTipsInfoByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

}