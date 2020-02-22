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

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;
import com.zhangysh.accumulate.back.sys.service.IQuickVisitService;

/**
 * 常用功能快速访问调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
@Controller
@RequestMapping("/sys/quick_visit")
public class QuickVisitController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(QuickVisitController.class);

	@Autowired
	private IQuickVisitService quickVisitService;
	@Autowired
    private IRedisRelatedService redisRelatedService;


	/****
	 * 保存新增和修改的常用功能快速访问信息 
	 * @param request 请求对象
	 * @param quickVisit 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveQuickVisit(HttpServletRequest request,@RequestBody AefsysQuickVisit quickVisit) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( quickVisit.getId()!=null ) {//修改方法
		    quickVisit.setUpdateTime(DateOperate.getCurrentUtilDate());
			quickVisit.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(quickVisitService.updateQuickVisit(quickVisit));
		} else {//新增方法
			quickVisit.setCreateTime(DateOperate.getCurrentUtilDate());
			quickVisit.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(quickVisitService.insertQuickVisit(quickVisit));
		}
	}
	
	/****
	 * 删除常用功能快速访问对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的常用功能快速访问ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteQuickVisitByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(quickVisitService.deleteQuickVisitByIds(ids));
	}
}
