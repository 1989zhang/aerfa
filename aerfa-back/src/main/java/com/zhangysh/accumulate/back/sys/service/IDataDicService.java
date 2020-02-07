package com.zhangysh.accumulate.back.sys.service;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataDic;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysDataDicVo;
import java.util.List;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 数据字典相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年04月15日
 */
public interface IDataDicService {
	/**
     * 根据ID查询单个数据字典信息
     * 
     * @param id 数据字典主键ID
     * @return 数据字典信息
     */
	 AefsysDataDic getDataDicById(Long id);
	
	/**
     * 根据条件查询数据字典分页列表
     * 
     * @param pageInfo 分页对象
     * @param dataDic 条件数据字典对象
     * @return 数据字典条件下结果集合
     */
	 BsTableDataInfo listPageDataDic(BsTablePageInfo pageInfo,AefsysDataDic dataDic);
	 
	/**
     * 根据条件查询数据字典不分页列表
     * 
     * @param dataDic 条件数据字典对象
     * @return 数据字典条件下结果集合
     */
	 List<AefsysDataDicVo> listDataDic(AefsysDataDic dataDic);
	 
	/****
	 *唯一性资源标记检测，查询出资源list集合，未分页排序等 
	 *@param resource 查询条件 
	 ****/
	 List<AefsysDataDicVo> checkDataDicUnique(AefsysDataDic dataDic);
		
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
     * @param dataDic 数据字典修改信息
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
     * 删除数据字典信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDataDicByIds(String ids);
	
	 /**
	  *  根据字典类型编码,获取redis里面的字典列表,redis里面不包含字典类型,如果没有重新获取且设置到redis中
	  *  @param typeCode 类型编码 
	  **/
	 String getDataDicByTypeCodeFromRedis(String typeCode);
	 
	 /***
	  * 根据字典类型编码，获取字典类型对象
	  ****/
	 AefsysDataDicVo getDicTypeByTypeCode(String typeCode);
	 
}
