package com.zhangysh.accumulate.ui.webim.service;

import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimPersonVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;

/*****
 * 即时通讯webim个人拓展信息相关
 * @author zhangysh
 * @date 2019年11月24日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IWebimPersonService {

	/****
	 * 获取个人拓展信息
	 * @param aerfatoken token对象
	 * @param searchDto 查询条件对象
	 ***/
	@RequestMapping(value = "/webim/person/get_information",method = RequestMethod.POST)
	public String getInformation(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimSearchDto searchDto);

	/***
	 * 保存修改的我的个人信息
	 * @param aerfatoken token对象
	 * @param webimPersonVo 保存的我的个人信息
	 ***/
	@RequestMapping(value = "/webim/person/save_my_information",method = RequestMethod.POST)
	public String saveMyInformation(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefwebimPersonVo webimPersonVo);
}
