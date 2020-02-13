package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 定时任务相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年02月13日
 */
public interface IJobService {
	/**
     * 根据ID查询单个定时任务信息
     * 
     * @param id 定时任务主键ID
     * @return 定时任务信息
     */
	 AefsysJob getJobById(Long id);
	
	/**
     * 根据条件查询定时任务分页列表
     * 
     * @param pageInfo 分页对象
     * @param job 条件定时任务对象
     * @return 定时任务条件下结果集合
     */
	 BsTableDataInfo listPageJob(BsTablePageInfo pageInfo, AefsysJob job);
	
	/**
     * 根据主键ids查询定时任务不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 定时任务条件下结果集合
     */
	 List<AefsysJob> listBypksJob(String ids);
	 
	/**
     * 根据条件查询定时任务不分页列表
     * 
     * @param job 条件定时任务对象
     * @return 定时任务条件下结果集合
     */
	 List<AefsysJob> listJob(AefsysJob job);
	 
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
     * @param job 定时任务修改信息
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
     * 删除定时任务信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteJobByIds(String ids);
	
}
