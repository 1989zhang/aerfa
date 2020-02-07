package com.zhangysh.accumulate.back.webim.dao;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 提示消息数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
@Mapper
public interface TipsInfoDao {
	/**
     * 根据ID查询单个提示消息信息
     * 
     * @param id 主键ID
     * @return 提示消息信息
     */
	 AefwebimTipsInfo getTipsInfoById(Long id);
	
	/**
     * 根据条件查询提示消息列表
     * 
     * @param tipsInfo 条件对象
     * @return 提示消息条件下的集合
     */
	 List<AefwebimTipsInfo> listTipsInfo(AefwebimTipsInfo tipsInfo);
	 
	/**
     * 根据主键ids查询提示消息
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 提示消息条件下的集合
     */
	 List<AefwebimTipsInfo> listBypksTipsInfo(String[] ids);
	 
	/**
     * 新增提示消息
     * 
     * @param tipsInfo 提示消息对象信息
     * @return 新增结果条数
     */
	 int insertTipsInfo(AefwebimTipsInfo tipsInfo);
	
	/**
     * 修改提示消息
     * 
     * @param tipsInfo 提示消息信息
     * @return 修改结果条数
     */
	 int updateTipsInfo(AefwebimTipsInfo tipsInfo);
	
	/**
     * 单个删除提示消息
     * 
     * @param id 提示消息ID
     * @return 删除结果条数
     */
	 int deleteTipsInfoById(Long id);
	
	/**
     * 批量删除提示消息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteTipsInfoByIds(String[] ids);
	
}