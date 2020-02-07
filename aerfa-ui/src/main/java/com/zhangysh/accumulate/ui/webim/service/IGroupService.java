package com.zhangysh.accumulate.ui.webim.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimGroupDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;

/*****
 * 即时通讯webim群组相关方法
 * @author zhangysh
 * @date 2019年10月20日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IGroupService {

	/****
	 * 保存新增的群组申请
	 * @param aerfatoken token对象
	 * @param Friend 要保存的好友对象
	 ***/
	@RequestMapping(value = "/webim/group/save_apply",method = RequestMethod.POST)
	public String saveApply(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimApplyDto apply);
	
	/****
	 * 分页显示群组信息
	 * @param aerfatoken token对象
	 * @param GroupDto 查询条件
	 ***/
	@RequestMapping(value = "/webim/group/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimGroupDto GroupDto);
	
	/****
	 * 获取查找信息，包括查找群组条数
	 * @param searchDto 查询条件对象
	 ***/
	@RequestMapping(value = "/webim/group/search_page",method = RequestMethod.POST)
	public String getSearchPage(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimSearchDto searchDto);

	/****
	 * 获取查找信息，包括查找群组详细信息
	 * @param searchDto 查询条件对象
	 ***/
	@RequestMapping(value = "/webim/group/search_info",method = RequestMethod.POST)
	public String getSearchInfo(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimSearchDto searchDto);

	/****
	 * 获取普通群拓展信息
	 * @param searchDto 查询条件对象
	 ***/
	@RequestMapping(value = "/webim/group/get_information",method = RequestMethod.POST)
	public String getInformation(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefwebimSearchDto searchDto);

}
