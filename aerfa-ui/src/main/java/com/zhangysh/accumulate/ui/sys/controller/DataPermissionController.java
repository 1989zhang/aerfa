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
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysDataPermissionVo;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysDataPermissionDto;
import com.zhangysh.accumulate.ui.sys.service.IDataPermissionService;

/**
 * 数据权限调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Controller
@RequestMapping("/sys/data_permission")
public class DataPermissionController {

    private String prefix="/sys/data_permission";//返回界面路径即前缀

	@Autowired
	private IDataPermissionService dataPermissionService;
	
	/**
	 * 跳转到sys数据权限页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的数据权限页面
	 ****/
	@RequiresPermissions("sys:dataPermission:view")
	@RequestMapping(value="/to_data_permission")
	public String toSysDataPermission(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/data_permission";
	}
	
	/****
	 * 获取展示数据权限列表，且分页显示
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequiresPermissions("sys:dataPermission:list")
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysDataPermission dataPermission) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysDataPermissionDto dataPermissionDto=new AefsysDataPermissionDto();
		dataPermissionDto.setPageInfo(pageInfo);dataPermissionDto.setDataPermission(dataPermission);
		return dataPermissionService.getList(aerfatoken,dataPermissionDto);
	}
	
	/**
	 * 跳转到sys数据权限新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequiresPermissions("sys:dataPermission:add")
	@RequestMapping(value="/to_add")
	public String toAdd(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
    /***
	 * 保存填写的数据权限对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param dataPermission 保存的对象
	 ******/
	@RequiresPermissions(value={"sys:dataPermission:add","sys:dataPermission:edit"},logical= Logical.OR)
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysDataPermission dataPermission) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataPermissionService.saveAdd(aerfatoken, dataPermission);
	}
	
	/****
	 * 修改数据权限，先获取数据权限信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @return templates下的页面
	 ****/
	@RequiresPermissions("sys:dataPermission:edit")
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retDataPermissionStr=dataPermissionService.getSingle(aerfatoken, id);
		AefsysDataPermissionVo dataPermissionVo=JSON.parseObject(retDataPermissionStr,AefsysDataPermissionVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("dataPermission", dataPermissionVo);
		return prefix+"/edit";
	}
	
	/***
	 * 删除数据权限对象，可以删除多个，中间英文,隔开
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequiresPermissions("sys:dataPermission:remove")
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataPermissionService.deleteDataPermissionByIds(aerfatoken, ids);
    }
}