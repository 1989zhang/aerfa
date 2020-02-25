package com.zhangysh.accumulate.back.comm.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoContent;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 发布内容相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年02月24日
 */
public interface IInfoContentService {

	/**
	 * 根据ID查询单个发布内容信息
	 *
	 * @param id 发布内容主键ID
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
     * 根据主键ids查询发布内容不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 发布内容条件下结果集合
     */
	 List<AefcommInfoContent> listBypksInfoContent(String ids);
	 
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
     * @param infoContent 发布内容修改信息
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
     * 删除发布内容信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteInfoContentByIds(String ids);
	
}
