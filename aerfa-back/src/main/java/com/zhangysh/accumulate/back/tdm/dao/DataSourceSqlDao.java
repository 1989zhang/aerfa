package com.zhangysh.accumulate.back.tdm.dao;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 数据源SQL定义数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Mapper
public interface DataSourceSqlDao {
	/**
     * 根据ID查询单个数据源SQL定义信息
     * 
     * @param id 主键ID
     * @return 数据源SQL定义信息
     */
	 AeftdmDataSourceSql getDataSourceSqlById(Long id);
	
	/**
     * 根据条件查询数据源SQL定义列表
     * 
     * @param dataSourceSql 条件对象
     * @return 数据源SQL定义条件下的集合
     */
	 List<AeftdmDataSourceSql> listDataSourceSql(AeftdmDataSourceSql dataSourceSql);
	 
	/**
     * 新增数据源SQL定义
     * 
     * @param dataSourceSql 数据源SQL定义对象信息
     * @return 新增结果条数
     */
	 int insertDataSourceSql(AeftdmDataSourceSql dataSourceSql);
	
	/**
     * 修改数据源SQL定义
     * 
     * @param dataSourceSql 数据源SQL定义信息
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
     * 批量删除数据源SQL定义
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDataSourceSqlByIds(String[] ids);
	
}