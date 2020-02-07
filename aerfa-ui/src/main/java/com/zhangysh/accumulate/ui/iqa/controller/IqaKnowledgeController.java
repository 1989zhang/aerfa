package com.zhangysh.accumulate.ui.iqa.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaKnowledgeDto;
import com.zhangysh.accumulate.pojo.iqa.viewobj.AefiqaCategoryVo;
import com.zhangysh.accumulate.pojo.iqa.viewobj.AefiqaKnowledgeVo;
import com.zhangysh.accumulate.ui.iqa.service.IqaKnowledgeService;

/*****
 * 智能问答，知识库相关方法
 * @author zhangysh
 * @date 2019年11月1日
 *****/
@Controller
@RequestMapping("/iqa/knowledge")
public class IqaKnowledgeController{

	@Autowired
	private IqaKnowledgeService knowledgeService;
	
	private String prefix="/iqa/knowledge";//返回界面路径即前缀

	/**
	 *跳转到知识库页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的配置页面
	 ****/
	@RequestMapping(value="/to_library")
	public String toLibrary(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/library";
	}
	
	/**
	 *跳转到标准知识库页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的配置页面
	 ****/
	@RequestMapping(value="/to_standard")
	public String toStandard(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/standard";
	}
	
	/**
	 *跳转到未知知识库页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的配置页面
	 ****/
	@RequestMapping(value="/to_unknown")
	public String toUnknown(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/unknown";
	}
	
	
	/***
	 * 展示知识分类的树形结构
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 ****/
	@RequestMapping(value="/category_tree")
    @ResponseBody
    public String getCategoryTree(HttpServletRequest request, ModelMap modelMap){	
		String aerfatoken=HttpStorageUtil.getToken(request);
		return knowledgeService.getCategoryTree(aerfatoken);
	}
	
	/***
	 * 获取已回答知识库列表
	 **/
	@RequestMapping(value="/list_standard")
    @ResponseBody
    public String getListStandard(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefiqaQuestion question){	
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefiqaKnowledgeDto knowledge=new AefiqaKnowledgeDto();
		knowledge.setPageInfo(pageInfo);
		knowledge.setQuestion(question);
		return knowledgeService.getListStandard(aerfatoken,knowledge);
	}
	
	/***
	 * 获取未回答知识库列表
	 **/
	@RequestMapping(value="/list_unknown")
    @ResponseBody
    public String getListUnknown(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefiqaQuestion question){	
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefiqaKnowledgeDto knowledge=new AefiqaKnowledgeDto();
		knowledge.setPageInfo(pageInfo);
		knowledge.setQuestion(question);
		return knowledgeService.getListUnknown(aerfatoken,knowledge);
	}
	
	/**
	 * 跳转到iqa知识类别新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add_category")
    public String toAddCategory(HttpServletRequest request, ModelMap modelMap){	
		modelMap.addAttribute("prefix",prefix);
		return  prefix+"/add_category";
	}
	
	/**
	 * 跳转到iqa知识类别修改页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_edit_category/{categoryId}")
    public String toEditCategory(HttpServletRequest request, ModelMap modelMap,@PathVariable("categoryId") Long categoryId){	
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retCategoryStr=knowledgeService.getSingleCategory(aerfatoken, categoryId);
		AefiqaCategoryVo categoryVo=JSON.parseObject(retCategoryStr, AefiqaCategoryVo.class);
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("category",categoryVo);
		return  prefix+"/edit_category";
	}
	
    /***
	 * 保存填写的知识类别对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param category 保存的对象
	 ******/
	@RequestMapping(value="/save_category")
    @ResponseBody
    public String saveCategory(HttpServletRequest request, ModelMap modelMap,AefiqaCategory category) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return knowledgeService.saveCategory(aerfatoken, category);
	}
	
	/****
	 * 删除知识类别对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的知识类别ids,中间英文,隔开
	 ***/
	@RequestMapping(value = "/delete_category/{categoryId}")
	@ResponseBody
	public String deleteCategoryByIds(HttpServletRequest request,@PathVariable("categoryId") Long categoryId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String ids =categoryId+"";
		return knowledgeService.deleteCategoryByIds(aerfatoken,ids);
	}
	
	/**
	 * 跳转到iqa标准知识库新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add_standard/{categoryId}")
    public String toAddStandard(HttpServletRequest request, ModelMap modelMap,@PathVariable("categoryId") Long categoryId){	
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefiqaCategoryVo categoryVo=new AefiqaCategoryVo();
		if (categoryId>0) {
			String retCategoryStr=knowledgeService.getSingleCategory(aerfatoken,categoryId);
			categoryVo=JSON.parseObject(retCategoryStr, AefiqaCategoryVo.class);
		}
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("category",categoryVo);
		return  prefix+"/add_standard";
	}
	
	/****
	 * 修改提问问题，先获取提问问题信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit_standard/{id}")
	public String toEditStandard(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retQuestionStr=knowledgeService.getTogetherStandard(aerfatoken, id);
		AefiqaKnowledgeVo knowledgeVo=JSON.parseObject(retQuestionStr,AefiqaKnowledgeVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("question", knowledgeVo);
		return prefix+"/edit_standard";
	}
	
    /***
	 * 保存填写的标准知识库提问回答内容
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param category 保存的对象
	 ******/
	@RequestMapping(value="/save_standard")
    @ResponseBody
    public String saveStandard(HttpServletRequest request, ModelMap modelMap,AefiqaKnowledgeDto knowledge) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return knowledgeService.saveStandard(aerfatoken, knowledge);
	}
	
	/****
	 * 删除表准知识对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的知识类别ids,中间英文,隔开
	 ***/
	@RequestMapping(value = "/delete_standard/{ids}")
	@ResponseBody
	public String deleteStandardByIds(HttpServletRequest request,@PathVariable("ids") String ids) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return knowledgeService.deleteStandardByIds(aerfatoken,ids);
	}
	
}
