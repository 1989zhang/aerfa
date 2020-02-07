package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysRoleDto;


/*****
 * 角色相关调用后台方法
 * @author zhangysh
 * @date 2019年05月16日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IRoleService {

	/****
	 *分页显示角色信息
	 *@param aerfatoken token对象
	 *@param RoleDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/role/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysRoleDto RoleDto);

	/****
	 *获取单个角色信息
	 *@param aerfatoken token对象
	 *@param id 角色的id
	 ***/
	@RequestMapping(value = "/sys/role/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *保存新增的角色信息 
	 *@param aerfatoken token对象
	 *@param Role 要保存的角色对象
	 ***/
	@RequestMapping(value = "/sys/role/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysRole role);

	/****
	 *删除角色对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的角色ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/role/delete",method = RequestMethod.POST)
	public String deleteRoleByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

}