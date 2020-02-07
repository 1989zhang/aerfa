package com.zhangysh.accumulate.back.tdm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateDto;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateFileDto;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateParmDto;
import com.zhangysh.accumulate.back.tdm.service.ITemplateService;

/**
 * 模板定义调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Controller
@RequestMapping("/tdm/template")
public class TemplateController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	private ITemplateService templateService;
	@Autowired
    private IRedisRelatedService redisRelatedService;

    
	/****
	 * 获取展示模板定义信息列表
	 * @param request 请求对象
	 * @param AeftdmTemplateDto 分页和查询对象
	 * @return 获取到的模板定义对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AeftdmTemplateDto templateDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",templateDto.getPageInfo().getPageNum(),templateDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=templateService.listPageTemplate(templateDto.getPageInfo(),templateDto.getTemplate());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个模板定义,以便修改
	 * @param request 请求对象
     * @param id 模板定义主键ID
     * @return 模板定义信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle模板定义主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(templateService.getTemplateById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的模板定义信息 
	 * @param request 请求对象
	 * @param template 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveTemplate(HttpServletRequest request,@RequestBody AeftdmTemplateFileDto template) {
		try {	
			String aerfatoken=HttpStorageUtil.getToken(request); 
			AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
			//首先保存模板文件到模板目录
			if(StringUtil.isNotEmpty(template.getFileBase64Data())) {
				//基本信息赋值
				String fileName=template.getFileName();
				String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
				String uuid =DateOperate.getDateYmdHms();
				String saveName=uuid+"."+suffix;
				template.setSaveName(saveName);
				template.setFileType(suffix);
				//保存至存储
				templateService.saveTemplateFile(template.getFileBase64Data(), saveName);
			}
			//再保存模板基本信息
			if ( template.getId()!=null ) {//修改方法
			    template.setUpdateTime(DateOperate.getCurrentUtilDate());
				template.setUpdateBy(operPerson.getPersonName());
				return toHandlerResultStr(templateService.updateTemplate(template));
			} else {//新增方法
				template.setCreateTime(DateOperate.getCurrentUtilDate());
				template.setCreateBy(operPerson.getPersonName());
				return toHandlerResultStr(templateService.insertTemplate(template));
			}
		} catch (IOException e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage()),null);
		}
	}
	
	/****
	 * 删除模板定义对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的模板定义ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="系统工具",menu="模板定义",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteTemplateByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(templateService.deleteTemplateByIds(ids));
	}
	
	
	/****
	 * 获取excell和word等模板内容 
	 * @param aerfatoken token对象
	 * @param id 模板定义的id
	 ***/
	@RequestMapping(value = "/template_content",method = RequestMethod.POST)
	@ResponseBody
	public String getTemplateContent(HttpServletRequest request,@RequestBody Long id) {
		try {
			return JSON.toJSONString(ResultVo.success(templateService.getTemplateContent(id))); 
		} catch (Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR.fillArgs(e.getMessage()),null);
		}
	}
	
	/****
	 * 获取模板对应数据源的所有参数，以逗号分割返回
	 * @param request 请求对象
	 * @param id 模板定义的id
	 ***/
	@RequestMapping(value = "/template_parameter",method = RequestMethod.POST)
	@ResponseBody
	public String getTemplateParameter(HttpServletRequest request,@RequestBody Long id) {
		try {
			return JSON.toJSONString(ResultVo.success(templateService.getTemplateParameter(id))); 
		} catch (Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR.fillArgs(e.getMessage()),null);
		}
	}

	/****
	 * 传递参数，获取pdf展示文件内容  
	 */
	@RequestMapping(value = "/view_data",method = RequestMethod.POST)
	@ResponseBody
	public String getViewData(HttpServletRequest request,@RequestBody AeftdmTemplateParmDto templateParmDto) {
		try {
			return JSON.toJSONString(ResultVo.success(templateService.getViewData(templateParmDto))); 
		} catch (Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR.fillArgs(e.getMessage()),null);
		}
	}
	
	/****
	 * 获取excell已填充位置和字段信息  格式(行:列:字段)
	 * @param request 请求对象
	 * @param id 模板定义的id
	 ***/
	@RequestMapping(value = "/template_excel_fill_info",method = RequestMethod.POST)
	@ResponseBody
	public String getTemplateExcelFillInfo(HttpServletRequest request,@RequestBody Long id) {
		try {
			return JSON.toJSONString(ResultVo.success(templateService.getTemplateExcelFillInfo(id))); 
		} catch (Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR.fillArgs(e.getMessage()),null);
		}
	}
}
