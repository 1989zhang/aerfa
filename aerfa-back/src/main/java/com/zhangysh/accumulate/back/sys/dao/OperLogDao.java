package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOperLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 操作日志数据层对应接口
 * 
 * @author 创建者：zhangysh
 * @date 2018年10月20日
 */
@Mapper
public interface OperLogDao {
	/**
     * 根据ID查询单个操作日志信息
     * 
     * @param id 主键ID
     * @return 操作日志信息
     */
	 AefsysOperLog getOperLogById(Long id);
	
	/**
     * 根据条件查询操作日志列表
     * 
     * @param operLog 条件对象
     * @return 操作日志条件下的集合
     */
	 List<AefsysOperLog> listOperLog(AefsysOperLog operLog);
	
	/**
     * 新增操作日志
     * 
     * @param operLog 操作日志对象信息
     * @return 新增结果条数
     */
	 int insertOperLog(AefsysOperLog operLog);
	
	/**
     * 修改操作日志
     * 
     * @param operLog 操作日志信息
     * @return 修改结果条数
     */
	 int updateOperLog(AefsysOperLog operLog);
	
	/**
     * 单个删除操作日志
     * 
     * @param id 操作日志ID
     * @return 删除结果条数
     */
	 int deleteOperLogById(Long id);
	
	/**
     * 批量删除操作日志
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteOperLogByIds(String[] ids);
	
}