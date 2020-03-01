package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRoleResource;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 角色资源关系数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年02月28日
 */
@Mapper
public interface RoleResourceDao {
	
	/**
     * 根据条件查询角色资源关系列表
     * 
     * @param roleResource 条件对象
     * @return 角色资源关系条件下的集合
     */
	 List<AefsysRoleResource> listRoleResource(AefsysRoleResource roleResource);
	 
	/**
     * 新增角色资源关系
     * 
     * @param roleResource 角色资源关系对象信息
     * @return 新增结果条数
     */
	 int insertRoleResource(AefsysRoleResource roleResource);
	
	/**
     * 根据角色删除角色资源关系
     * 
     * @param roleId 角色ID
     * @return 删除结果条数
     */
	 int deleteRoleResourceByRoleId(Long roleId);

	/**
	 * 根据资源删除角色资源关系
	 *
	 * @param resourceId 资源ID
	 * @return 删除结果条数
	 */
	int deleteRoleResourceByResourceId(Long resourceId);
	
}