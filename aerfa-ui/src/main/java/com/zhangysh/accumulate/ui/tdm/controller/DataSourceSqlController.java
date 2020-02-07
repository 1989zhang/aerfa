package com.zhangysh.accumulate.ui.tdm.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.pojo.tdm.viewobj.AeftdmDataSourceSqlVo;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmDataSourceSqlDto;
import com.zhangysh.accumulate.ui.tdm.service.IDataSourceSqlService;

/**
 * 数据源SQL定义调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Controller
@RequestMapping("/tdm/data_source_sql")
public class DataSourceSqlController {

    private String prefix="/tdm/data_source_sql";//返回界面路径即前缀

	@Autowired
	private IDataSourceSqlService dataSourceSqlService;

	/**
	 * 跳转到sys数据源SQL定义页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的数据源SQL定义页面
	 ****/
	@RequestMapping(value="/to_data_source_sql/{templateId}")
	public String toTdmDataSourceSql(HttpServletRequest request, ModelMap modelMap,@PathVariable("templateId") Long templateId) {
		modelMap.put("templateId", templateId);
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/data_source_sql";
	}
	
	/****
	 * 获取展示数据源SQL定义列表，且分页显示
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AeftdmDataSourceSql dataSourceSql) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AeftdmDataSourceSqlDto dataSourceSqlDto=new AeftdmDataSourceSqlDto();
		dataSourceSqlDto.setPageInfo(pageInfo);dataSourceSqlDto.setDataSourceSql(dataSourceSql);
		return dataSourceSqlService.getList(aerfatoken,dataSourceSqlDto);
	}
	
	/**
	 * 跳转到sys数据源SQL定义新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add/{templateId}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("templateId") Long templateId) {
		modelMap.put("templateId", templateId);
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
    /***
	 * 保存填写的数据源SQL定义对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param dataSourceSql 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AeftdmDataSourceSql dataSourceSql) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataSourceSqlService.saveAdd(aerfatoken, dataSourceSql);
	}
	
	/****
	 * 修改数据源SQL定义，先获取数据源SQL定义信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retDataSourceSqlStr=dataSourceSqlService.getSingle(aerfatoken, id);
		AeftdmDataSourceSqlVo dataSourceSqlVo=JSON.parseObject(retDataSourceSqlStr,AeftdmDataSourceSqlVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("dataSourceSql", dataSourceSqlVo);
		return prefix+"/edit";
	}
	
	/***
	 * 删除数据源SQL定义对象，可以删除多个，中间英文,隔开
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataSourceSqlService.deleteDataSourceSqlByIds(aerfatoken, ids);
    }
	
	/***
	 * 以树形结构展示数据源sql和字段field
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param templateId 数据源所属模板id
	 ****/
	@RequestMapping(value="/tree/{templateId}")
    @ResponseBody
    public String getTree(HttpServletRequest request, ModelMap modelMap,@PathVariable("templateId") Long templateId){
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataSourceSqlService.getTree(aerfatoken,templateId);
	}
	
}