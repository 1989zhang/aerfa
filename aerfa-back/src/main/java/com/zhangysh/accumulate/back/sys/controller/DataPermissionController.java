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

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysDataPermissionDto;
import com.zhangysh.accumulate.back.sys.service.IDataPermissionService;

/**
 * 数据权限调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Controller
@RequestMapping("/sys/data_permission")
public class DataPermissionController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(DataPermissionController.class);

	@Autowired
	private IDataPermissionService dataPermissionService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示数据权限信息列表
	 * @param request 请求对象
	 * @param dataPermissionDto 分页和查询对象
	 * @return 获取到的数据权限对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysDataPermissionDto dataPermissionDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",dataPermissionDto.getPageInfo().getPageNum(),dataPermissionDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=dataPermissionService.listPageDataPermission(dataPermissionDto.getPageInfo(),dataPermissionDto.getDataPermission());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个数据权限,以便修改
	 * @param request 请求对象
     * @param id 数据权限主键ID
     * @return 数据权限信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle数据权限主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(dataPermissionService.getDataPermissionById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的数据权限信息 
	 * @param request 请求对象
	 * @param dataPermission 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveDataPermission(HttpServletRequest request,@RequestBody AefsysDataPermission dataPermission) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( dataPermission.getId()!=null ) {//修改方法
		    dataPermission.setUpdateTime(DateOperate.getCurrentUtilDate());
			dataPermission.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(dataPermissionService.updateDataPermission(dataPermission));
		} else {//新增方法
			dataPermission.setCreateTime(DateOperate.getCurrentUtilDate());
			dataPermission.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(dataPermissionService.insertDataPermission(dataPermission));
		}
	}
	
	/****
	 * 删除数据权限对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的数据权限ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteDataPermissionByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(dataPermissionService.deleteDataPermissionByIds(ids));
	}
}
