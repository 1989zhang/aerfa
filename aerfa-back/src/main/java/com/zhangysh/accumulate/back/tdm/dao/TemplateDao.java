package com.zhangysh.accumulate.back.tdm.dao;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmTemplate;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 模板定义数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Mapper
public interface TemplateDao {
	/**
     * 根据ID查询单个模板定义信息
     * 
     * @param id 主键ID
     * @return 模板定义信息
     */
	 AeftdmTemplate getTemplateById(Long id);
	
	/**
     * 根据条件查询模板定义列表
     * 
     * @param template 条件对象
     * @return 模板定义条件下的集合
     */
	 List<AeftdmTemplate> listTemplate(AeftdmTemplate template);
	 
	/**
     * 新增模板定义
     * 
     * @param template 模板定义对象信息
     * @return 新增结果条数
     */
	 int insertTemplate(AeftdmTemplate template);
	
	/**
     * 修改模板定义
     * 
     * @param template 模板定义信息
     * @return 修改结果条数
     */
	 int updateTemplate(AeftdmTemplate template);
	
	/**
     * 单个删除模板定义
     * 
     * @param id 模板定义ID
     * @return 删除结果条数
     */
	 int deleteTemplateById(Long id);
	
	/**
     * 批量删除模板定义
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteTemplateByIds(String[] ids);
	
}