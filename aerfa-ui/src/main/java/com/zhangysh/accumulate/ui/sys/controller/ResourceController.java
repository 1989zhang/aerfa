package com.zhangysh.accumulate.ui.sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysResourceDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysResourceVo;
import com.zhangysh.accumulate.ui.sys.service.IResourceService;

/*****
 * 资源菜单相关方法
 * @author zhangysh
 * @date 2018年8月17日
 *****/
@Controller
@RequestMapping("/sys/resource")
public class ResourceController {
	
	private String prefix="/sys/resource";//返回界面路径即前缀
	private static final Logger logger=LoggerFactory.getLogger(ResourceController.class);
	
	@Resource
	private IResourceService resourceService;
	
	/**
	 *跳转到sys资源页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequiresPermissions("system:resource:view")
	@RequestMapping(value="/to_resource")
	public String toSysResource(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/resource";
	}
	
	/****
	 * 一次性全部展示所有资源，且带父子结构
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @param resource 资源查询对象
	 * @return 所有资源集合
	 ***/
	@RequiresPermissions("system:resource:list")
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,AefsysResource resource) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysResourceDto resourceDto=new AefsysResourceDto();
		resourceDto.setResource(resource);
		return resourceService.getList(aerfatoken, resourceDto);
	}
	
	/**
	 * 跳转到sys资源新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @param parentId 资源父级ID
	 * @return templates下的单位新增页面
	 ****/
	@RequiresPermissions("system:resource:add")
	@RequestMapping(value="/to_add/{parentId}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("parentId") Long parentId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retResourceStr=resourceService.getSingle(aerfatoken,parentId);
		AefsysResourceVo resourceVo=JSON.parseObject(retResourceStr, AefsysResourceVo.class);
		
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("resource",resourceVo);
		return prefix+"/add";
	}
	
	/*****
	 *检查资源标识的唯一性 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param resource 要检查的资源对象，包含identify标识属性
	 ****/
	@RequestMapping(value="/check_resource_unique")
    @ResponseBody
	public String checkResourceUnique(HttpServletRequest request, ModelMap modelMap,AefsysResource resource) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return resourceService.checkResourceUnique(aerfatoken,resource);
	}
	
	/***
	 *点击父级资源展示资源树,直接跳转
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 ***/
	@RequestMapping(value="/to_select_tree")
	public String ToSelectTree(HttpServletRequest request,  ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/tree";
	}
	
	/***
	 *保存填写的资源信息 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param resource 保存的对象
	 ******/
	@RequiresPermissions(value={"system:resource:add","sys:resource:edit"},logical= Logical.OR)
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysResource resource) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return resourceService.saveAdd(aerfatoken, resource);
	}

	/**
	 *跳转到sys资源修改页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequiresPermissions("system:resource:edit")
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retResourceStr=resourceService.getSingle(aerfatoken,id);
		AefsysResourceVo resourceVo=JSON.parseObject(retResourceStr, AefsysResourceVo.class);
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("resource",resourceVo);
		return prefix+"/edit";
	}

	/***
	 *删除资源
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param id 要删除的资源id
	 ***/
	@RequiresPermissions("system:resource:remove")
	@RequestMapping(value="/remove/{id}")
    @ResponseBody
	public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return resourceService.deleteResourceById(aerfatoken, id);
	}
	
	/***
	 *展示所有资源的树形结构
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 ****/
	@RequestMapping(value="/tree")
    @ResponseBody
    public String getTree(HttpServletRequest request, ModelMap modelMap){
		String aerfatoken=HttpStorageUtil.getToken(request);
        return resourceService.getTree(aerfatoken);
	}

}
