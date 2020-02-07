package com.zhangysh.accumulate.back.tdm.dao;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 模板填充规则数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Mapper
public interface FillRuleDao {
	/**
     * 根据ID查询单个模板填充规则信息
     * 
     * @param id 主键ID
     * @return 模板填充规则信息
     */
	 AeftdmFillRule getFillRuleById(Long id);
	
	/**
     * 根据条件查询模板填充规则列表
     * 
     * @param fillRule 条件对象
     * @return 模板填充规则条件下的集合
     */
	 List<AeftdmFillRule> listFillRule(AeftdmFillRule fillRule);
	 
	/**
     * 新增模板填充规则
     * 
     * @param fillRule 模板填充规则对象信息
     * @return 新增结果条数
     */
	 int insertFillRule(AeftdmFillRule fillRule);
	
	/**
     * 修改模板填充规则
     * 
     * @param fillRule 模板填充规则信息
     * @return 修改结果条数
     */
	 int updateFillRule(AeftdmFillRule fillRule);
	
	/**
     * 单个删除模板填充规则
     * 
     * @param id 模板填充规则ID
     * @return 删除结果条数
     */
	 int deleteFillRuleById(Long id);
	
	/**
     * 批量删除模板填充规则
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteFillRuleByIds(String[] ids);
	
}