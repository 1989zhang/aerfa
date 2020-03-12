package com.zhangysh.accumulate.back.tdm.service;

import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmFillRuleDataDto;

/**
 * 模板填充规则相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public interface IFillRuleService {
	
	/***
	 * 根据字段id获取前台展示设置样式列表，含默认的值
	 * @param fieldIds 字段ids
	 ****/
	BsTableDataInfo getSettingFieldList(String fieldIds);
	
	/***
	 * 保存填写的模板填充规则对象
	 * @param tableDataDto bootstrap表格对象json字符
	 * @return 是否保存成功
	 */
	boolean saveTableData(AeftdmFillRuleDataDto tableDataDto);
	
	/****
     * 根据条件查询填充规则不分页列表
     * 
     * @param fillRule 条件填充规则对象
     * @return 填充规则条件下结果集合
	 **/
	List<AeftdmFillRule> listFillRule(AeftdmFillRule fillRule);
	
	/***
	 * 根据模板和位置信息,或者替换字符标记,删除填充规则
	 * @param tableDataDto 参数对象
	 ****/
	int deleteFillRuleByMark(AeftdmFillRuleDataDto tableDataDto);

	/**
	 * 获取填充规则Map集合,key为填充规则的replaceChar
	 * @param fillRule 查询条件填充规则对象
	 */
	Map<String,AeftdmFillRule> getFillRuleMap(AeftdmFillRule fillRule);

	/**
	 * 根据模板ID获取里面已绑定的替换字符
	 * @param templateId 模板ID
	 ***/
	String getReplaceCharArr(Long templateId);
}
