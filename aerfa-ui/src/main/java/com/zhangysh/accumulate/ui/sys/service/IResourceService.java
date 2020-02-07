package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysResourceDto;

/*****
 * 资源相关调用后台方法
 * @author zhangysh
 * @date 2019年4月8日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IResourceService {
	
	/****
	 *一次性全部展示所有资源，且带树形结构 
	 *@param aerfatoken token对象
	 ***/
	@RequestMapping(value = "/sys/resource/tree",method = RequestMethod.POST)
	public String getTree(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);

	/****
	 *一次性全部展示所有资源，且带父子结构 
	 *@param aerfatoken token对象
	 *@param resourceDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/resource/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysResourceDto resourceDto);

	/****
	 *获取单个资源信息，且包含父资源名称 
	 *@param aerfatoken token对象
	 *@param id 资源主键id
	 ***/
	@RequestMapping(value = "/sys/resource/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *保存新增的资源信息 
	 *@param aerfatoken token对象
	 *@param resource 要保存的对象
	 ***/
	@RequestMapping(value = "/sys/resource/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysResource resource);

	/****
	 *验证资源标识是否唯一
	 *@param aerfatoken token对象
	 *@param resource 要检查的资源包括：标识和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/sys/resource/check_resource_unique",method = RequestMethod.POST)
	public String checkResourceUnique(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysResource resource);

	/****
	 *根据id删除某个资源
	 *@param request 请求对象
	 *@param id 要删除的资源id
	 ***/
	@RequestMapping(value = "/sys/resource/delete",method = RequestMethod.POST)
	public String deleteResourceById(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

}
