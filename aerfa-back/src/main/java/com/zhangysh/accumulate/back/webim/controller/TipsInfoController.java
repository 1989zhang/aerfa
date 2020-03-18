package com.zhangysh.accumulate.back.webim.controller;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoInviteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

import java.util.List;

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
	 * 分页显示人员的消息提醒列表
	 * @param request 请求对象
	 * @param tipsInfoDto 查询条件
	 ***/
	@RequestMapping(value = "/msg_box",method = RequestMethod.POST)
	@ResponseBody
	public String getWebimMsgbox(HttpServletRequest request,@RequestBody AefwebimTipsInfoDto tipsInfoDto){
		return tipsInfoService.getWebimMsgbox(tipsInfoDto);
	}

	/****
	 * 处理提示消息,接收邀请信息
	 * @param request 请求对象
	 * @param tipsInfoInviteDto 操作的对象
	 ****/
	@RequestMapping(value="/accept_invite",method = RequestMethod.POST)
	@ResponseBody
	public String acceptInvite(HttpServletRequest request,@RequestBody AefwebimTipsInfoInviteDto tipsInfoInviteDto) {
		List<Object> resultList=tipsInfoService.acceptInvite(tipsInfoInviteDto);
		if(resultList.size()>0){
			return toHandlerResultStr(true,resultList,null,null);
		}
		return toHandlerResultStr(resultList.size());
	}

	/****
	 * 处理提示消息,拒绝邀请信息
	 * @param request 请求对象
	 * @param tipsInfoInviteDto 操作的对象
	 ****/
	@RequestMapping(value="/refuse_invite",method = RequestMethod.POST)
	@ResponseBody
	public String refuseInvite(HttpServletRequest request,@RequestBody AefwebimTipsInfoInviteDto tipsInfoInviteDto) {
		return toHandlerResultStr(tipsInfoService.refuseInvite(tipsInfoInviteDto));
	}


	/****
	 * 获取展示提示消息信息列表
	 * @param request 请求对象
	 * @param tipsInfoDto 分页和查询对象
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
