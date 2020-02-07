package com.zhangysh.accumulate.back.support.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IGenerateCodeService;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.pojo.support.dataobj.ColumnInfo;
import com.zhangysh.accumulate.pojo.support.transobj.GenerateCodeParamDto;
import com.zhangysh.accumulate.pojo.support.transobj.TableInfoDto;

/*****
 * 代码生成相关方法
 * @author zhangysh
 * @date 2019年5月11日
 *****/
@Controller
@RequestMapping("/support/code")
public class GenerateCodeController {

	@Autowired
	private IGenerateCodeService generateCodeService;
	
	private static final Logger logger=LoggerFactory.getLogger(GenerateCodeController.class);

	/****
	 *代码生成方法，展现数据库表列表
	 *@param request 请求对象
	 *@param tableInfoDto 分页和查询对象
	 *@return 获取到的系统表列表集合JSON
	 ****/
	@RequestMapping(value="/table_list",method = RequestMethod.POST)
    @ResponseBody
	public String getTableList(HttpServletRequest request,@RequestBody TableInfoDto tableInfoDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",tableInfoDto.getPageInfo().getPageNum(),tableInfoDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=generateCodeService.listPageTables(tableInfoDto.getPageInfo(),tableInfoDto.getTableInfo());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 *根据表名获取表的列
	 *@param request 请求对象
	 *@param tableName 表名称对象
	 *@return 获取到的系统表列名集合JSON
	 ****/
	@RequestMapping(value="/table_column",method = RequestMethod.POST)
    @ResponseBody
	public String getTableColumns(@RequestBody String tableName) {
		List<ColumnInfo> retColumnsList=generateCodeService.getTableColumnsByName(tableName);
		return JSON.toJSONStringWithDateFormat(retColumnsList,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 根据参数生成代码,后台返回文件的base64的字符串
	 *@param request 请求对象
	 *@param codeParamDto 生成代码相关参数
	 *@return 文件base64加密后的字符串
	 ****/
	@RequestMapping(value="/generate",method = RequestMethod.POST)
    @ResponseBody
	public String generateCode(HttpServletRequest request,@RequestBody GenerateCodeParamDto codeParamDto) {
		byte[] retByte=generateCodeService.generatorCode(codeParamDto);
		return InputStreamUtil.ByteToBase64(retByte);
	}
}
