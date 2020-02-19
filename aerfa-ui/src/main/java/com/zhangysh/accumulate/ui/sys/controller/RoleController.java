package com.zhangysh.accumulate.ui.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysRoleDto;
import com.zhangysh.accumulate.ui.sys.service.IRoleService;

/**
 * 角色调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月16日
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController {

    private String prefix="/sys/role";//返回界面路径即前缀

	@Autowired
	private IRoleService roleService;
	
	/**
	 *跳转到sys角色页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的角色页面
	 ****/
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value="/to_role")
	public String toSysRole(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/role";
	}
	
	/****
	 *获取展示角色列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequiresPermissions("sys:role:list")
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysRole role) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysRoleDto roleDto=new AefsysRoleDto();
		roleDto.setPageInfo(pageInfo);roleDto.setRole(role);
		return roleService.getList(aerfatoken,roleDto);
	}
	
	/**
	 *跳转到sys角色新增页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value="/to_add")
	public String toAdd(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
    /***
	 *保存填写的角色对象
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param role 保存的对象
	 ******/
	@RequiresPermissions(value={"sys:role:add","sys:role:edit"},logical= Logical.OR)
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysRole role) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return roleService.saveAdd(aerfatoken, role);
	}

	/**
	 * 检查角色编码的唯一性
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @param role 要检查的角色对象，包含roleCode标识属性
	 ****/
	@RequestMapping(value="/check_role_unique")
	@ResponseBody
	public String checkRoleUnique(HttpServletRequest request, ModelMap modelMap,AefsysRole role){
		String aerfatoken=HttpStorageUtil.getToken(request);
		return roleService.checkRoleUnique(aerfatoken,role);
	}


	
	/****
	 *修改角色，先获取角色信息
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的页面
	 ****/
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retRoleStr=roleService.getSingle(aerfatoken, id);
		AefsysRole role=JSON.parseObject(retRoleStr,AefsysRole.class);
		modelMap.put("prefix", prefix);
		modelMap.put("role", role);
		return prefix+"/edit";
	}
	
	/***
	 *删除角色对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequiresPermissions("sys:role:remove")
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return roleService.deleteRoleByIds(aerfatoken, ids);
    }
}