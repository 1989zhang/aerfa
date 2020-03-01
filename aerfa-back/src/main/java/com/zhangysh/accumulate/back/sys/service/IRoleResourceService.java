package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRoleResource;

/**
 * 角色资源关系相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年02月28日
 */
public interface IRoleResourceService {
	 
	/**
     * 根据条件查询角色资源关系不分页列表
     * 
     * @param roleResource 条件角色资源关系对象
     * @return 角色资源关系条件下结果集合
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
     * 单个删除角色资源关系
     * 
     * @param roleId 角色资源关系ID
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
