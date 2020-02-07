package com.zhangysh.accumulate.ui.tdm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmDataSourceSqlDto;


/*****
 * 数据源SQL定义相关调用后台方法
 * @author zhangysh
 * @date 2019年06月19日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IDataSourceSqlService {

	/****
	 * 分页显示数据源SQL定义信息
	 * @param aerfatoken token对象
	 * @param DataSourceSqlDto 查询条件
	 ***/
	@RequestMapping(value = "/tdm/data_source_sql/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AeftdmDataSourceSqlDto DataSourceSqlDto);

	/****
	 * 获取单个数据源SQL定义信息
	 * @param aerfatoken token对象
	 * @param id 数据源SQL定义的id
	 ***/
	@RequestMapping(value = "/tdm/data_source_sql/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 * 保存新增的数据源SQL定义信息 
	 * @param aerfatoken token对象
	 * @param DataSourceSql 要保存的数据源SQL定义对象
	 ***/
	@RequestMapping(value = "/tdm/data_source_sql/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AeftdmDataSourceSql dataSourceSql);

	/****
	 * 删除数据源SQL定义对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的数据源SQL定义ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/tdm/data_source_sql/delete",method = RequestMethod.POST)
	public String deleteDataSourceSqlByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

	/****
	 * 以树形结构展示数据源sql和字段field
	 * @param aerfatoken token对象
	 * @param templateId 数据源所属模板id
	 ***/
	@RequestMapping(value = "/tdm/data_source_sql/tree",method = RequestMethod.POST)
	public String getTree(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long templateId);

}