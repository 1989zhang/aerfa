package com.zhangysh.accumulate.back.sys.service;

import java.util.List;

import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysOrgVo;

/**
 * 单位组织相关service接口
 * @author zhangysh
 * @date 2018年9月12日
 */
public interface IOrgService {

	/**
     * 根据部门id获取部门实体，不含父单位对象信息，只有父单位id
     * 
     * @param id 部门主键
     * @return 部门单位实体对象
     */
	AefsysOrg getOrgById(Long id);
	
	/**
     * 根据部门id获取部门实体，含父单位对象名称信息
     * 
     * @param id 部门主键
     * @return 部门单位实体对象
     */
	AefsysOrgVo getOrgWithParentOrgNameById(Long id);
	
	/**
	 *一次查询出所有单位部门信息且含父子结构
	 *@param org 查询条件
	 *@return 带父子结构的list集合 
	 *****/
	List<AefsysOrgVo> listAllOrgWithParentStructure();
	
	/***
	 *一次查询出所有单位部门信息且含树形结构
	 *@return 树形结构的单位列表 
	 *******/
	List<BsTreeNode> listAllOrgWithTreeStructure();
	
	/****
	 *普通模糊查询条件下的部门list集合，未分页排序等 
	 *@param org 查询条件 
	 ****/
	List<AefsysOrgVo> listOrg(AefsysOrg org);
	
	/****
	 *唯一性单位全称检测，查询出部门list集合，未分页排序等 
	 *@param org 查询条件 
	 ****/
	List<AefsysOrgVo> checkOrgUnique(AefsysOrg org);
	
	/**
     * 新增组织单位
     * 
     * @param org 组织单位对象信息
     * @return 新增结果条数
     */
	 int insertOrg(AefsysOrg org);
	
	/**
     * 修改组织单位
     * 
     * @param org 组织单位修改信息
     * @return 修改结果条数
     */
	 int updateOrg(AefsysOrg org);
	
	/**
     * 单个删除组织单位
     * 
     * @param id 组织单位ID
     * @return 删除结果条数
     */
	 int deleteOrgById(Long id);
	
}
