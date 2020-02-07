package com.zhangysh.accumulate.back.sys.controller;

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

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysConfigDataDto;
import com.zhangysh.accumulate.back.sys.service.IConfigDataService;

/**
 * 配置调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月24日
 */
@Controller
@RequestMapping("/sys/config_data")
public class ConfigDataController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(ConfigDataController.class);

	@Autowired
	private IConfigDataService configDataService;
	@Autowired
    private IRedisRelatedService redisRelatedService;

	/****
	 * 获取展示配置信息列表
	 * @param request 请求对象
	 * @param AefsysConfigDataDto 分页和查询对象
	 * @return 获取到的配置对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysConfigDataDto configDataDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",configDataDto.getPageInfo().getPageNum(),configDataDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=configDataService.listPageConfigData(configDataDto.getPageInfo(),configDataDto.getConfigData());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/**
	 * 获取所有配置信息，如系统名称等，因为没登录就不要token信息
	 *****/
	@RequestMapping(value="/all",method = RequestMethod.POST)
	@ResponseBody
	public String getAll() {
		return JSON.toJSONStringWithDateFormat(configDataService.getAllConfigDataFromRedis(),UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个配置,以便修改
	 * @param request 请求对象
     * @param id 配置主键ID
     * @return 配置信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle配置主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(configDataService.getConfigDataById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 验证配置参数编码唯一性
	 * @param request 请求对象
	 * @param configData 要验证的对象内含参数编码属性和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/check_data_code_unique",method = RequestMethod.POST)
	@ResponseBody
	public String checkDataCodeUnique(HttpServletRequest request,@RequestBody AefsysConfigData configData) {
		return toUniqueResultStr(configDataService.checkDataCodeUnique(configData).size());
	}
	/****
	 *保存新增和修改的配置信息 
	 *@param request 请求对象
	 *@param configData 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveConfigData(HttpServletRequest request,@RequestBody AefsysConfigData configData) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( configData.getId()!=null ) {//修改方法
		    configData.setUpdateTime(DateOperate.getCurrentUtilDate());
			configData.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(configDataService.updateConfigData(configData));
		} else {//新增方法
			configData.setCreateTime(DateOperate.getCurrentUtilDate());
			configData.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(configDataService.insertConfigData(configData));
		}
	}
	
	/****
	 *删除配置对象，可以删除多个.
	 *@param request 请求对象
	 *@param ids 要删除的配置ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteConfigDataByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(configDataService.deleteConfigDataByIds(ids));
	}
}
