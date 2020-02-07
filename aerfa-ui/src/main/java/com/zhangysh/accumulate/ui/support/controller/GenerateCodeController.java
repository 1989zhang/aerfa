package com.zhangysh.accumulate.ui.support.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.support.dataobj.TableInfo;
import com.zhangysh.accumulate.pojo.support.transobj.GenerateCodeParamDto;
import com.zhangysh.accumulate.pojo.support.transobj.TableInfoDto;
import com.zhangysh.accumulate.ui.support.service.IGenerateCodeService;
import com.zhangysh.accumulate.ui.sys.service.IOrgService;

/*****
 * 代码生成相关方法
 * @author zhangysh
 * @date 2019年5月11日
 *****/
@Controller
@RequestMapping("/support/code")
public class GenerateCodeController {
	
	private String prefix="/support/code";//返回界面路径即前缀
	private static final Logger logger=LoggerFactory.getLogger(GenerateCodeController.class);
	
	@Resource
	private IGenerateCodeService generateCodeService;
	/****
	 *跳转到代码生成界面
	 *@return templates下的页面
	 **/
	@RequestMapping(value="/to_generate_code")
	public String toGenerateCode(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/generate_code";
	}
	
	/****
	 *跳转到代码生成界面
	 *@return templates下的页面
	 **/
	@RequestMapping(value="/to_step_param")
	public String toStepParam(HttpServletRequest request, ModelMap modelMap,String tableName) {
		logger.info("toStepParam:tableName:"+tableName);
		GenerateCodeParamDto codeParam=new GenerateCodeParamDto();
		codeParam.setTableName(tableName);
		codeParam.setReomveTablePrefix(StringUtil.substring(tableName, "_")+"_");
		codeParam.setAuthor("zhangysh");
		codeParam.setPackageName("com.zhangysh.accumulate.back."+StringUtil.substring(tableName, "_").replace("aef",""));
		
		modelMap.addAttribute("codeParam",codeParam);
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/step_param";
	}
	
	/****
	 *一次性全部展示所有组织单位，且带父子结构 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@param org 查询对象
	 ***/
	@RequestMapping(value="/table_list")
    @ResponseBody
	public String getTableList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,TableInfo tableInfo) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		TableInfoDto tableInfoDto=new TableInfoDto();
		tableInfoDto.setPageInfo(pageInfo);tableInfoDto.setTableInfo(tableInfo);
		return generateCodeService.getTableList(aerfatoken,tableInfoDto);
	}
	
	/**
	 * 根据参数生成代码
	 ****/
	@RequestMapping(value = "/generate")
	@ResponseBody
	public void findFileByParam(HttpServletRequest request,HttpServletResponse response,GenerateCodeParamDto codeParamDto){
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		ServletOutputStream out = null;
		try {
			String aerfatoken=HttpStorageUtil.getToken(request);
			String zipFileName=codeParamDto.getTableName()+"_"+DateOperate.getCurrentStrDate("yyyy_MM_dd")+".zip";
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(zipFileName.getBytes("GB2312"), "ISO8859-1"));
			out = response.getOutputStream();
			String retDataBase64Str = generateCodeService.generateCode(aerfatoken,codeParamDto);
			logger.info("retDataBase64Str:"+retDataBase64Str.length());
			if( retDataBase64Str!=null ){
				byte[] buffer = InputStreamUtil.Base64ToByte(retDataBase64Str); 
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
	
	
}
