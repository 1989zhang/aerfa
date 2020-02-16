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

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommInfoPublishDto;
import com.zhangysh.accumulate.back.comm.service.IInfoPublishService;

/**
 * 发布调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Controller
@RequestMapping("/sys/info_publish")
public class InfoPublishController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(InfoPublishController.class);

	@Autowired
	private IInfoPublishService infoPublishService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示发布信息列表
	 * @param request 请求对象
	 * @param infoPublishDto 分页和查询对象
	 * @return 获取到的发布对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefcommInfoPublishDto infoPublishDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",infoPublishDto.getPageInfo().getPageNum(),infoPublishDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=infoPublishService.listPageInfoPublish(infoPublishDto.getPageInfo(),infoPublishDto.getInfoPublish());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个发布,以便修改
	 * @param request 请求对象
     * @param id 发布主键ID
     * @return 发布信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle发布主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(infoPublishService.getInfoPublishById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的发布信息 
	 * @param request 请求对象
	 * @param infoPublish 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveInfoPublish(HttpServletRequest request,@RequestBody AefcommInfoPublish infoPublish) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( infoPublish.getId()!=null ) {//修改方法
		    infoPublish.setUpdateTime(DateOperate.getCurrentUtilDate());
			infoPublish.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(infoPublishService.updateInfoPublish(infoPublish));
		} else {//新增方法
			infoPublish.setCreateTime(DateOperate.getCurrentUtilDate());
			infoPublish.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(infoPublishService.insertInfoPublish(infoPublish));
		}
	}
	
	/****
	 * 删除发布对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的发布ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteInfoPublishByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(infoPublishService.deleteInfoPublishByIds(ids));
	}
}
