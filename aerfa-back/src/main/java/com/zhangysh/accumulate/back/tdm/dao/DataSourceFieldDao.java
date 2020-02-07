package com.zhangysh.accumulate.back.tdm.dao;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 数据源字段映射数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Mapper
public interface DataSourceFieldDao {
	/**
     * 根据ID查询单个数据源字段映射信息
     * 
     * @param id 主键ID
     * @return 数据源字段映射信息
     */
	 AeftdmDataSourceField getDataSourceFieldById(Long id);
	
	/**
     * 根据条件查询数据源字段映射列表
     * 
     * @param dataSourceField 条件对象
     * @return 数据源字段映射条件下的集合
     */
	 List<AeftdmDataSourceField> listDataSourceField(AeftdmDataSourceField dataSourceField);
	 
	/**
     * 根据主键ids查询数据源字段映射
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 数据源字段映射条件下的集合
     */
	 List<AeftdmDataSourceField> listBypksDataSourceField(String[] ids);
		 
	/**
     * 新增数据源字段映射
     * 
     * @param dataSourceField 数据源字段映射对象信息
     * @return 新增结果条数
     */
	 int insertDataSourceField(AeftdmDataSourceField dataSourceField);
	
	/**
     * 修改数据源字段映射
     * 
     * @param dataSourceField 数据源字段映射信息
     * @return 修改结果条数
     */
	 int updateDataSourceField(AeftdmDataSourceField dataSourceField);
	
	/**
     * 单个删除数据源字段映射
     * 
     * @param id 数据源字段映射ID
     * @return 删除结果条数
     */
	 int deleteDataSourceFieldById(Long id);
	
	/**
     * 批量删除数据源字段映射
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDataSourceFieldByIds(String[] ids);
	
}