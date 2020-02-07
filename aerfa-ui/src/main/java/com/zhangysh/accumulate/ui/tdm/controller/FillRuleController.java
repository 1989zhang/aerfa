package com.zhangysh.accumulate.ui.tdm.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmFillRuleDataDto;
import com.zhangysh.accumulate.ui.tdm.service.IFillRuleService;

/**
 * 模板填充规则调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Controller
@RequestMapping("/tdm/fill_rule")
public class FillRuleController {

    private String prefix="/tdm/fill_rule";//返回界面路径即前缀

	@Autowired
	private IFillRuleService fillRuleService;
	
	/**
	 * 跳转到sys模板填充规则页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @param templateId 模板id
	 * @param locationMark 替换内容位置字符
	 * @return templates下的模板填充规则页面
	 ****/
	@RequestMapping(value="/to_fill_rule")
	public String toTdmFillRule(HttpServletRequest request, ModelMap modelMap,Long templateId,String locationMark) {
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("templateId",templateId);
		modelMap.put("locationMark",locationMark);
		return prefix+"/fill_rule";
	}
	
	/***
	 *跳转到填充规则的字段样式设置 
	 ***/
	@RequestMapping(value="/to_insert_setting")
	public String toInsertSetting(HttpServletRequest request, ModelMap modelMap,Long templateId,String locationMark,String nodeIds) {
		modelMap.addAttribute("prefix",prefix);
		modelMap.put("templateId",templateId);
		modelMap.put("locationMark",locationMark);
		modelMap.put("nodeIds",nodeIds);
		return prefix+"/insert_setting";
	}
	
	/**
	 * 根据列的id获取列设置样式列表
	 ***/
	@RequestMapping(value="/setting_field_list")
    @ResponseBody
    public String getSettingFieldList(HttpServletRequest request, ModelMap modelMap,Long templateId,String locationMark,String nodeIds) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		modelMap.addAttribute("prefix",prefix);
		return fillRuleService.getSettingFieldList(aerfatoken, nodeIds);
	}
	
	
    /***
	 * 保存填写的模板填充规则对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param tableData 保存bootstrap整体的对象
	 ******/
	@RequestMapping(value="/save_table_data")
    @ResponseBody
    public String saveTableData(HttpServletRequest request, ModelMap modelMap,String tableData,String locationMark) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AeftdmFillRuleDataDto fillRuleDataDto=new AeftdmFillRuleDataDto();
		fillRuleDataDto.setTableData(tableData);
		fillRuleDataDto.setLocationMark(locationMark);
		return fillRuleService.saveTableData(aerfatoken, fillRuleDataDto);
	}
	
	/**
	 * 删除填充规则
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @param templateId 模板id
	 * @param locationMark 替换内容位置字符
	 * @return 删除成功信息
	 ****/
	@RequestMapping(value="/remove")
    @ResponseBody
	public String remove(HttpServletRequest request, ModelMap modelMap,Long templateId,String locationMark) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AeftdmFillRuleDataDto fillRuleDataDto=new AeftdmFillRuleDataDto();
		fillRuleDataDto.setTemplateId(templateId);
		fillRuleDataDto.setLocationMark(locationMark);
		return fillRuleService.deleteFillRuleByMark(aerfatoken,fillRuleDataDto);
	}
	
}