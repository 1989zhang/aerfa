package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysDataPermissionDto;


/*****
 * 数据权限相关调用后台方法
 * @author zhangysh
 * @date 2020年02月16日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IDataPermissionService {

	/****
	 * 分页显示数据权限信息
	 * @param aerfatoken token对象
	 * @param DataPermissionDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/data_permission/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefsysDataPermissionDto DataPermissionDto);

	/****
	 * 获取单个数据权限信息
	 * @param aerfatoken token对象
	 * @param id 数据权限的id
	 ***/
	@RequestMapping(value = "/sys/data_permission/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody Long id);

	/****
	 * 保存新增的数据权限信息 
	 * @param aerfatoken token对象
	 * @param dataPermission 要保存的数据权限对象
	 ***/
	@RequestMapping(value = "/sys/data_permission/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefsysDataPermission dataPermission);

	/****
	 * 删除数据权限对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的数据权限ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/data_permission/delete",method = RequestMethod.POST)
	public String deleteDataPermissionByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody String ids);

}