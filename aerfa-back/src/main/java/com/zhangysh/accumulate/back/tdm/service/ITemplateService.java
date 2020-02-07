package com.zhangysh.accumulate.back.tdm.service;
import java.io.IOException;
import java.util.List;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmTemplate;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateParmDto;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 模板定义相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
public interface ITemplateService {
	/**
     * 根据ID查询单个模板定义信息
     * 
     * @param id 模板定义主键ID
     * @return 模板定义信息
     */
	 AeftdmTemplate getTemplateById(Long id);
	
	/**
     * 根据条件查询模板定义分页列表
     * 
     * @param pageInfo 分页对象
     * @param template 条件模板定义对象
     * @return 模板定义条件下结果集合
     */
	 BsTableDataInfo listPageTemplate(BsTablePageInfo pageInfo,AeftdmTemplate template);
	
	/**
     * 根据条件查询模板定义不分页列表
     * 
     * @param template 条件模板定义对象
     * @return 模板定义条件下结果集合
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
     * @param template 模板定义修改信息
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
     * 删除模板定义信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteTemplateByIds(String ids);
	
	 /**
	  * 保存模板文件内容到模板文件夹下面
	  ***/
	 void saveTemplateFile(String fileBase64Str,String saveName) throws IOException;
	 
	/****
	 * 获取excell和word等模板内容 
	 * @param id 模板定义的id
	 * @return 获取到的模板内容，类型不固定
	 ***/
	 Object getTemplateContent(Long id) throws Exception;
	 
	/****
	 * 获取模板对应数据源的所有参数
	 * @param id 模板定义的id
	 * @return 以逗号分割返回
	 ***/
	 String getTemplateParameter(Long templateId) throws Exception;
	 
	 /**
	  * 获取pdf展示文件内容 
	  ****/
	 String getViewData(AeftdmTemplateParmDto templateParmDto) throws Exception; 
	 
	/****
	 * 获取excell已填充位置和字段信息  格式(行:列:字段)
	 * @param id 模板定义的id
	 * @return 以逗号分割返回
	 ***/
	 String getTemplateExcelFillInfo(Long templateId) throws Exception;
}
