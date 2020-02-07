package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataDic;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 数据字典数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年04月15日
 */
@Mapper
public interface DataDicDao {
	/**
     * 根据ID查询单个数据字典信息
     * 
     * @param id 主键ID
     * @return 数据字典信息
     */
	 AefsysDataDic getDataDicById(Long id);
	
	/**
     * 根据条件查询数据字典列表
     * 
     * @param dataDic 条件对象
     * @return 数据字典条件下的集合
     */
	 List<AefsysDataDic> listDataDic(AefsysDataDic dataDic);
	
	/****
	 *唯一性资源标识检测，查询出资源list集合，检查资源identify和id
	 *因为检测不用like所以单独列出来
	 *@param resource 查询条件
	 *@return 资源集合
	 ***/
	List<AefsysDataDic> checkDataDicUnique(AefsysDataDic dataDic);
	
	/**
     * 新增数据字典
     * 
     * @param dataDic 数据字典对象信息
     * @return 新增结果条数
     */
	 int insertDataDic(AefsysDataDic dataDic);
	
	/**
     * 修改数据字典
     * 
     * @param dataDic 数据字典信息
     * @return 修改结果条数
     */
	 int updateDataDic(AefsysDataDic dataDic);
	
	/**
     * 单个删除数据字典
     * 
     * @param id 数据字典ID
     * @return 删除结果条数
     */
	 int deleteDataDicById(Long id);
	
	/**
     * 批量删除数据字典
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDataDicByIds(String[] ids);
	
}