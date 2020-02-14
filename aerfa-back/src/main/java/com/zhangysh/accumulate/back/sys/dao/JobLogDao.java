package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 定时任务日志数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
@Mapper
public interface JobLogDao {
	/**
     * 根据ID查询单个定时任务日志信息
     * 
     * @param id 主键ID
     * @return 定时任务日志信息
     */
	 AefsysJobLog getJobLogById(Long id);
	
	/**
     * 根据条件查询定时任务日志列表
     * 
     * @param jobLog 条件对象
     * @return 定时任务日志条件下的集合
     */
	 List<AefsysJobLog> listJobLog(AefsysJobLog jobLog);
	 
	/**
     * 根据主键ids查询定时任务日志
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 定时任务日志条件下的集合
     */
	 List<AefsysJobLog> listBypksJobLog(String[] ids);
	 
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
     * @param jobLog 定时任务日志信息
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
     * 批量删除定时任务日志
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteJobLogByIds(String[] ids);
	
}