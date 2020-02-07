package com.zhangysh.accumulate.back.comm.dao;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 工作日数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年06月29日
 */
@Mapper
public interface WorkDayDao {
	/**
     * 根据ID查询单个工作日信息
     * 
     * @param id 主键ID
     * @return 工作日信息
     */
	 AefcommWorkDay getWorkDayById(Long id);
	
	/**
     * 根据条件查询工作日列表
     * 
     * @param workDay 条件对象
     * @return 工作日条件下的集合
     */
	 List<AefcommWorkDay> listWorkDay(AefcommWorkDay workDay);
	 
	/**
     * 根据主键ids查询工作日
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 工作日条件下的集合
     */
	 List<AefcommWorkDay> listBypksWorkDay(String[] ids);
	 
	/**
     * 新增工作日
     * 
     * @param workDay 工作日对象信息
     * @return 新增结果条数
     */
	 int insertWorkDay(AefcommWorkDay workDay);
	
	/**
     * 批量新增工作日
     * 
     * @param workDay 工作日对象信息
     * @return 新增结果条数
     */
	 void insertBatchWorkDay(List<AefcommWorkDay> workDays);
		 
	/**
     * 修改工作日
     * 
     * @param workDay 工作日信息
     * @return 修改结果条数
     */
	 int updateWorkDay(AefcommWorkDay workDay);
	
	/**
     * 单个删除工作日
     * 
     * @param id 工作日ID
     * @return 删除结果条数
     */
	 int deleteWorkDayById(Long id);
	
	/**
     * 批量删除工作日
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteWorkDayByIds(String[] ids);
	
}