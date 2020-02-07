package com.zhangysh.accumulate.ui.support.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.support.transobj.GenerateCodeParamDto;
import com.zhangysh.accumulate.pojo.support.transobj.TableInfoDto;

/*****
 * 代码生成相关方法
 * @author zhangysh
 * @date 2019年5月11日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IGenerateCodeService {

	/*****
	 * 代码生成获取系统表列表
	 * @param aerfatoken token对象
	 * @param tableInfoDto 查询条件和分页对象
	 ***/
	@RequestMapping(value = "/support/code/table_list",method = RequestMethod.POST)
	public String getTableList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody TableInfoDto tableInfoDto);
	
	/****
	 * 根据表名获取表的列
	 * @param tableName 表名
	 **/
	@RequestMapping(value = "/support/code/table_column",method = RequestMethod.POST)
	public String getTableColumns(@RequestBody String tableName);
	
	/****
	 * 根据参数生成代码,后台返回文件的base64的字符串
	 * @param codeParamDto 代码生成相关参数
	 **/
	@RequestMapping(value = "/support/code/generate",method = RequestMethod.POST)
	public String generateCode(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody GenerateCodeParamDto codeParamDto);

}
