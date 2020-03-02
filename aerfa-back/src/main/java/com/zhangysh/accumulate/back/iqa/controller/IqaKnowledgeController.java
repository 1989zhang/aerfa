package com.zhangysh.accumulate.back.iqa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.iqa.service.ICategoryService;
import com.zhangysh.accumulate.back.iqa.service.IQuestionService;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.common.constant.IqaDefineConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaKnowledgeDto;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

/*****
 * 智能问答，知识库相关方法
 * @author zhangysh
 * @date 2019年11月1日
 *****/
@Controller
@RequestMapping("/iqa/knowledge")
public class IqaKnowledgeController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(IqaKnowledgeController.class);
    
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IQuestionService questionService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
	
	/****
	 * 展示知识分类的树形结构
	 * @param request 请求对象
	 ***/
	@RequestMapping(value = "/category_tree",method = RequestMethod.POST)
    @ResponseBody
	public String getCategoryTree(HttpServletRequest request) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return JSON.toJSONString(categoryService.listCategoryWithTreeStructure());
	}
	
	/****
	 * 获取展示单个知识类别,以便修改
	 * @param request 请求对象
     * @param id 知识类别主键ID
     * @return 知识类别信息
	 ****/
	@RequestMapping(value="/single_category",method = RequestMethod.POST)
    @ResponseBody
	public String getSingleCategory(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingleCategory知识类别主键信息:{}",id);
		return JSON.toJSONString(categoryService.getCategoryById(id),SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/****
	 * 保存新增和修改的知识类别信息 
	 * @param request 请求对象
	 * @param category 保存的对象
	 ****/
	@RequestMapping(value="/save_category",method = RequestMethod.POST)
    @ResponseBody
	public String saveCategory(HttpServletRequest request,@RequestBody AefiqaCategory category) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( category.getId()!=null ) {//修改方法
		    category.setUpdateTime(DateOperate.getCurrentUtilDate());
			category.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(categoryService.updateCategory(category));
		} else {//新增方法
			category.setBelongOrgId(operPerson.getOrgId());
			category.setCreateTime(DateOperate.getCurrentUtilDate());
			category.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(categoryService.insertCategory(category));
		}
	}
	
	/****
	 * 删除知识类别对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的知识类别ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="智能问答",menu="知识库知识分类",button="删除",saveParam=true)
	@RequestMapping(value = "/delete_category",method = RequestMethod.POST)
	@ResponseBody
	public String deleteCategoryByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(categoryService.deleteCategoryByIds(ids));
	}	
	
	/***
	 * 获取已回答即已知知识库列表
	 * @param request 请求对象
	 * @param knowledgeDto 分页和问题查询对象
	 * @return 获取到的问题带分页对象集合JSON
	 **/
	@RequestMapping(value = "/list_standard",method = RequestMethod.POST)
	@ResponseBody
	public String getListStandard(HttpServletRequest request,@RequestBody AefiqaKnowledgeDto knowledgeDto) {
		AefiqaQuestion question=knowledgeDto.getQuestion();
		question.setStandard(SysDefineConstant.DIC_COMMON_STATUS_YES);//只展示标准问题，非标准点击编辑显示
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(IqaDefineConstant.QUESTION_MARK_REPLY, SysDefineConstant.DIC_COMMON_STATUS_YES);
		question.setParams(params);
		BsTableDataInfo tableInfo=questionService.listPageQuestion(knowledgeDto.getPageInfo(), question);
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/***
	 * 获取未回答即未知知识库列表
	 * @param knowledgeDto 分页和问题查询对象
	 * @return 获取到的问题带分页对象集合JSON
	 **/
	@RequestMapping(value = "/list_unknown",method = RequestMethod.POST)
	@ResponseBody
	public String getListUnknown(HttpServletRequest request,@RequestBody AefiqaKnowledgeDto knowledgeDto) {
		AefiqaQuestion question=knowledgeDto.getQuestion();
		question.setStandard(SysDefineConstant.DIC_COMMON_STATUS_YES);//只展示标准问题，非标准点击编辑显示
		Map<String, Object> params=new HashMap<String, Object>();
		params.put(IqaDefineConstant.QUESTION_MARK_REPLY, SysDefineConstant.DIC_COMMON_STATUS_NO);
		question.setParams(params);
		BsTableDataInfo tableInfo=questionService.listPageQuestion(knowledgeDto.getPageInfo(), question);
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个知识问题，且包含问题、答案、类别对象,以便普通修改
	 * @param request 请求对象
     * @param id 知识问题主键ID
     * @return 知识问题展示信息
	 ****/
	@RequestMapping(value="/single_standard",method = RequestMethod.POST)
    @ResponseBody
	public String getSingleStandard(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingleStandard知识类别主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(questionService.getQuestionById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个知识问题和相关对象：且包含问题、答案、类别和非标准问法集合,以便知识库修改
	 * @param request 请求对象
     * @param id 知识问题主键ID
     * @return 知识问题展示信息
	 ****/
	@RequestMapping(value="/together_standard",method = RequestMethod.POST)
    @ResponseBody
	public String getTogetherStandard(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getTogetherStandard知识类别主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(questionService.getQuestionTogetherById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 保存填写的标准知识库提问回答内容
	 * @param request token对象
	 * @param knowledgeDto 要保存的知识传输对象
	 ***/
	@RequestMapping(value="/save_standard",method = RequestMethod.POST)
    @ResponseBody
	public String saveStandard(HttpServletRequest request,@RequestBody AefiqaKnowledgeDto knowledgeDto) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		Long questionId=knowledgeDto.getId();
		if ( questionId!=null && questionId>0 ) {//修改方法
			return toHandlerResultStr(questionService.updateKnowledgeInfo(operPerson, knowledgeDto));
		} else {//新增方法
			return toHandlerResultStr(questionService.insertKnowledgeInfo(operPerson, knowledgeDto));
		}
	}
	
	/****
	 * 删除标准知识对象，同时删除非标准问法和回答；可以删除多个。
	 * @param request 请求对象
	 * @param ids 要删除的知识ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="智能问答",menu="标准知识库",button="删除",saveParam=true)
	@RequestMapping(value = "/delete_standard",method = RequestMethod.POST)
	@ResponseBody
	public String deleteStandardByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(questionService.deleteTogetherStandardByIds(ids));
	}	

}
