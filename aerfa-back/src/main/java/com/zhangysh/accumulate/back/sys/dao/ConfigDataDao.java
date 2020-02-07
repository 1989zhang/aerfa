package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 配置数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年05月24日
 */
@Mapper
public interface ConfigDataDao {
	/**
     * 根据ID查询单个配置信息
     * 
     * @param id 主键ID
     * @return 配置信息
     */
	 AefsysConfigData getConfigDataById(Long id);
	
	/**
     * 根据条件查询配置列表
     * 
     * @param configData 条件对象
     * @return 配置条件下的集合
     */
	 List<AefsysConfigData> listConfigData(AefsysConfigData configData);
	 
	/****
	 * 配置参数编码唯一性检测，查询出配置list集合，检查参数编码和id
	 * 因为检测不用like所以单独列出来
	 * @param configData 查询条件,内含参数编码属性和id,id为了排除自己
	 * @return 配置结果集合
	 ***/
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
     * @param configData 配置信息
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
     * 批量删除配置
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteConfigDataByIds(String[] ids);
	
}