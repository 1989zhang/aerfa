package com.zhangysh.accumulate.back.tdm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmDataSourceFieldDto;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceFieldService;

/**
 * 数据源字段映射调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Controller
@RequestMapping("/tdm/data_source_field")
public class DataSourceFieldController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(DataSourceFieldController.class);

	@Autowired
	private IDataSourceFieldService dataSourceFieldService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示数据源字段映射信息列表
	 * @param request 请求对象
	 * @param AeftdmDataSourceFieldDto 分页和查询对象
	 * @return 获取到的数据源字段映射对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AeftdmDataSourceFieldDto dataSourceFieldDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",dataSourceFieldDto.getPageInfo().getPageNum(),dataSourceFieldDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=dataSourceFieldService.listPageDataSourceField(dataSourceFieldDto.getPageInfo(),dataSourceFieldDto.getDataSourceField());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个数据源字段映射,以便修改
	 * @param request 请求对象
     * @param id 数据源字段映射主键ID
     * @return 数据源字段映射信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle数据源字段映射主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(dataSourceFieldService.getDataSourceFieldById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的数据源字段映射信息 
	 * @param request 请求对象
	 * @param dataSourceField 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveDataSourceField(HttpServletRequest request,@RequestBody AeftdmDataSourceField dataSourceField) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( dataSourceField.getId()!=null ) {//修改方法
		    dataSourceField.setUpdateTime(DateOperate.getCurrentUtilDate());
			dataSourceField.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(dataSourceFieldService.updateDataSourceField(dataSourceField));
		} else {//新增方法
			dataSourceField.setCreateTime(DateOperate.getCurrentUtilDate());
			dataSourceField.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(dataSourceFieldService.insertDataSourceField(dataSourceField));
		}
	}
	
	/****
	 * 删除数据源字段映射对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的数据源字段映射ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteDataSourceFieldByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(dataSourceFieldService.deleteDataSourceFieldByIds(ids));
	}
}
