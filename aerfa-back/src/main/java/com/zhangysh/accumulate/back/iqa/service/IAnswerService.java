package com.zhangysh.accumulate.back.iqa.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaAnswer;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 解答回答相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
public interface IAnswerService {
	/**
     * 根据ID查询单个解答回答信息
     * 
     * @param id 解答回答主键ID
     * @return 解答回答信息
     */
	 AefiqaAnswer getAnswerById(Long id);
	
	/**
     * 根据条件查询解答回答分页列表
     * 
     * @param pageInfo 分页对象
     * @param answer 条件解答回答对象
     * @return 解答回答条件下结果集合
     */
	 BsTableDataInfo listPageAnswer(BsTablePageInfo pageInfo,AefiqaAnswer answer);
	
	/**
     * 根据主键ids查询解答回答不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 解答回答条件下结果集合
     */
	 List<AefiqaAnswer> listBypksAnswer(String ids);
	 
	/**
     * 根据条件查询解答回答不分页列表
     * 
     * @param answer 条件解答回答对象
     * @return 解答回答条件下结果集合
     */
	 List<AefiqaAnswer> listAnswer(AefiqaAnswer answer);
	 
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
     * @param answer 解答回答修改信息
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
     * 删除解答回答信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteAnswerByIds(String ids);
	
}
