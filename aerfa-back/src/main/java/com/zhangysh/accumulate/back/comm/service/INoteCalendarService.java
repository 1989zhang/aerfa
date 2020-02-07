package com.zhangysh.accumulate.back.comm.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommNoteCalendar;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 日历记事本相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年10月12日
 */
public interface INoteCalendarService {
	/**
     * 根据ID查询单个日历记事本信息
     * 
     * @param id 日历记事本主键ID
     * @return 日历记事本信息
     */
	 AefcommNoteCalendar getNoteCalendarById(Long id);
	
	/**
     * 根据条件查询日历记事本分页列表
     * 
     * @param pageInfo 分页对象
     * @param noteCalendar 条件日历记事本对象
     * @return 日历记事本条件下结果集合
     */
	 BsTableDataInfo listPageNoteCalendar(BsTablePageInfo pageInfo,AefcommNoteCalendar noteCalendar);
	
	/**
     * 根据主键ids查询日历记事本不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 日历记事本条件下结果集合
     */
	 List<AefcommNoteCalendar> listBypksNoteCalendar(String ids);
	 
	/**
     * 根据条件查询日历记事本不分页列表
     * 
     * @param noteCalendar 条件日历记事本对象
     * @return 日历记事本条件下结果集合
     */
	 List<AefcommNoteCalendar> listNoteCalendar(AefcommNoteCalendar noteCalendar);
	 
	/**
     * 据条件查询时间段日历记事本列表
     * 
     * @param personId 记事本所属人员ID
     * @return 日历记事本条件下结果集合
     */
	 List<AefcommNoteCalendar> listNoteCalendarAround(AefcommNoteCalendar noteCalendar);
		 
	/**
     * 新增日历记事本
     * 
     * @param noteCalendar 日历记事本对象信息
     * @return 新增结果条数
     */
	 int insertNoteCalendar(AefcommNoteCalendar noteCalendar);
	
	/**
     * 修改日历记事本
     * 
     * @param noteCalendar 日历记事本修改信息
     * @return 修改结果条数
     */
	 int updateNoteCalendar(AefcommNoteCalendar noteCalendar);
	
	/**
     * 单个删除日历记事本
     * 
     * @param id 日历记事本ID
     * @return 删除结果条数
     */
	 int deleteNoteCalendarById(Long id);
	 	
	/**
     * 删除日历记事本信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteNoteCalendarByIds(String ids);
	
}
