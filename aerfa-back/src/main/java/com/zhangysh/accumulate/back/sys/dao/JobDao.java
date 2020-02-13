package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 定时任务数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
@Mapper
public interface JobDao {
	/**
     * 根据ID查询单个定时任务信息
     * 
     * @param id 主键ID
     * @return 定时任务信息
     */
	 AefsysJob getJobById(Long id);
	
	/**
     * 根据条件查询定时任务列表
     * 
     * @param job 条件对象
     * @return 定时任务条件下的集合
     */
	 List<AefsysJob> listJob(AefsysJob job);
	 
	/**
     * 根据主键ids查询定时任务
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 定时任务条件下的集合
     */
	 List<AefsysJob> listBypksJob(String[] ids);
	 
	/**
     * 新增定时任务
     * 
     * @param job 定时任务对象信息
     * @return 新增结果条数
     */
	 int insertJob(AefsysJob job);
	
	/**
     * 修改定时任务
     * 
     * @param job 定时任务信息
     * @return 修改结果条数
     */
	 int updateJob(AefsysJob job);
	
	/**
     * 单个删除定时任务
     * 
     * @param id 定时任务ID
     * @return 删除结果条数
     */
	 int deleteJobById(Long id);
	
	/**
     * 批量删除定时任务
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteJobByIds(String[] ids);
	
}