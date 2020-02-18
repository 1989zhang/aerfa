package com.zhangysh.accumulate.ui.comm.controller;

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
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.pojo.comm.viewobj.AefcommInfoPublishVo;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommInfoPublishDto;
import com.zhangysh.accumulate.ui.comm.service.IInfoPublishService;

/**
 * 发布调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Controller
@RequestMapping("/comm/info_publish")
public class InfoPublishController {

    private String prefix="/comm/info_publish";//返回界面路径即前缀

	@Autowired
	private IInfoPublishService infoPublishService;
	
	/**
	 * 跳转到sys发布页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的发布页面
	 ****/
	@RequiresPermissions("comm:infoPublish:view")
	@RequestMapping(value="/to_info_publish")
	public String toSysInfoPublish(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/info_publish";
	}
	
	/****
	 * 获取展示发布列表，且分页显示
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequiresPermissions("comm:infoPublish:list")
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefcommInfoPublish infoPublish) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefcommInfoPublishDto infoPublishDto=new AefcommInfoPublishDto();
		infoPublishDto.setPageInfo(pageInfo);infoPublishDto.setInfoPublish(infoPublish);
		return infoPublishService.getList(aerfatoken,infoPublishDto);
	}
	
	/**
	 * 跳转到sys发布新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequiresPermissions("comm:infoPublish:add")
	@RequestMapping(value="/to_add")
	public String toAdd(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
    /***
	 * 保存填写的发布对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param infoPublish 保存的对象
	 ******/
	@RequiresPermissions(value={"comm:infoPublish:add","comm:infoPublish:edit"},logical= Logical.OR)
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefcommInfoPublish infoPublish) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return infoPublishService.saveAdd(aerfatoken, infoPublish);
	}
	
	/****
	 * 修改发布，先获取发布信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @return templates下的页面
	 ****/
	@RequiresPermissions("comm:infoPublish:edit")
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retInfoPublishStr=infoPublishService.getSingle(aerfatoken, id);
		AefcommInfoPublishVo infoPublishVo=JSON.parseObject(retInfoPublishStr,AefcommInfoPublishVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("infoPublish", infoPublishVo);
		return prefix+"/edit";
	}
	
	/***
	 * 删除发布对象，可以删除多个，中间英文,隔开
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequiresPermissions("comm:infoPublish:remove")
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return infoPublishService.deleteInfoPublishByIds(aerfatoken, ids);
    }
}