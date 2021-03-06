package com.zhangysh.accumulate.back.sys.controller;

import com.zhangysh.accumulate.back.sys.service.IDataPermissionService;
import com.zhangysh.accumulate.back.sys.service.IResourceService;
import com.zhangysh.accumulate.back.sys.service.IRoleResourceService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRoleResource;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysRoleDataPermissionDto;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysRoleResourceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysRoleDto;
import com.zhangysh.accumulate.back.sys.service.IRoleService;

/**
 * 角色调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月16日
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private IRoleService roleService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IRoleResourceService roleResourceService;
	@Autowired
	private IDataPermissionService dataPermissionService;
	/****
	 * 获取展示角色信息列表
	 * @param request 请求对象
	 * @param roleDto 分页和查询对象
	 * @return 获取到的角色对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysRoleDto roleDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",roleDto.getPageInfo().getPageNum(),roleDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=roleService.listPageRole(roleDto.getPageInfo(),roleDto.getRole());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个角色,以便修改
	 * @param request 请求对象
     * @param id 角色主键ID
     * @return 角色信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle角色主键信息:{}",id);
		return JSON.toJSONString(roleService.getRoleById(id));
	}
	
	/****
	 *保存新增和修改的角色信息 
	 *@param request 请求对象
	 *@param role 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveRole(HttpServletRequest request,@RequestBody AefsysRole role) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( role.getId()!=null ) {//修改方法
		    role.setUpdateTime(DateOperate.getCurrentUtilDate());
			role.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(roleService.updateRole(role));
		} else {//新增方法
			role.setCreateTime(DateOperate.getCurrentUtilDate());
			role.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(roleService.insertRole(role));
		}
	}

	/****
	 * 验证角色编码标识是否唯一
	 * @param request 请求对象
	 * @param role 要检查的资源包括：标识和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/check_role_unique",method = RequestMethod.POST)
	@ResponseBody
	public String checkRoleUnique(HttpServletRequest request,@RequestBody AefsysRole role) {
		return toUniqueResultStr(roleService.checkRoleUnique(role).size());
	}

	/****
	 *删除角色对象，可以删除多个.
	 *@param request 请求对象
	 *@param ids 要删除的角色ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="系统管理",menu="角色管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteRoleByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(roleService.deleteRoleByIds(ids));
	}

	/****
	 * 展示所有带父子资源结构,且根据角色是否被授权打上标签
	 * @param request 请求对象
	 * @param id 角色的ID
	 **/
	@RequestMapping(value = "/role_resource",method = RequestMethod.POST)
	@ResponseBody
	public String getRoleResource(HttpServletRequest request, @RequestBody Long id){
		return JSON.toJSONStringWithDateFormat(resourceService.getResourceListByRoleId(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 根据角色获取对应的数据权限对象
	 * @param request 请求对象
	 * @param id 角色的ID
	 **/
	@RequestMapping(value = "/role_data_permission",method = RequestMethod.POST)
	@ResponseBody
	public String getRoleDataPermission(HttpServletRequest request, @RequestBody Long id){
		return JSON.toJSONStringWithDateFormat(dataPermissionService.getDataPermissionListByRoleId(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存角色对应的资源
	 * @param request 请求对象
	 * @param roleResourceDto 保存的角色对应资源对象
	 ***/
	@Log(system="后台管理系统",module="系统管理",menu="角色管理",button="角色资源",saveParam=true)
	@RequestMapping(value = "/save_role_resource",method = RequestMethod.POST)
	@ResponseBody
	public String saveRoleResource(HttpServletRequest request,@RequestBody AefsysRoleResourceDto roleResourceDto) {
		int countEffectRows=0;
		//首先是删除角色对应的资源
		int deleteRows=roleResourceService.deleteRoleResourceByRoleId(roleResourceDto.getRoleId());
		//再新增角色对应的资源
		String[] resourceIdArr=roleResourceDto.getResourceIds().split(MarkConstant.MARK_SPLIT_EN_COMMA);
		countEffectRows=deleteRows;
		for(int i=0;i<resourceIdArr.length;i++){
			if(StringUtil.isNotEmpty(resourceIdArr[i])){
				AefsysRoleResource roleResource=new AefsysRoleResource();
				roleResource.setRoleId(roleResourceDto.getRoleId());
				roleResource.setResourceId(Long.valueOf(resourceIdArr[i]));
				roleResourceService.insertRoleResource(roleResource);
				countEffectRows=countEffectRows+1;
			}
		}
		return toHandlerResultStr(countEffectRows);
	}

	/****
	 * 保存角色对应的数据权限
	 * @param request 请求对象
	 * @param roleDataPermissionDto 保存的角色对应数据权限对象
	 ***/
	@Log(system="后台管理系统",module="系统管理",menu="角色管理",button="角色数据权限",saveParam=true)
	@RequestMapping(value = "/save_role_data_permission",method = RequestMethod.POST)
	@ResponseBody
	public String saveRoleDataPermission(HttpServletRequest request, @RequestBody AefsysRoleDataPermissionDto roleDataPermissionDto) {
		int countEffectRows=0;
		//修改数据权限的对应角色字段即可
		String[] dataPermissionIdArr=roleDataPermissionDto.getDataPermissionIds().split(MarkConstant.MARK_SPLIT_EN_COMMA);
		boolean cancelDataPermission=true;
		for(int i=0;i<dataPermissionIdArr.length;i++){
			if(StringUtil.isNotEmpty(dataPermissionIdArr[i])){
				AefsysDataPermission dataPermission=new AefsysDataPermission();
				dataPermission.setId(Long.valueOf(dataPermissionIdArr[i]));
				dataPermission.setRoleId(roleDataPermissionDto.getRoleId());
				dataPermissionService.updateDataPermission(dataPermission);
				countEffectRows=countEffectRows+1;
				cancelDataPermission=false;
			}
		}
		//取消角色关联的数据权限
		if(cancelDataPermission){
			countEffectRows=dataPermissionService.cancelRoleDataPermission(roleDataPermissionDto.getRoleId());
		}
		return toHandlerResultStr(countEffectRows);
	}
}
