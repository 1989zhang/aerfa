package com.zhangysh.accumulate.ui.tdm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateDto;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateFileDto;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateParmDto;


/*****
 * 模板定义相关调用后台方法
 * @author zhangysh
 * @date 2019年06月19日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface ITemplateService {

	/****
	 * 分页显示模板定义信息
	 * @param aerfatoken token对象
	 * @param TemplateDto 查询条件
	 ***/
	@RequestMapping(value = "/tdm/template/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AeftdmTemplateDto TemplateDto);

	/****
	 * 获取单个模板定义信息
	 * @param aerfatoken token对象
	 * @param id 模板定义的id
	 ***/
	@RequestMapping(value = "/tdm/template/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);
	
	/****
	 * 保存新增的模板定义信息 
	 * @param aerfatoken token对象
	 * @param Template 要保存的模板定义对象
	 ***/
	@RequestMapping(value = "/tdm/template/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AeftdmTemplateFileDto templateFileDto);

	/****
	 * 删除模板定义对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的模板定义ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/tdm/template/delete",method = RequestMethod.POST)
	public String deleteTemplateByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

	/****
	 * 获取excell和word等模板内容 
	 * @param aerfatoken token对象
	 * @param id 模板定义的id
	 ***/
	@RequestMapping(value = "/tdm/template/template_content",method = RequestMethod.POST)
	public String getTemplateContent(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);
	
	/****
	 * 获取模板对应数据源的所有参数，以逗号分割返回
	 * @param aerfatoken token对象
	 * @param id 模板定义的id
	 ***/
	@RequestMapping(value = "/tdm/template/template_parameter",method = RequestMethod.POST)
	public String getTemplateParameter(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);
	
	/****
	 * 传递参数，获取pdf展示文件内容  
	 * @param aerfatoken token对象
	 * @param templateParmDto 获取模板内容的内涵参数
	 ***/
	@RequestMapping(value = "/tdm/template/view_data",method = RequestMethod.POST)
	public String getViewData(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AeftdmTemplateParmDto templateParmDto);

	/****
	 * 获取excell已填充位置和字段信息  格式(行:列:字段)
	 * @param aerfatoken token对象
	 * @param id 模板定义的id
	 ***/
	@RequestMapping(value = "/tdm/template/template_excel_fill_info",method = RequestMethod.POST)
	public String getTemplateExcelFillInfo(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);
	
}