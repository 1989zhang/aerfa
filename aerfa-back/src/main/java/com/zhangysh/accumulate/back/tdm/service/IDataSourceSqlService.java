package com.zhangysh.accumulate.back.tdm.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.back.tdm.exception.QueryDataException;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;

/**
 * 数据源SQL定义相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public interface IDataSourceSqlService {
	/**
     * 根据ID查询单个数据源SQL定义信息
     * 
     * @param id 数据源SQL定义主键ID
     * @return 数据源SQL定义信息
     */
	 AeftdmDataSourceSql getDataSourceSqlById(Long id);
	
	/**
     * 根据条件查询数据源SQL定义分页列表
     * 
     * @param pageInfo 分页对象
     * @param dataSourceSql 条件数据源SQL定义对象
     * @return 数据源SQL定义条件下结果集合
     */
	 BsTableDataInfo listPageDataSourceSql(BsTablePageInfo pageInfo,AeftdmDataSourceSql dataSourceSql);
	
	/**
     * 根据条件查询数据源SQL定义不分页列表
     * 
     * @param dataSourceSql 条件数据源SQL定义对象
     * @return 数据源SQL定义条件下结果集合
     */
	 List<AeftdmDataSourceSql> listDataSourceSql(AeftdmDataSourceSql dataSourceSql);
	 
	/**
     * 新增数据源SQL定义，且处理对应字段信息
     * 
     * @param dataSourceSql 数据源SQL定义对象信息
     * @return 新增结果条数
     */
	 int insertDataSourceSql(AeftdmDataSourceSql dataSourceSql) throws QueryDataException;
	
	/**
     * 修改数据源SQL定义
     * 
     * @param dataSourceSql 数据源SQL定义修改信息
     * @return 修改结果条数
     */
	 int updateDataSourceSql(AeftdmDataSourceSql dataSourceSql);
	
	/**
     * 单个删除数据源SQL定义
     * 
     * @param id 数据源SQL定义ID
     * @return 删除结果条数
     */
	 int deleteDataSourceSqlById(Long id);
	 	
	/**
     * 删除数据源SQL定义信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDataSourceSqlByIds(String ids);
	
	 /****
	  * 获取树形结构的数据源和字段
	  * @param templateId 模板id
	  * 
	  **/
	 List<BsTreeNode> listDataSourceWithTreeStructure(Long templateId);
}
