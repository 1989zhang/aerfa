package com.zhangysh.accumulate.ui.sys.controller;

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
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysConfigDataDto;
import com.zhangysh.accumulate.ui.sys.service.IConfigDataService;

/**
 * 配置调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月24日
 */
@Controller
@RequestMapping("/sys/config_data")
public class ConfigDataController {

    private String prefix="/sys/config_data";//返回界面路径即前缀

	@Autowired
	private IConfigDataService configDataService;
	
	/**
	 *跳转到sys配置页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的配置页面
	 ****/
	@RequestMapping(value="/to_config_data")
	public String toSysConfigData(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/config_data";
	}
	
	/****
	 *获取展示配置列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysConfigData configData) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysConfigDataDto configDataDto=new AefsysConfigDataDto();
		configDataDto.setPageInfo(pageInfo);configDataDto.setConfigData(configData);
		return configDataService.getList(aerfatoken,configDataDto);
	}
	
	/**
	 *跳转到sys配置新增页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add")
	public String toAdd(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add";
	}
	
	/*****
	 * 验证配置参数编码唯一性 
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param configData 要验证的对象内含参数编码属性和id,id为了排除自己
	 ****/
	@RequestMapping(value="/check_data_code_unique")
    @ResponseBody
	public String checkDataCodeUnique(HttpServletRequest request, ModelMap modelMap,AefsysConfigData configData) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return configDataService.checkDataCodeUnique(aerfatoken,configData);
	}
    /***
	 *保存填写的配置对象
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param configData 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysConfigData configData) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return configDataService.saveAdd(aerfatoken, configData);
	}
	
	/****
	 *修改配置，先获取配置信息
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retConfigDataStr=configDataService.getSingle(aerfatoken, id);
		AefsysConfigData configData=JSON.parseObject(retConfigDataStr,AefsysConfigData.class);
		modelMap.put("prefix", prefix);
		modelMap.put("configData", configData);
		return prefix+"/edit";
	}
	
	/***
	 *删除配置对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return configDataService.deleteConfigDataByIds(aerfatoken, ids);
    }
}