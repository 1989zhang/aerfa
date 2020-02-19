package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysResourceVo;

/**
 * 角色相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年05月16日
 */
public interface IRoleService {
	/**
     * 根据ID查询单个角色信息
     * 
     * @param id 角色主键ID
     * @return 角色信息
     */
	 AefsysRole getRoleById(Long id);
	
	/**
     * 根据条件查询角色分页列表
     * 
     * @param pageInfo 分页对象
     * @param role 条件角色对象
     * @return 角色条件下结果集合
     */
	 BsTableDataInfo listPageRole(BsTablePageInfo pageInfo,AefsysRole role);
	
	/**
     * 根据条件查询角色不分页列表
     * 
     * @param role 条件角色对象
     * @return 角色条件下结果集合
     */
	 List<AefsysRole> listRole(AefsysRole role);

	/****
	 * 唯一性角色编码标记检测，查询出资源list集合，未分页排序等
	 * @param role 查询条件
	 ****/
	List<AefsysRole> checkRoleUnique(AefsysRole role);

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
     * @param role 角色修改信息
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
     * 删除角色信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteRoleByIds(String ids);


	 /**
	  * 根据人员的ID获取他拥有的角色集合
	  * @param personId 人员ID
	  * @retrun 人员拥有的角色集合
	  * ***/
     List<AefsysRole> getPersonRolesByPersonId(Long personId);
}
