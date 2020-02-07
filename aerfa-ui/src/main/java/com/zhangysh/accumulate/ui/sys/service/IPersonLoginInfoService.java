package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonLoginInfoDto;


/*****
 * 个人登录相关调用后台方法
 * @author zhangysh
 * @date 2019年05月20日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IPersonLoginInfoService {

	/****
	 *分页显示个人登录信息
	 *@param aerfatoken token对象
	 *@param PersonLoginInfoDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/person_login_info/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysPersonLoginInfoDto PersonLoginInfoDto);

	/****
	 *获取单个个人登录信息
	 *@param aerfatoken token对象
	 *@param id 个人登录的id
	 ***/
	@RequestMapping(value = "/sys/person_login_info/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

}