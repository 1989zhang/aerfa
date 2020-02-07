package com.zhangysh.accumulate.back.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.service.IOrgService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysOrgDto;


/**
 * 组织单位部门相关方法
 * @author zhangysh
 * @date 2018年9月12日
 */
@Controller
@RequestMapping("/sys/org")
public class OrgController extends BaseController{

	@Autowired
	private IOrgService orgService;
    @Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 *获取树形结构的所有单位数据 
	 *@param request 请求对象
	 *@return 树形单位对象JSON
	 ****/
	@RequestMapping(value="/tree",method = RequestMethod.POST)
    @ResponseBody
	public String getTree(HttpServletRequest request) {
		return JSON.toJSONString(orgService.listAllOrgWithTreeStructure());
	}
	
	/****
	 *获取展示部门信息列表，且带父子结构所以页面不排序和分页 
	 *@param request 请求对象
	 *@param orgDto 查询对象
	 *@return 获取到的单位对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
    @ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysOrgDto orgDto) {
		AefsysOrg sysOrgParam=orgDto.getOrg();
		//带查询条件列表，不分父子结构
		if( StringUtil.isNotEmpty(sysOrgParam.getFullName()) ||  StringUtil.isNotNull(sysOrgParam.getStatus()) )  {
			return JSON.toJSONStringWithDateFormat(orgService.listOrg(sysOrgParam),UtilConstant.NORMAL_MIDDLE_DATE);
		}else {//第一次全加载父子结构的部门列表
			return JSON.toJSONStringWithDateFormat(orgService.listAllOrgWithParentStructure(),UtilConstant.NORMAL_MIDDLE_DATE);
		}
	}
	
	/****
	 *获取一个单位信息，且有父单位名称
	 *@param request 请求对象
	 *@param orgId 单位部门id
	 *@return 获取到的单位对象JSON
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long orgId) {
		return JSON.toJSONString(orgService.getOrgWithParentOrgNameById(orgId));
	}
	
	/****
	 *保存新增的部门单位和修改部门单位信息 
	 *@param request 请求对象
	 *@param org 新增对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveOrg(HttpServletRequest request,@RequestBody AefsysOrg org) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if(org.getId()!=null) {//修改方法
			org.setUpdateTime(DateOperate.getCurrentUtilDate());
			org.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(orgService.updateOrg(org));
		}else {//新增方法
			org.setCreateTime(DateOperate.getCurrentUtilDate());
			org.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(orgService.insertOrg(org));
		}
	}
	
	/****
	 *验证单位全称是否唯一
	 *@param request 请求对象
	 *@param org 要检查的单位包括：名称和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/check_org_unique",method = RequestMethod.POST)
	@ResponseBody
	public String checkOrgUnique(HttpServletRequest request,@RequestBody AefsysOrg org) {
		return toUniqueResultStr(orgService.checkOrgUnique(org).size());
	}

	/****
	 *根据id删除某个单位
	 *@param request 请求对象
	 *@param orgId 要验证的单位id
	 ***/
	@Log(system="后台管理系统",module="系统管理",menu="部门管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteOrgById(HttpServletRequest request,@RequestBody Long orgId) {
		return toHandlerResultStr(orgService.deleteOrgById(orgId));
	}
}
