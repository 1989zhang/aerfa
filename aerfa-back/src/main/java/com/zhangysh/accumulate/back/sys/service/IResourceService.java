package com.zhangysh.accumulate.back.sys.service;

import java.util.List;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysResourceVo;

/*****
 * 资源相关service接口
 * @author zhangysh
 * @date 2019年4月7日
 *****/
public interface IResourceService {

	/**
     * 根据资源id获取资源实体，不含父资源对象信息，只有父资源id
     * 
     * @param id 资源主键
     * @return 资源实体对象
     */
	AefsysResource getResourceById(Long id);
	
	/**
     * 根据资源id获取资源实体，含父资源对象名称信息
     * 
     * @param id 资源主键
     * @return 资源实体对象
     */
	AefsysResourceVo getResourceWithParentResourceNameById(Long id);
	
	/***
	 *一次查询出所有资源信息且含树形结构
	 *@return 树形结构的资源列表 
	 *******/
	List<BsTreeNode> listAllResourceWithTreeStructure();

	/****
	 *普通模糊查询条件下的资源list集合，未分页排序等 
	 *@param resource 查询条件 
	 ****/
	List<AefsysResourceVo> listResource(AefsysResource resource);

	/**
	 *一次查询出所有资源信息且含父子结构
	 *@return 带父子结构的list集合 
	 *****/
	List<AefsysResourceVo> listAllResourceWithParentStructure();
	
	/****
	 *唯一性资源标记检测，查询出资源list集合，未分页排序等 
	 *@param resource 查询条件 
	 ****/
	List<AefsysResourceVo> checkResourceUnique(AefsysResource resource);
	
	/**
     * 新增资源对象
     * 
     * @param resource 资源对象信息
     * @return 新增结果条数
     */
	 int insertResource(AefsysResource resource);
	
	/**
     * 修改资源对象
     * 
     * @param resource 要修改的资源对象
     * @return 修改结果条数
     */
	 int updateResource(AefsysResource resource);
	
	/**
     * 删除单个资源信息
     * 
     * @param id 资源主键
     * @return 删除结果条数
     */
	 int deleteResourceById(Long id);

	/**
	 * 根据人员的ID获取他拥有的资源带父级结构集合，不管有效状态；
	 * 查询逻辑为按钮到菜单到模块到系统，显示逻辑为系统到模块到菜单到按钮；
	 * 超级管理员显示所有资源
	 * @param personId 人员ID
	 * @return 人员拥有资源带父子结构集合
	 */
	List<AefsysResourceVo> getPersonStructResourcesByPersonId(Long personId);

}
