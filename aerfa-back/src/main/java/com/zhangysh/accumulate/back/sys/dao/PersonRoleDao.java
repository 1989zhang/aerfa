package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonRole;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 人员角色关系数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年03月01日
 */
@Mapper
public interface PersonRoleDao {
	
	/**
     * 根据条件查询人员角色关系列表
     * 
     * @param personRole 条件对象
     * @return 人员角色关系条件下的集合
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
     * @param roleId 角色ID
     * @return 删除结果条数
     */
	 int deletePersonRoleByRoleId(Long roleId);
	
}