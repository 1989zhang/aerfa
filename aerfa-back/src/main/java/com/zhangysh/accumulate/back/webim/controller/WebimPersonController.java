package com.zhangysh.accumulate.back.webim.controller;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.webim.service.IWebimPersonService;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimPersonVo;

/*****
 * 类作用说明
 * @author zhangysh
 * @date 2019年11月24日
 *****/
@Controller
@RequestMapping("/webim/person")
public class WebimPersonController extends BaseController{

	@Autowired
	private IRedisRelatedService redisRelatedService;
	@Autowired
	private IWebimPersonService webimPersonService;
	
	/***
	 * 获取个人拓展信息
	 * @param request 请求对象
	 * @param searchDto 查询条件
	 * @return 个人拓展详细信息
	 *****/
	@RequestMapping(value="/get_information",method=RequestMethod.POST)
	@ResponseBody
	public String getInformation(HttpServletRequest request,@RequestBody AefwebimSearchDto searchDto) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request); 
			AefwebimPersonVo webimPersonVo=webimPersonService.getInformation(searchDto);
			return toHandlerResultStr(webimPersonVo, UtilConstant.NORMAL_MIDDLE_DATE,false);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR, e.getMessage());
		}
	}

	/***
	 * 保存修改的我的个人信息,包括webim人员信息和sys人员信息
	 * @param request 请求对象
	 * @param webimPersonVo 保存的我的个人信息
	 *****/
	@RequestMapping(value="/save_my_information",method=RequestMethod.POST)
	@ResponseBody
	public String saveMyInformation(HttpServletRequest request,@RequestBody AefwebimPersonVo webimPersonVo) {
		try {
			String aerfatoken=HttpStorageUtil.getToken(request);
			AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
			boolean retTar=webimPersonService.saveMyInformation(operPerson,webimPersonVo);
			return toHandlerResultStr(retTar);
		}catch(Exception e) {
			e.printStackTrace();
			return toHandlerResultStr(false, null, CodeMsgConstant.SYS_DATA_HANDLER_ERROR, e.getMessage());
		}
	}
}
