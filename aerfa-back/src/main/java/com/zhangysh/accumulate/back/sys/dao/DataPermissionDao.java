package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 数据权限数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Mapper
public interface DataPermissionDao {
	/**
     * 根据ID查询单个数据权限信息
     * 
     * @param id 主键ID
     * @return 数据权限信息
     */
	 AefsysDataPermission getDataPermissionById(Long id);
	
	/**
     * 根据条件查询数据权限列表
     * 
     * @param dataPermission 条件对象
     * @return 数据权限条件下的集合
     */
	 List<AefsysDataPermission> listDataPermission(AefsysDataPermission dataPermission);
	 
	/**
     * 根据主键ids查询数据权限
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 数据权限条件下的集合
     */
	 List<AefsysDataPermission> listBypksDataPermission(String[] ids);
	 
	/**
     * 新增数据权限
     * 
     * @param dataPermission 数据权限对象信息
     * @return 新增结果条数
     */
	 int insertDataPermission(AefsysDataPermission dataPermission);
	
	/**
     * 修改数据权限
     * 
     * @param dataPermission 数据权限信息
     * @return 修改结果条数
     */
	 int updateDataPermission(AefsysDataPermission dataPermission);
	
	/**
     * 单个删除数据权限
     * 
     * @param id 数据权限ID
     * @return 删除结果条数
     */
	 int deleteDataPermissionById(Long id);
	
	/**
     * 批量删除数据权限
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDataPermissionByIds(String[] ids);
	
}