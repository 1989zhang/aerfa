package com.zhangysh.accumulate.back.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
}
