package com.zhangysh.accumulate.back.webim.dao;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 群组数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年10月09日
 */
@Mapper
public interface GroupDao {
	/**
     * 根据ID查询单个群组信息
     * 
     * @param id 主键ID
     * @return 群组信息
     */
	 AefwebimGroup getGroupById(Long id);
	
	/**
     * 根据条件查询群组列表
     * 
     * @param group 条件对象
     * @return 群组条件下的集合
     */
	 List<AefwebimGroup> listGroup(AefwebimGroup group);
	 
	/**
     * 根据主键ids查询群组
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 群组条件下的集合
     */
	 List<AefwebimGroup> listBypksGroup(String[] ids);
	 
	/**
     * 新增群组
     * 
     * @param group 群组对象信息
     * @return 新增结果条数
     */
	 int insertGroup(AefwebimGroup group);
	
	/**
     * 修改群组
     * 
     * @param group 群组信息
     * @return 修改结果条数
     */
	 int updateGroup(AefwebimGroup group);
	
	/**
     * 单个删除群组
     * 
     * @param id 群组ID
     * @return 删除结果条数
     */
	 int deleteGroupById(Long id);
	
	/**
     * 批量删除群组
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteGroupByIds(String[] ids);
	
}