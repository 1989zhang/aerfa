package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 常用功能快速访问数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
@Mapper
public interface QuickVisitDao {
	/**
     * 根据ID查询单个常用功能快速访问信息
     * 
     * @param id 主键ID
     * @return 常用功能快速访问信息
     */
	 AefsysQuickVisit getQuickVisitById(Long id);
	
	/**
     * 根据条件查询常用功能快速访问列表
     * 
     * @param quickVisit 条件对象
     * @return 常用功能快速访问条件下的集合
     */
	 List<AefsysQuickVisit> listQuickVisit(AefsysQuickVisit quickVisit);
	 
	/**
     * 根据主键ids查询常用功能快速访问
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 常用功能快速访问条件下的集合
     */
	 List<AefsysQuickVisit> listBypksQuickVisit(String[] ids);
	 
	/**
     * 新增常用功能快速访问
     * 
     * @param quickVisit 常用功能快速访问对象信息
     * @return 新增结果条数
     */
	 int insertQuickVisit(AefsysQuickVisit quickVisit);
	
	/**
     * 修改常用功能快速访问
     * 
     * @param quickVisit 常用功能快速访问信息
     * @return 修改结果条数
     */
	 int updateQuickVisit(AefsysQuickVisit quickVisit);
	
	/**
     * 单个删除常用功能快速访问
     * 
     * @param id 常用功能快速访问ID
     * @return 删除结果条数
     */
	 int deleteQuickVisitById(Long id);
	
	/**
     * 批量删除常用功能快速访问
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteQuickVisitByIds(String[] ids);
	
}