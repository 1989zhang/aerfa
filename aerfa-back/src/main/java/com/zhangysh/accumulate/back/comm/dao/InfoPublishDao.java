package com.zhangysh.accumulate.back.comm.dao;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 发布数据层对应接口
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Mapper
public interface InfoPublishDao {
	/**
     * 根据ID查询单个发布信息
     * 
     * @param id 主键ID
     * @return 发布信息
     */
	 AefcommInfoPublish getInfoPublishById(Long id);
	
	/**
     * 根据条件查询发布列表
     * 
     * @param infoPublish 条件对象
     * @return 发布条件下的集合
     */
	 List<AefcommInfoPublish> listInfoPublish(AefcommInfoPublish infoPublish);
	 
	/**
     * 根据主键ids查询发布
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 发布条件下的集合
     */
	 List<AefcommInfoPublish> listBypksInfoPublish(String[] ids);
	 
	/**
     * 新增发布
     * 
     * @param infoPublish 发布对象信息
     * @return 新增结果条数
     */
	 int insertInfoPublish(AefcommInfoPublish infoPublish);
	
	/**
     * 修改发布
     * 
     * @param infoPublish 发布信息
     * @return 修改结果条数
     */
	 int updateInfoPublish(AefcommInfoPublish infoPublish);
	
	/**
     * 单个删除发布
     * 
     * @param id 发布ID
     * @return 删除结果条数
     */
	 int deleteInfoPublishById(Long id);
	
	/**
     * 批量删除发布
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteInfoPublishByIds(String[] ids);

	 /**
	  * 获取信息发布满足条件共有数据条数
	  * @return aefcomm_info_publish表总数据条数
	  **/
	 long getAllRowCountByParam(AefcommInfoPublish infoPublish);
	
}