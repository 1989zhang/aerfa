package com.zhangysh.accumulate.back.comm.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;
import com.zhangysh.accumulate.pojo.comm.viewobj.LayDateRenderVo;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 工作日相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年06月29日
 */
public interface IWorkDayService {
	 
	/**
     * 根据ID查询单个工作日信息
     * 
     * @param id 工作日主键ID
     * @return 工作日信息
     */
	 AefcommWorkDay getWorkDayById(Long id);
	
	/**
     * 根据条件查询工作日分页列表
     * 
     * @param pageInfo 分页对象
     * @param workDay 条件工作日对象
     * @return 工作日条件下结果集合
     */
	 BsTableDataInfo listPageWorkDay(BsTablePageInfo pageInfo,AefcommWorkDay workDay);
	
	/**
     * 根据主键ids查询工作日不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 工作日条件下结果集合
     */
	 List<AefcommWorkDay> listBypksWorkDay(String ids);
	 
	/**
     * 根据条件查询工作日不分页列表
     * 
     * @param workDay 条件工作日对象
     * @return 工作日条件下结果集合
     */
	 List<AefcommWorkDay> listWorkDay(AefcommWorkDay workDay);
	 
	/**
     * 新增工作日
     * 
     * @param workDay 工作日对象信息
     * @return 新增结果条数
     */
	 int insertWorkDay(AefcommWorkDay workDay);
	
	/**
     * 修改工作日
     * 
     * @param workDay 工作日修改信息
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
     * 删除工作日信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteWorkDayByIds(String ids);
	
	/**
	 * 初始化年所在的月份和日期
	 * @param operPerson 操作人员对象
	 * @param year 年份 
     * @return 工作日信息
     */
	List<LayDateRenderVo> getRenderDateByYear(AefsysPerson operPerson,int year);
	
	/**
	 * 点击改变日期的上班不上班状态,并返回提示信息
	 * @param operPerson 操作人员对象
	 * @param dateStr 改变的日期字符串
	 * @return 提示信息
	 */
	String changeWorkDayStatus(AefsysPerson operPerson,String dateStr);
}
