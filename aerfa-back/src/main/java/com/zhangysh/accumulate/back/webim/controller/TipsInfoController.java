package com.zhangysh.accumulate.back.webim.controller;

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

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoDto;
import com.zhangysh.accumulate.back.webim.service.ITipsInfoService;

/**
 * 提示消息调用相关方法
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
@Controller
@RequestMapping("/webim/tips_info")
public class TipsInfoController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(TipsInfoController.class);

	@Autowired
	private ITipsInfoService tipsInfoService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示提示消息信息列表
	 * @param request 请求对象
	 * @param AefwebimTipsInfoDto 分页和查询对象
	 * @return 获取到的提示消息对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefwebimTipsInfoDto tipsInfoDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",tipsInfoDto.getPageInfo().getPageNum(),tipsInfoDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=tipsInfoService.listPageTipsInfo(tipsInfoDto.getPageInfo(),tipsInfoDto.getTipsInfo());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个提示消息,以便修改
	 * @param request 请求对象
     * @param id 提示消息主键ID
     * @return 提示消息信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle提示消息主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(tipsInfoService.getTipsInfoById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的提示消息信息 
	 * @param request 请求对象
	 * @param tipsInfo 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveTipsInfo(HttpServletRequest request,@RequestBody AefwebimTipsInfo tipsInfo) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( tipsInfo.getId()!=null ) {//修改方法
		    tipsInfo.setUpdateTime(DateOperate.getCurrentUtilDate());
			tipsInfo.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(tipsInfoService.updateTipsInfo(tipsInfo));
		} else {//新增方法
			tipsInfo.setCreateTime(DateOperate.getCurrentUtilDate());
			tipsInfo.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(tipsInfoService.insertTipsInfo(tipsInfo));
		}
	}
	
	/****
	 * 删除提示消息对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的提示消息ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteTipsInfoByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(tipsInfoService.deleteTipsInfoByIds(ids));
	}
}
