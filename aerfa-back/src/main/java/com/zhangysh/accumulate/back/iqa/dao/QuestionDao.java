package com.zhangysh.accumulate.back.iqa.dao;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 提问问题数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
@Mapper
public interface QuestionDao {
	/**
     * 根据ID查询单个提问问题信息
     * 
     * @param id 主键ID
     * @return 提问问题信息
     */
	 AefiqaQuestion getQuestionById(Long id);
	
	/**
     * 根据条件查询提问问题列表
     * 
     * @param question 条件对象
     * @return 提问问题条件下的集合
     */
	 List<AefiqaQuestion> listQuestion(AefiqaQuestion question);

	/**
	 * 根据条件匹配到问题内容
	 *
	 * @param question 条件对象,内容是匹配的
	 * @return 提问问题条件下的集合
	 */
	List<AefiqaQuestion> listMatchContentQuestion(AefiqaQuestion question);

	/**
     * 根据主键ids查询提问问题
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 提问问题条件下的集合
     */
	 List<AefiqaQuestion> listBypksQuestion(String[] ids);
	 
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
     * @param question 提问问题信息
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
     * 批量删除提问问题
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteQuestionByIds(String[] ids);
	
}