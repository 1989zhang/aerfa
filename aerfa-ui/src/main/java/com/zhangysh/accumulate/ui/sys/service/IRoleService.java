package com.zhangysh.accumulate.ui.sys.service;

import com.zhangysh.accumulate.pojo.sys.transobj.AefsysRoleDataPermissionDto;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysRoleResourceDto;
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
	 *@param role 要保存的角色对象
	 ***/
	@RequestMapping(value = "/sys/role/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysRole role);

	/****
	 * 验证角色编码标识是否唯一
	 * @param aerfatoken token对象
	 * @param role 要检查的角色包括：标识和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/sys/role/check_role_unique",method = RequestMethod.POST)
	public String checkRoleUnique(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysRole role);

	/****
	 *删除角色对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的角色ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/role/delete",method = RequestMethod.POST)
	public String deleteRoleByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

	/****
	 * 展示所有资源且带父子结构,且根据角色是否被授权打上标签
	 * @param aerfatoken token对象
	 * @param id 角色的ID
	 **/
	@RequestMapping(value = "/sys/role/role_resource",method = RequestMethod.POST)
	public String getRoleResource(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 * 根据角色获取对应的数据权限对象
	 * @param aerfatoken token对象
	 * @param id 角色的ID
	 **/
	@RequestMapping(value = "/sys/role/role_data_permission",method = RequestMethod.POST)
	public String getRoleDataPermission(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 * 保存角色对应的资源
	 * @param aerfatoken token对象
	 * @param roleResourceDto 保存的角色对应资源对象
	 **/
	@RequestMapping(value = "/sys/role/save_role_resource",method = RequestMethod.POST)
	public String saveRoleResource(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysRoleResourceDto roleResourceDto);

	/**
	 * 保存角色对应的单个数据权限
	 * @param aerfatoken token对象
	 * @param roleDataPermissionDto 保存的角色对应数据权限对象
	 **/
	@RequestMapping(value = "/sys/role/save_role_data_permission",method = RequestMethod.POST)
	public String saveRoleDataPermission(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysRoleDataPermissionDto roleDataPermissionDto);



}