package com.zhangysh.accumulate.ui.tdm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhangysh.accumulate.ui.tdm.service.IFillRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmTemplate;
import com.zhangysh.accumulate.pojo.tdm.viewobj.AeftdmTemplateVo;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateDto;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateFileDto;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateParmDto;
import com.zhangysh.accumulate.ui.tdm.service.ITemplateService;
/**
 * 模板定义调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Controller
@RequestMapping("/tdm/template")
public class TemplateController {

    private String prefix="/tdm/template";//返回界面路径即前缀

	@Autowired
	private ITemplateService templateService;
	@Autowired
	private IFillRuleService fillRuleService;

	/**
	 * 跳转到tdm模板定义页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的模板定义页面
	 ****/
	@RequestMapping(value="/to_template")
	public String toTdmTemplate(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/template";
	}
	
	/****
	 * 获取展示模板定义列表，且分页显示
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AeftdmTemplate template) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AeftdmTemplateDto templateDto=new AeftdmTemplateDto();
		templateDto.setPageInfo(pageInfo);templateDto.setTemplate(template);
		return templateService.getList(aerfatoken,templateDto);
	}
	
	/**
	 * 跳转到sys模板定义新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add")
	public String toAdd(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
    /***
	 * 保存填写的模板定义对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param templateFileDto 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,MultipartFile templateFile,AeftdmTemplateFileDto templateFileDto) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request);
			if (!templateFile.isEmpty()){
				templateFileDto.setFileName(templateFile.getOriginalFilename());
				templateFileDto.setFileBase64Data(InputStreamUtil.ByteToBase64(templateFile.getBytes()));
			}
			return templateService.saveAdd(aerfatoken, templateFileDto);
		} catch (IOException e) {
			e.printStackTrace();
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage())));
		}
	}
	
	/****
	 * 修改模板定义，先获取模板定义信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retTemplateStr=templateService.getSingle(aerfatoken, id);
		AeftdmTemplateVo templateVo=JSON.parseObject(retTemplateStr,AeftdmTemplateVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("template", templateVo);
		return prefix+"/edit";
	}
	
	/***
	 * 删除模板定义对象，可以删除多个，中间英文,隔开
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return templateService.deleteTemplateByIds(aerfatoken, ids);
    }
	
	
	/***
	 * 跳转到模板内容展示界面
	 * @param templateId 模板id
	 * @return 展示内容所在的页面路径
	 ***/
	@RequestMapping(value="/to_template_content/{templateId}")
	public String toTemplateContent(HttpServletRequest request, ModelMap modelMap,@PathVariable("templateId") Long templateId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retTemplateStr=templateService.getSingle(aerfatoken, templateId);
		AeftdmTemplateVo templateVo=JSON.parseObject(retTemplateStr,AeftdmTemplateVo.class);
		if(UtilConstant.FILE_TYPE_EXCEL_XLS.equals(templateVo.getFileType())){
			modelMap.put("templateId",templateId);
			modelMap.addAttribute("prefix",prefix);
			return prefix + "/excel_content";
		}else if(UtilConstant.FILE_TYPE_WORD_DOCX.equals(templateVo.getFileType())) {
			String replaceCharJsonStr=fillRuleService.getReplaceCharArr(aerfatoken,templateId);
			JSONObject replaceCharJson=JSON.parseObject(replaceCharJsonStr);
			if(MarkConstant.MARK_RESULT_VO_SUCESS.equals(replaceCharJson.getInteger(MarkConstant.MARK_RESULT_VO_CODE))) {
				String replaceChar = replaceCharJson.getString(MarkConstant.MARK_RESULT_VO_DATA);
				modelMap.put("replaceChar",replaceChar);
			}
			modelMap.put("templateId",templateId);
			modelMap.addAttribute("prefix",prefix);
			return prefix + "/word_content";
		}
		return "redirect:/404 "; 
	}
	
	/***
	 * 获取excell和word等模板内容 
	 * @param templateId 模板的id
	 ***/
	@RequestMapping(value = "/template_content")
	@ResponseBody
	public String getTemplateContent(HttpServletRequest request, ModelMap modelMap,Long templateId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return templateService.getTemplateContent(aerfatoken, templateId);
	}
	
	/***
	 * 获取模板参数 
	 * @param templateId 模板的id
	 ***/
	@RequestMapping(value = "/template_parameter")
	@ResponseBody
	public String getTemplateParameter(HttpServletRequest request, ModelMap modelMap,Long templateId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return templateService.getTemplateParameter(aerfatoken, templateId);
	}
	
	/***
	 * 报表表单pdf展示预览数据
	 ***/
	@RequestMapping(value={"/to_view_data/{templateId}/{requireParm}","/to_view_data/{templateId}/"})
	public String toViewData(HttpServletRequest request, ModelMap modelMap,@PathVariable("templateId") Long templateId,@PathVariable(value = "requireParm",required = false) String requireParm) {
		modelMap.addAttribute("prefix",prefix);
		modelMap.addAttribute("templateId",templateId);
		modelMap.addAttribute("requireParm",requireParm);
		return prefix + "/viewer_data";
	}
	
	/**
	 *获取pdf展示文件内容 
	 ***/
	@RequestMapping(value = "/view_data/{templateId}/{requireParm}")
	@ResponseBody
	public void getViewData(HttpServletResponse response, HttpServletRequest request,@PathVariable("templateId") Long templateId,@PathVariable("requireParm") String requireParm) {
		ServletOutputStream out = null;
		try {
			
			String aerfatoken=HttpStorageUtil.getToken(request);
			AeftdmTemplateParmDto templateParmDto=new AeftdmTemplateParmDto();
			templateParmDto.setTemplateId(templateId);
			templateParmDto.setRequireParm(requireParm);
			String fileBase64ResultStr=templateService.getViewData(aerfatoken,templateParmDto);
			JSONObject fileBase64Json=JSON.parseObject(fileBase64ResultStr);
			if(MarkConstant.MARK_RESULT_VO_SUCESS.equals(fileBase64Json.getInteger(MarkConstant.MARK_RESULT_VO_CODE))) {
				String fileBase64=fileBase64Json.getString(MarkConstant.MARK_RESULT_VO_DATA);
				byte[] bytes = InputStreamUtil.Base64ToByte(fileBase64);		
				for (int i = 0; i < bytes.length; ++i) {
					if (bytes[i] < 0) {// 调整异常数据
						bytes[i] += 256;
					}
				}
				response.setContentType("application/pdf");
				out = response.getOutputStream();
				byte[] buffer = bytes;
				out.write(buffer, 0, buffer.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * 获取excell已填充位置和字段信息  格式(行:列:字段)
	 * @param templateId 模板的id
	 ***/
	@RequestMapping(value = "/template_excel_fill_info")
	@ResponseBody
	public String getTemplateExcelFillInfo(HttpServletRequest request, ModelMap modelMap,Long templateId) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return templateService.getTemplateExcelFillInfo(aerfatoken, templateId);
	}
	
}