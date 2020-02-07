package com.zhangysh.accumulate.ui.iqa.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaKnowledgeDto;

/*****
 * 智能问答，知识库相关方法
 * @author zhangysh
 * @date 2019年12月22日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IqaKnowledgeService {

	/****
	 * 展示知识分类的树形结构
	 * @param aerfatoken token对象
	 ***/
	@RequestMapping(value = "/iqa/knowledge/category_tree",method = RequestMethod.POST)
	public String getCategoryTree(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);
	
	/****
	 * 获取单个知识类别信息
	 * @param aerfatoken token对象
	 * @param id 知识类别的id
	 ***/
	@RequestMapping(value = "/iqa/knowledge/single_category",method = RequestMethod.POST)
	public String getSingleCategory(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);
	
	/****
	 * 保存新增的知识类别信息 
	 * @param aerfatoken token对象
	 * @param Category 要保存的知识类别对象
	 ***/
	@RequestMapping(value = "/iqa/knowledge/save_category",method = RequestMethod.POST)
	public String saveCategory(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefiqaCategory category);
	
	/****
	 * 删除知识类别对象，可以删除多个.
	 * @param aerfatoken token对象
	 * @param ids 要删除的知识类别ids,中间英文,隔开
	 ***/
	@RequestMapping(value = "/iqa/knowledge/delete_category",method = RequestMethod.POST)
	public String deleteCategoryByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);
	
	/***
	 * 获取已回答即已知知识库列表
	 * @param aerfatoken token对象
	 **/
	@RequestMapping(value = "/iqa/knowledge/list_standard",method = RequestMethod.POST)
	public String getListStandard(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefiqaKnowledgeDto knowledgeDto);
	
	/***
	 * 获取未回答即未知知识库列表
	 * @param aerfatoken token对象
	 **/
	@RequestMapping(value = "/iqa/knowledge/list_unknown",method = RequestMethod.POST)
	public String getListUnknown(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefiqaKnowledgeDto knowledgeDto);
	
	/***
	 * 获取展示单个知识问题和相关对象：且包含问题、答案、类别和非标准问法集合,以便知识库修改
	 * @param aerfatoken token对象
	 * @param id 知识问题主键ID 
	 ***/
	@RequestMapping(value = "/iqa/knowledge/together_standard",method = RequestMethod.POST)
	public String getTogetherStandard(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);
	
	/****
	 * 保存填写的标准知识库提问回答内容
	 * @param aerfatoken token对象
	 * @param Category 要保存的知识类别对象
	 ***/
	@RequestMapping(value = "/iqa/knowledge/save_standard",method = RequestMethod.POST)
	public String saveStandard(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefiqaKnowledgeDto knowledgeDto);
	
	/****
	 * 删除表准知识对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的知识类别ids,中间英文,隔开
	 ***/
	@RequestMapping(value = "/iqa/knowledge/delete_standard",method = RequestMethod.POST)
	public String deleteStandardByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);
	
}
