package com.zhangysh.accumulate.back.tdm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmDataSourceSqlDto;
import com.zhangysh.accumulate.back.tdm.exception.QueryDataException;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceSqlService;

/**
 * 数据源SQL定义调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Controller
@RequestMapping("/tdm/data_source_sql")
public class DataSourceSqlController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(DataSourceSqlController.class);

	@Autowired
	private IDataSourceSqlService dataSourceSqlService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示数据源SQL定义信息列表
	 * @param request 请求对象
	 * @param AeftdmDataSourceSqlDto 分页和查询对象
	 * @return 获取到的数据源SQL定义对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AeftdmDataSourceSqlDto dataSourceSqlDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",dataSourceSqlDto.getPageInfo().getPageNum(),dataSourceSqlDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=dataSourceSqlService.listPageDataSourceSql(dataSourceSqlDto.getPageInfo(),dataSourceSqlDto.getDataSourceSql());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个数据源SQL定义,以便修改
	 * @param request 请求对象
     * @param id 数据源SQL定义主键ID
     * @return 数据源SQL定义信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle数据源SQL定义主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(dataSourceSqlService.getDataSourceSqlById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的数据源SQL定义信息 
	 * @param request 请求对象
	 * @param dataSourceSql 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveDataSourceSql(HttpServletRequest request,@RequestBody AeftdmDataSourceSql dataSourceSql) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request); 
			AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
			if ( dataSourceSql.getId()!=null ) {//修改方法
			    dataSourceSql.setUpdateTime(DateOperate.getCurrentUtilDate());
				dataSourceSql.setUpdateBy(operPerson.getPersonName());
				return toHandlerResultStr(dataSourceSqlService.updateDataSourceSql(dataSourceSql));
			} else {//新增方法
				dataSourceSql.setCreateTime(DateOperate.getCurrentUtilDate());
				dataSourceSql.setCreateBy(operPerson.getPersonName());
				return toHandlerResultStr(dataSourceSqlService.insertDataSourceSql(dataSourceSql));
			}
		}catch(QueryDataException qde) {
			qde.printStackTrace();
			return toHandlerResultStr(false,null,null,qde.getMessage());
		}
	}
	
	/****
	 * 删除数据源SQL定义对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的数据源SQL定义ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteDataSourceSqlByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(dataSourceSqlService.deleteDataSourceSqlByIds(ids));
	}
	
	/****
	 *获取树形结构的数据源和字段
	 *@param request 请求对象
	 *@return 树形数据源对象JSON
	 ****/
	@RequestMapping(value="/tree",method = RequestMethod.POST)
    @ResponseBody
	public String getTree(HttpServletRequest request,@RequestBody Long templateId) {
		return JSON.toJSONString(dataSourceSqlService.listDataSourceWithTreeStructure(templateId));
	}
}
