package com.zhangysh.accumulate.back.comm.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.comm.viewobj.AefcommInfoPublishVo;

/**
 * 发布相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
public interface IInfoPublishService {
	/**
     * 根据ID查询单个发布信息,级联内容查询
     * 
     * @param id 发布主键ID
     * @return 发布信息
     */
	 AefcommInfoPublishVo getInfoPublishById(Long id);

	/**
	 * 根据主键ids查询发布不分页列表,级联内容查询
	 *
	 * @param ids 需要查询的数据ID以,拼装
	 * @return 发布条件下结果集合
	 */
	List<AefcommInfoPublishVo> listBypksInfoPublish(String ids);

	/**
     * 根据条件查询发布分页列表,不查询内容
     * 
     * @param pageInfo 分页对象
     * @param infoPublish 条件发布对象
     * @return 发布条件下结果集合
     */
	 BsTableDataInfo listPageInfoPublish(BsTablePageInfo pageInfo, AefcommInfoPublish infoPublish);

	/**
	 * 根据条件查询发布不分页列表,不查询内容
	 *
	 * @param infoPublish 条件发布对象
	 * @return 发布条件下结果集合
	 */
	List<AefcommInfoPublish> listInfoPublish(AefcommInfoPublish infoPublish);

	/**
     * 新增发布,级联内容新增
     * 
     * @param infoPublishVo 发布对象信息
     * @return 新增结果条数
     */
	 int insertInfoPublish(AefcommInfoPublishVo infoPublishVo);
	
	/**
     * 修改发布,级联内容修改
     * 
     * @param infoPublishVo 发布修改信息
     * @return 修改结果条数
     */
	 int updateInfoPublish(AefcommInfoPublishVo infoPublishVo);
	
	/**
     * 单个删除发布,级联内容删除
     * 
     * @param id 发布ID
     * @return 删除结果条数
     */
	 int deleteInfoPublishById(Long id);
	 	
	/**
     * 删除发布信息,级联内容删除
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteInfoPublishByIds(String ids);
	
}
