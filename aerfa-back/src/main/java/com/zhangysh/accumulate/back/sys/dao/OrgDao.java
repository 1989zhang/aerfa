package com.zhangysh.accumulate.back.sys.dao;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
/**
 * 单位部门到数据库层方法映射
 * @author zhangysh
 * @date 2018年10月20日
 */
@Mapper
public interface OrgDao {
    /**
     * 根据部门id获取部门实体
     * 
     * @param id 主键
     * @return 部门实体对象
     */
	AefsysOrg getOrgById(Long id);
	
	/****
	 *获取顶级单位列表，父单位序号为空null或者为0
	 *
	 *@return 顶级单位部门集合
	 ***/
	List<AefsysOrg> listTopOrg();
	
	/****
	 *模糊查询获取单位列表，因为要显示父子结构查询所有，所哟service的PageHelper就不设置了
	 *
	 *@param org 查询条件
	 *@return 单位部门集合
	 ***/
	List<AefsysOrg> listOrg(AefsysOrg org);
	
	/****
	 *唯一性单位全称检测，查询出部门list集合，检查fullName和id
	 *
	 *@param org 查询条件
	 *@return 单位部门集合
	 ***/
	List<AefsysOrg> checkOrgUnique(AefsysOrg org);
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
     * @param org 组织单位信息
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
