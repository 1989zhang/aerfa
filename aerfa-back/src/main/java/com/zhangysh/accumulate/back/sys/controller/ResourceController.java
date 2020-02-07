package com.zhangysh.accumulate.back.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.service.IResourceService;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysResourceDto;

/*****
 * 资源相关方法，包括：系统、目录、菜单、按钮。
 * @author zhangysh
 * @date 2019年4月6日
 *****/
@Controller
@RequestMapping("/sys/resource")
public class ResourceController extends BaseController{

	@Autowired
	private IResourceService resourceService;
    @Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 *获取树形结构的所有资源数据 
	 *@param request 请求对象
	 *@return 树形资源对象JSON
	 ****/
	@RequestMapping(value="/tree",method = RequestMethod.POST)
    @ResponseBody
	public String getTree(HttpServletRequest request) {
		return JSON.toJSONString(resourceService.listAllResourceWithTreeStructure());
	}
	
	/****
	 *获取展示资源信息列表，且带父子结构所以页面不排序和分页 
	 *@param request 请求对象
	 *@param resourceDto 查询对象
	 *@return 获取到的资源对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
    @ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysResourceDto resourceDto) {
		AefsysResource sysResourceParam=resourceDto.getResource();
		//带查询条件列表，不分父子结构
		if( StringUtil.isNotEmpty(sysResourceParam.getResourceName()) || StringUtil.isNotEmpty(sysResourceParam.getIdentify()) || StringUtil.isNotEmpty(sysResourceParam.getResourceType()) )  {
			return JSON.toJSONStringWithDateFormat(resourceService.listResource(sysResourceParam),UtilConstant.NORMAL_MIDDLE_DATE);
		}else {//第一次全加载父子结构的部门列表
			return JSON.toJSONStringWithDateFormat(resourceService.listAllResourceWithParentStructure(),UtilConstant.NORMAL_MIDDLE_DATE);
		}
	}
	
	/****
	 *获取一个资源信息，且有父资源名称
	 *@param request 请求对象
	 *@param resourceId 资源id
	 *@return 获取到的资源对象JSON
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long resourceId) {
		return JSON.toJSONString(resourceService.getResourceWithParentResourceNameById(resourceId));
	}
	
	/****
	 *保存新增的资源位和修改资源信息 
	 *@param request 请求对象
	 *@param resource 新增和修改的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveResource(HttpServletRequest request,@RequestBody AefsysResource resource) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if(resource.getId()!=null) {//修改方法
			resource.setUpdateTime(DateOperate.getCurrentUtilDate());
			resource.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(resourceService.updateResource(resource));
		}else {//新增方法
			resource.setCreateTime(DateOperate.getCurrentUtilDate());
			resource.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(resourceService.insertResource(resource));
		}
	}
	
	/****
	 *验证资源标识是否唯一
	 *@param request 请求对象
	 *@param resource 要检查的资源包括：标识和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/check_resource_unique",method = RequestMethod.POST)
	@ResponseBody
	public String checkResourceUnique(HttpServletRequest request,@RequestBody AefsysResource resource) {
		return toUniqueResultStr(resourceService.checkResourceUnique(resource).size());
	}

	/****
	 *根据id删除某个资源
	 *@param request 请求对象
	 *@param resourceId 要删除的资源id
	 ***/
	@Log(system="后台管理系统",module="系统管理",menu="菜单管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteResourceById(HttpServletRequest request,@RequestBody Long resourceId) {
		return toHandlerResultStr(resourceService.deleteResourceById(resourceId));
	}
}
