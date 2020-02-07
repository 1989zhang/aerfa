package com.zhangysh.accumulate.back.iqa.dao;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaAnswer;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 解答回答数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
@Mapper
public interface AnswerDao {
	/**
     * 根据ID查询单个解答回答信息
     * 
     * @param id 主键ID
     * @return 解答回答信息
     */
	 AefiqaAnswer getAnswerById(Long id);
	
	/**
     * 根据条件查询解答回答列表
     * 
     * @param answer 条件对象
     * @return 解答回答条件下的集合
     */
	 List<AefiqaAnswer> listAnswer(AefiqaAnswer answer);
	 
	/**
     * 根据主键ids查询解答回答
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 解答回答条件下的集合
     */
	 List<AefiqaAnswer> listBypksAnswer(String[] ids);
	 
	/**
     * 新增解答回答
     * 
     * @param answer 解答回答对象信息
     * @return 新增结果条数
     */
	 int insertAnswer(AefiqaAnswer answer);
	
	/**
     * 修改解答回答
     * 
     * @param answer 解答回答信息
     * @return 修改结果条数
     */
	 int updateAnswer(AefiqaAnswer answer);
	
	/**
     * 单个删除解答回答
     * 
     * @param id 解答回答ID
     * @return 删除结果条数
     */
	 int deleteAnswerById(Long id);
	
	/**
     * 批量删除解答回答
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteAnswerByIds(String[] ids);
	
}