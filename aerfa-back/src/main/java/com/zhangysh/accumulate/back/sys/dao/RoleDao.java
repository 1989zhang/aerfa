package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 角色数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年05月16日
 */
@Mapper
public interface RoleDao {
	/**
     * 根据ID查询单个角色信息
     * 
     * @param id 主键ID
     * @return 角色信息
     */
	 AefsysRole getRoleById(Long id);
	
	/**
     * 根据条件查询角色列表
     * 
     * @param role 条件对象
     * @return 角色条件下的集合
     */
	 List<AefsysRole> listRole(AefsysRole role);
	
	/**
     * 新增角色
     * 
     * @param role 角色对象信息
     * @return 新增结果条数
     */
	 int insertRole(AefsysRole role);
	
	/**
     * 修改角色
     * 
     * @param role 角色信息
     * @return 修改结果条数
     */
	 int updateRole(AefsysRole role);
	
	/**
     * 单个删除角色
     * 
     * @param id 角色ID
     * @return 删除结果条数
     */
	 int deleteRoleById(Long id);
	
	/**
     * 批量删除角色
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteRoleByIds(String[] ids);
	
}