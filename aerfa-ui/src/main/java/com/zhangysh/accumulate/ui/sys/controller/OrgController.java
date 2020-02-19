package com.zhangysh.accumulate.ui.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.common.pojo.CodeMsg;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysOrgDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysOrgVo;
import com.zhangysh.accumulate.ui.sys.service.IOrgService;


/*****
 * 单位组织相关方法
 * @author zhangysh
 * @date 2018年8月17日
 *****/
@Controller
@RequestMapping("/sys/org")
public class OrgController {
	
	private String prefix="/sys/org";//返回界面路径即前缀
	private static final Logger logger=LoggerFactory.getLogger(OrgController.class);
	
	@Resource
	private IOrgService orgService;
	/**
	 *跳转到sys单位页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequiresPermissions("sys:org:view")
	@RequestMapping(value="/to_org")
	public String toSysOrg(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/org";
	}

	/****
	 *一次性全部展示所有组织单位，且带父子结构 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@param org 查询对象
	 ***/
	@RequiresPermissions("sys:org:list")
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,AefsysOrg org) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysOrgDto orgDto=new AefsysOrgDto();
		orgDto.setOrg(org);
		return orgService.getList(aerfatoken, orgDto);
	}
	
	/**
	 *跳转到sys单位新增页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequiresPermissions("sys:org:add")
	@RequestMapping(value="/to_add/{parentId}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("parentId") Long parentId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retOrgStr=orgService.getSingle(aerfatoken,parentId);
		AefsysOrgVo orgVo=JSON.parseObject(retOrgStr, AefsysOrgVo.class);
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("org",orgVo);
		return prefix+"/add";
	}
	
	/*****
	 *检查单位全称名称的唯一性 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param org 要检查的单位包括：名称和id,id为了排除自己
	 ****/
	@RequestMapping(value="/check_org_unique")
    @ResponseBody
	public String checkOrgUnique(HttpServletRequest request, ModelMap modelMap,AefsysOrg org) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return orgService.checkOrgUnique(aerfatoken,org);
	}
	
	/***
	 *点击父级部门展示单位,直接跳转
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param id 已选中的父单位id
	 ***/
	@RequestMapping(value="/to_select_tree")
	public String ToSelectTree(HttpServletRequest request,  ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/tree";
	}
	
	/***
	 *保存填写的单位信息 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param org 保存的对象
	 ******/
	@RequiresPermissions(value={"sys:org:add","sys:org:edit"},logical= Logical.OR)
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysOrg org) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return orgService.saveAdd(aerfatoken,org);
	}
	
	/***
	 *删除单位部门
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param id 要删除的单位id,单位只能一个个删除，因为是路径获取参数所以不分id和ids
	 ***/
	@RequiresPermissions("sys:org:remove")
	@RequestMapping(value="/remove/{id}")
    @ResponseBody
	public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return orgService.deleteOrgById(aerfatoken, id);
	}
	
	/***
	 *展示所有组织部门的树形结构
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 ****/
	@RequestMapping(value="/tree")
    @ResponseBody
    public String getTree(HttpServletRequest request, ModelMap modelMap){	
		String aerfatoken=HttpStorageUtil.getToken(request);
		return orgService.getTree(aerfatoken);
	}
	
	/**
	 *跳转到sys单位修改页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequiresPermissions("sys:org:edit")
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retOrgStr=orgService.getSingle(aerfatoken,id);
		AefsysOrgVo orgVo=JSON.parseObject(retOrgStr, AefsysOrgVo.class);
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("org",orgVo);
		return prefix+"/edit";
	}
}
