package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysOrgDto;

/*****
 * 组织部门相关调用后台方法
 * @author zhangysh
 * @date 2018年10月28日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IOrgService {

	/****
	 *一次性全部展示所有组织单位，且带树形结构 
	 *@param aerfatoken token对象
	 ***/
	@RequestMapping(value = "/sys/org/tree",method = RequestMethod.POST)
	public String getTree(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);

	/****
	 *一次性全部展示所有组织单位，且带父子结构 
	 *@param aerfatoken token对象
	 *@param orgDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/org/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysOrgDto orgDto);

	/****
	 *获取单个单位信息，且包含父单位名称 
	 *@param aerfatoken token对象
	 *@param id 单位id
	 ***/
	@RequestMapping(value = "/sys/org/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *保存新增的部门单位信息 
	 *@param aerfatoken token对象
	 *@param org 要保存的单位对象
	 ***/
	@RequestMapping(value = "/sys/org/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysOrg org);

	/****
	 *验证单位全称是否唯一
	 *@param aerfatoken token对象
	 *@param org 要检查的单位包括：名称和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/sys/org/check_org_unique",method = RequestMethod.POST)
	public String checkOrgUnique(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysOrg org);

	/****
	 *根据id删除某个单位
	 *@param request 请求对象
	 *@param id 单位主键id
	 ***/
	@RequestMapping(value = "/sys/org/delete",method = RequestMethod.POST)
	public String deleteOrgById(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

}
