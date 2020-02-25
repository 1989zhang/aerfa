package com.zhangysh.accumulate.back.comm.dao;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoContent;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 发布内容数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年02月24日
 */
@Mapper
public interface InfoContentDao {
	/**
     * 根据ID查询单个发布内容信息
     * 
     * @param id 主键ID
     * @return 发布内容信息
     */
	 AefcommInfoContent getInfoContentById(Long id);

	/**
	 * 根据发布信息ID查询单个发布内容信息
	 *
	 * @param publishId 发布信息主键ID
	 * @return 发布内容信息
	 */
	AefcommInfoContent getInfoContentByPublishId(Long publishId);

	/**
     * 根据条件查询发布内容列表
     *
     * @param infoContent 条件对象
     * @return 发布内容条件下的集合
     */
	 List<AefcommInfoContent> listInfoContent(AefcommInfoContent infoContent);
	 
	/**
     * 根据主键ids查询发布内容
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 发布内容条件下的集合
     */
	 List<AefcommInfoContent> listBypksInfoContent(String[] ids);
	 
	/**
     * 新增发布内容
     * 
     * @param infoContent 发布内容对象信息
     * @return 新增结果条数
     */
	 int insertInfoContent(AefcommInfoContent infoContent);
	
	/**
     * 修改发布内容
     * 
     * @param infoContent 发布内容信息
     * @return 修改结果条数
     */
	 int updateInfoContent(AefcommInfoContent infoContent);
	
	/**
     * 单个删除发布内容
     * 
     * @param id 发布内容ID
     * @return 删除结果条数
     */
	 int deleteInfoContentById(Long id);

	/**
	 * 根据发布信息ID删除单个发布内容信息
	 *
	 * @param publishId 发布信息主键ID
	 * @return 删除结果条数
	 */
	int deleteInfoContentByPublishId(Long publishId);

	/**
     * 批量删除发布内容
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteInfoContentByIds(String[] ids);
	
}