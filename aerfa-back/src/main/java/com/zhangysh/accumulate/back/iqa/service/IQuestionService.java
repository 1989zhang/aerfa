package com.zhangysh.accumulate.back.iqa.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaKnowledgeDto;
import com.zhangysh.accumulate.pojo.iqa.viewobj.AefiqaKnowledgeVo;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 提问问题相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
public interface IQuestionService {
	/**
     * 根据ID查询单个提问问题信息,且包含回答和分类
     * 
     * @param id 提问问题主键ID
     * @return 提问问题信息
     */
	AefiqaKnowledgeVo getQuestionById(Long id);
	
	/**
     * 根据ID查询单个提问问题信息,且包含问题、答案、类别和非标准问法集合
     * 
     * @param id 提问问题主键ID
     * @return 提问问题信息
     */
	AefiqaKnowledgeVo getQuestionTogetherById(Long id);
	
	/**
     * 根据条件查询提问问题分页列表,且包含回答和分类
     * 
     * @param pageInfo 分页对象
     * @param question 条件提问问题对象
     * @return 提问问题条件下结果集合
     */
	 BsTableDataInfo listPageQuestion(BsTablePageInfo pageInfo,AefiqaQuestion question);
	
	/**
     * 根据主键ids查询提问问题不分页列表,且包含回答和分类
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 提问问题条件下结果集合
     */
	 List<AefiqaKnowledgeVo> listBypksQuestion(String ids);
	 
	/**
     * 根据条件查询提问问题不分页列表,不包含回答和分类
     * 
     * @param question 条件提问问题对象
     * @return 提问问题条件下结果集合
     */
	 List<AefiqaQuestion> listQuestion(AefiqaQuestion question);
	 
	/**
     * 新增提问问题
     * 
     * @param question 提问问题对象信息
     * @return 新增结果条数
     */
	 int insertQuestion(AefiqaQuestion question);
	
	/**
     * 修改提问问题
     * 
     * @param question 提问问题修改信息
     * @return 修改结果条数
     */
	 int updateQuestion(AefiqaQuestion question);
	
	/**
     * 单个删除提问问题
     * 
     * @param id 提问问题ID
     * @return 删除结果条数
     */
	 int deleteQuestionById(Long id);
	 
	/**
     * 删除提问问题信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteQuestionByIds(String ids);
	 
	/**
     * 删除标准知识对象，同时删除非标准问法和回答；可以删除多个。
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteTogetherStandardByIds(String ids);
	
	 /**
	  * 新增知识相关信息包括：标准问法、非标准问法、答案
	  * 操作逻辑：先新增答案，再新增标准问法，再新增非标准问法
	  * 
	  * @param operPerson 操作人员
	  * @param knowledgeDto 保存的知识对象
	  ***/
	 int insertKnowledgeInfo(AefsysPerson operPerson,AefiqaKnowledgeDto knowledgeDto);
	 
	 /**
	  * 修改知识相关信息包括：标准问法、非标准问法、答案
	  * 操作逻辑：先修改标准问法，再修改答案，再删除非标准问法新增非标准问法
	  * @param operPerson 操作人员
	  * @param knowledgeDto 保存的知识对象
	  ***/
	 int updateKnowledgeInfo(AefsysPerson operPerson,AefiqaKnowledgeDto knowledgeDto);
}
