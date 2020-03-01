package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonRole;
/**
 * 人员角色关系相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年03月01日
 */
public interface IPersonRoleService {
	 
	/**
     * 根据条件查询人员角色关系不分页列表
     * 
     * @param personRole 条件人员角色关系对象
     * @return 人员角色关系条件下结果集合
     */
	 List<AefsysPersonRole> listPersonRole(AefsysPersonRole personRole);
	 
	/**
     * 新增人员角色关系
     * 
     * @param personRole 人员角色关系对象信息
     * @return 新增结果条数
     */
	 int insertPersonRole(AefsysPersonRole personRole);

	
	/**
     * 根据人员ID删除人员角色关系
     * 
     * @param personId 人员ID
     * @return 删除结果条数
     */
	 int deletePersonRoleByPersonId(Long personId);
	 	
	/**
     * 根据角色ID删除人员角色关系
     * 
     * @param roleId 角色id
     * @return 删除结果条数
     */
	 int deletePersonRoleByRoleId(Long roleId);
	
}
