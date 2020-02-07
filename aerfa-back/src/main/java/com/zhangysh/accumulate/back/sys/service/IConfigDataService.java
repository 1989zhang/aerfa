package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 配置相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年05月24日
 */
public interface IConfigDataService {
	
	/**
	 * 根据配置code从redis获取一个配置文件，建议用此方法获取配置参数信息！
	 * 为了方便其他方法使用，所以就统一在service管理了，没有配置则返回null,get时注意空指针处理。
	 * ****/
	AefsysConfigData getConfigDataFromRedisByCode(String dataCode);
	
	/**
	 * 从redis获取所有配置文件，建议用此方法获取配置参数信息！
	 * 为了方便其他方法使用，所以就统一在service管理了。
	 * ****/
	List<AefsysConfigData> getAllConfigDataFromRedis();
	
	/**
     * 根据ID查询单个配置信息
     * 
     * @param id 配置主键ID
     * @return 配置信息
     */
	 AefsysConfigData getConfigDataById(Long id);
	
	/**
     * 根据条件查询配置分页列表
     * 
     * @param pageInfo 分页对象
     * @param configData 条件配置对象
     * @return 配置条件下结果集合
     */
	 BsTableDataInfo listPageConfigData(BsTablePageInfo pageInfo,AefsysConfigData configData);
	
	/**
     * 根据条件查询配置不分页列表
     * 
     * @param configData 条件配置对象
     * @return 配置条件下结果集合
     */
	 List<AefsysConfigData> listConfigData(AefsysConfigData configData);
	 
	/****
	 * 配置参数编码唯一性检测，查询出配置list集合，未分页排序等 
	 * @param configData 查询条件,内含参数编码属性和id,id为了排除自己
	 ****/
	List<AefsysConfigData> checkDataCodeUnique(AefsysConfigData configData);
	/**
     * 新增配置
     * 
     * @param configData 配置对象信息
     * @return 新增结果条数
     */
	 int insertConfigData(AefsysConfigData configData);
	
	/**
     * 修改配置
     * 
     * @param configData 配置修改信息
     * @return 修改结果条数
     */
	 int updateConfigData(AefsysConfigData configData);
	
	/**
     * 单个删除配置
     * 
     * @param id 配置ID
     * @return 删除结果条数
     */
	 int deleteConfigDataById(Long id);
	 	
	/**
     * 删除配置信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteConfigDataByIds(String ids);
	
}
