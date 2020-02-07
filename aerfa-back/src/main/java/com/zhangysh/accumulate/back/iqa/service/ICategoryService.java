package com.zhangysh.accumulate.back.iqa.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;

/**
 * 知识类别相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年12月22日
 */
public interface ICategoryService {
	
	/***
	 *查询出知识类别分类且含树形结构
	 *@return 树形结构的单位列表 
	 *******/
	List<BsTreeNode> listCategoryWithTreeStructure();
	
	/**
     * 根据ID查询单个知识类别信息
     * 
     * @param id 知识类别主键ID
     * @return 知识类别信息
     */
	 AefiqaCategory getCategoryById(Long id);
	
	/**
     * 根据条件查询知识类别分页列表
     * 
     * @param pageInfo 分页对象
     * @param category 条件知识类别对象
     * @return 知识类别条件下结果集合
     */
	 BsTableDataInfo listPageCategory(BsTablePageInfo pageInfo,AefiqaCategory category);
	
	/**
     * 根据主键ids查询知识类别不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 知识类别条件下结果集合
     */
	 List<AefiqaCategory> listBypksCategory(String ids);
	 
	/**
     * 根据条件查询知识类别不分页列表
     * 
     * @param category 条件知识类别对象
     * @return 知识类别条件下结果集合
     */
	 List<AefiqaCategory> listCategory(AefiqaCategory category);
	 
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
     * @param category 知识类别修改信息
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
     * 删除知识类别信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteCategoryByIds(String ids);
	
}
