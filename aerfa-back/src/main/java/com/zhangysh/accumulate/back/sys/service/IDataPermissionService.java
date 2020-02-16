package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 数据权限相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
public interface IDataPermissionService {
	/**
     * 根据ID查询单个数据权限信息
     * 
     * @param id 数据权限主键ID
     * @return 数据权限信息
     */
	 AefsysDataPermission getDataPermissionById(Long id);
	
	/**
     * 根据条件查询数据权限分页列表
     * 
     * @param pageInfo 分页对象
     * @param dataPermission 条件数据权限对象
     * @return 数据权限条件下结果集合
     */
	 BsTableDataInfo listPageDataPermission(BsTablePageInfo pageInfo, AefsysDataPermission dataPermission);
	
	/**
     * 根据主键ids查询数据权限不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 数据权限条件下结果集合
     */
	 List<AefsysDataPermission> listBypksDataPermission(String ids);
	 
	/**
     * 根据条件查询数据权限不分页列表
     * 
     * @param dataPermission 条件数据权限对象
     * @return 数据权限条件下结果集合
     */
	 List<AefsysDataPermission> listDataPermission(AefsysDataPermission dataPermission);
	 
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
     * @param dataPermission 数据权限修改信息
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
     * 删除数据权限信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDataPermissionByIds(String ids);
	
}
