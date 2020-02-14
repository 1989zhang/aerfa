package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 定时任务日志相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
public interface IJobLogService {
	/**
     * 根据ID查询单个定时任务日志信息
     * 
     * @param id 定时任务日志主键ID
     * @return 定时任务日志信息
     */
	 AefsysJobLog getJobLogById(Long id);
	
	/**
     * 根据条件查询定时任务日志分页列表
     * 
     * @param pageInfo 分页对象
     * @param jobLog 条件定时任务日志对象
     * @return 定时任务日志条件下结果集合
     */
	 BsTableDataInfo listPageJobLog(BsTablePageInfo pageInfo, AefsysJobLog jobLog);
	
	/**
     * 根据主键ids查询定时任务日志不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 定时任务日志条件下结果集合
     */
	 List<AefsysJobLog> listBypksJobLog(String ids);
	 
	/**
     * 根据条件查询定时任务日志不分页列表
     * 
     * @param jobLog 条件定时任务日志对象
     * @return 定时任务日志条件下结果集合
     */
	 List<AefsysJobLog> listJobLog(AefsysJobLog jobLog);
	 
	/**
     * 新增定时任务日志
     * 
     * @param jobLog 定时任务日志对象信息
     * @return 新增结果条数
     */
	 int insertJobLog(AefsysJobLog jobLog);
	
	/**
     * 修改定时任务日志
     * 
     * @param jobLog 定时任务日志修改信息
     * @return 修改结果条数
     */
	 int updateJobLog(AefsysJobLog jobLog);
	
	/**
     * 单个删除定时任务日志
     * 
     * @param id 定时任务日志ID
     * @return 删除结果条数
     */
	 int deleteJobLogById(Long id);
	 	
	/**
     * 删除定时任务日志信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteJobLogByIds(String ids);
	
}
