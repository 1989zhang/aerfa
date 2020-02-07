package com.zhangysh.accumulate.back.iqa.dao;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 知识类别数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年12月22日
 */
@Mapper
public interface CategoryDao {
	/**
     * 根据ID查询单个知识类别信息
     * 
     * @param id 主键ID
     * @return 知识类别信息
     */
	 AefiqaCategory getCategoryById(Long id);
	
	/**
     * 根据条件查询知识类别列表
     * 
     * @param category 条件对象
     * @return 知识类别条件下的集合
     */
	 List<AefiqaCategory> listCategory(AefiqaCategory category);
	 
	/**
     * 根据主键ids查询知识类别
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 知识类别条件下的集合
     */
	 List<AefiqaCategory> listBypksCategory(String[] ids);
	 
	/**
     * 新增知识类别
     * 
     * @param category 知识类别对象信息
     * @return 新增结果条数
     */
	 int insertCategory(AefiqaCategory category);
	
	/**
     * 修改知识类别
     * 
     * @param category 知识类别信息
     * @return 修改结果条数
     */
	 int updateCategory(AefiqaCategory category);
	
	/**
     * 单个删除知识类别
     * 
     * @param id 知识类别ID
     * @return 删除结果条数
     */
	 int deleteCategoryById(Long id);
	
	/**
     * 批量删除知识类别
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteCategoryByIds(String[] ids);
	
}