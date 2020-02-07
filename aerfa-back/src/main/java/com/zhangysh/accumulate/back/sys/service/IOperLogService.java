package com.zhangysh.accumulate.back.sys.service;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOperLog;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 操作日志相关服务层接口
 * 
 * @author 创建者：zhangysh
 * @date 2018年10月20日
 */
public interface IOperLogService {
	/**
     * 根据ID查询单个操作日志信息
     * 
     * @param id 操作日志主键ID
     * @return 操作日志信息
     */
	 AefsysOperLog getOperLogById(Long id);
	
	/**
     * 根据条件查询操作日志列表
     * 
     * @param operLog 条件操作日志对象
     * @return 操作日志条件下结果集合
     */
	 BsTableDataInfo listPageOperLog(BsTablePageInfo pageInfo,AefsysOperLog operLog);
	
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
     * @param operLog 操作日志修改信息
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
     * 删除操作日志信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteOperLogByIds(String ids);
	
}
