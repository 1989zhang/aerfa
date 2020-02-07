package com.zhangysh.accumulate.back.comm.controller;

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

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommDivisionDto;
import com.zhangysh.accumulate.back.comm.service.IDivisionService;

/**
 * 行政区划调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月25日
 */
@Controller
@RequestMapping("/comm/division")
public class DivisionController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(DivisionController.class);

	@Autowired
	private IDivisionService divisionService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示行政区划信息列表
	 * @param request 请求对象
	 * @param AefcommDivisionDto 分页和查询对象
	 * @return 获取到的行政区划对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefcommDivisionDto divisionDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",divisionDto.getPageInfo().getPageNum(),divisionDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=divisionService.listPageDivision(divisionDto.getPageInfo(),divisionDto.getDivision());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个行政区划,以便修改
	 * @param request 请求对象
     * @param id 行政区划主键ID
     * @return 行政区划信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle行政区划主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(divisionService.getDivisionById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 验证行政区划区划代码唯一性
	 * @param request 请求对象
	 * @param division 要验证的对象内含区划代码属性和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/check_code_unique",method = RequestMethod.POST)
	@ResponseBody
	public String checkCodeUnique(HttpServletRequest request,@RequestBody AefcommDivision division) {
		return toUniqueResultStr(divisionService.checkCodeUnique(division).size());
	}
	/****
	 *保存新增和修改的行政区划信息 
	 *@param request 请求对象
	 *@param division 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveDivision(HttpServletRequest request,@RequestBody AefcommDivision division) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( division.getId()!=null ) {//修改方法
		    division.setUpdateTime(DateOperate.getCurrentUtilDate());
			division.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(divisionService.updateDivision(division));
		} else {//新增方法
			division.setCreateTime(DateOperate.getCurrentUtilDate());
			division.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(divisionService.insertDivision(division));
		}
	}
	
	/****
	 *删除行政区划对象，可以删除多个.
	 *@param request 请求对象
	 *@param ids 要删除的行政区划ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteDivisionByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(divisionService.deleteDivisionByIds(ids));
	}
	
	/****
	 * 异步加载展示行政区划的树形结构
	 * @param request 请求对象
	 * @return 获取到的行政区划树JSON对象
	 ****/
	@RequestMapping(value="/tree",method = RequestMethod.POST)
	@ResponseBody
	public String getTree(HttpServletRequest request,@RequestBody Long parentId) {
		return JSON.toJSONString(divisionService.listDivisionWithTreeStructure(parentId));
	}
	
	/***
	 * 获取city-picker后台json数据
	 * @param request 请求对象
	 ***/
	@RequestMapping(value="/picker_data",method = RequestMethod.POST)
	@ResponseBody
	public String getCityPickerData(HttpServletRequest request) {
		return divisionService.getCityPickerDivisionDataAloneFromRedis();
	}
	
}
