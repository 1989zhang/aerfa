package com.zhangysh.accumulate.back.sys.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;

/*****
 * 资源到数据库层方法映射
 * @author zhangysh
 * @date 2019年4月7日
 *****/
@Mapper
public interface ResourceDao {
	/**
     * 根据资源id获取资源实体
     * 
     * @param id 资源主键
     * @return 资源实体对象
     */
	AefsysResource getResourceById(Long id);
	
	/****
	 *获取顶级资源列表，父资源序号为空null或者为0
	 *
	 *@return 顶级资源即系统类资源集合
	 ***/
	List<AefsysResource> listTopResource();
	
	/****
	 *模糊查询获取资源列表，因为要显示父子结构查询所有，所以service的PageHelper就不设置了
	 *
	 *@param resource 查询条件
	 *@return 资源集合
	 ***/
	List<AefsysResource> listResource(AefsysResource resource);
	
	/****
	 *唯一性资源标识检测，查询出资源list集合，检查资源identify和id
	 *因为检测不用like所以单独列出来
	 *@param resource 查询条件
	 *@return 资源集合
	 ***/
	List<AefsysResource> checkResourceUnique(AefsysResource resource);
	/**
     * 新增资源
     * 
     * @param resource 资源对象信息
     * @return 新增结果条数
     */
	 int insertResource(AefsysResource resource);
	
	/**
     * 修改资源
     * 
     * @param resource 要修改的资源信息
     * @return 修改结果条数
     */
	 int updateResource(AefsysResource resource);
	
	/**
     * 单个删除资源
     * 
     * @param id 资源主键
     * @return 删除结果条数
     */
	 int deleteResourceById(Long id);

	/**
	 * 根据人员的ID获取他拥有的资源集合
	 * @param personId 人员ID
	 * @return 人员拥有资源集合
	 */
	List<AefsysResource> getPersonResourcesByPersonId(Long personId);
}
