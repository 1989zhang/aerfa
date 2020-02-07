package com.zhangysh.accumulate.ui.comm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommDivisionDto;
import com.zhangysh.accumulate.ui.comm.service.IDivisionService;

/**
 * 行政区划调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月25日
 */
@Controller
@RequestMapping("/comm/division")
public class DivisionController {

    private String prefix="/comm/division";//返回界面路径即前缀

	@Autowired
	private IDivisionService divisionService;
	
	/**
	 *跳转到sys行政区划页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的行政区划页面
	 ****/
	@RequestMapping(value="/to_division")
	public String toSysDivision(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/division";
	}
	
	/****
	 *获取展示行政区划列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefcommDivision division) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefcommDivisionDto divisionDto=new AefcommDivisionDto();
		divisionDto.setPageInfo(pageInfo);divisionDto.setDivision(division);
		return divisionService.getList(aerfatoken,divisionDto);
	}
	
	/**
	 *跳转到sys行政区划新增页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add/{parentId}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("parentId") Long parentId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		//查询出来id和名称
		String divisionJsonStr=divisionService.getSingle(aerfatoken, parentId);
		AefcommDivision parentDivision=JSON.parseObject(divisionJsonStr,AefcommDivision.class);
		AefcommDivision division=new AefcommDivision();
		division.setParentId(parentDivision.getId());
		division.setParentName(parentDivision.getName());
		division.setLevel(parentDivision.getLevel());
		modelMap.put("division",division);
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
	/*****
	 * 验证行政区划区划代码唯一性 
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param division 要验证的对象内含区划代码属性和id,id为了排除自己
	 ****/
	@RequestMapping(value="/check_code_unique")
    @ResponseBody
	public String checkCodeUnique(HttpServletRequest request, ModelMap modelMap,AefcommDivision division) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return divisionService.checkCodeUnique(aerfatoken,division);
	}
    /***
	 *保存填写的行政区划对象
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param division 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefcommDivision division) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return divisionService.saveAdd(aerfatoken, division);
	}
	
	/****
	 *修改行政区划，先获取行政区划信息
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retDivisionStr=divisionService.getSingle(aerfatoken, id);
		AefcommDivision division=JSON.parseObject(retDivisionStr,AefcommDivision.class);
		modelMap.put("prefix", prefix);
		modelMap.put("division", division);
		return prefix+"/edit";
	}
	
	/***
	 *删除行政区划对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return divisionService.deleteDivisionByIds(aerfatoken, ids);
    }
	
	/***
	 *展示行政区划的树形结构
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 ****/
	@RequestMapping(value="/tree")
    @ResponseBody
    public String getTree(HttpServletRequest request, ModelMap modelMap,AefcommDivision division){	
		String aerfatoken=HttpStorageUtil.getToken(request);
		return divisionService.getTree(aerfatoken,division.getId()==null?SysDefineConstant.DB_DIVISION_TOP_PARENT_ID:division.getId());
	}
	
	/**
	 * 获取city-picker后台json数据
	 ***/
	@RequestMapping(value="/picker_data")
    @ResponseBody
    public String getCityPickerData(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return divisionService.getCityPickerData(aerfatoken);
	}
}