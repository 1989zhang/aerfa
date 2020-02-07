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
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonLoginInfo;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonLoginInfoDto;
import com.zhangysh.accumulate.back.sys.service.IPersonLoginInfoService;

/**
 * 个人登录调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月20日
 */
@Controller
@RequestMapping("/sys/person_login_info")
public class PersonLoginInfoController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(PersonLoginInfoController.class);

	@Autowired
	private IPersonLoginInfoService personLoginInfoService;
    
	/****
	 * 获取展示个人登录信息列表
	 * @param request 请求对象
	 * @param AefsysPersonLoginInfoDto 分页和查询对象
	 * @return 获取到的个人登录对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysPersonLoginInfoDto personLoginInfoDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",personLoginInfoDto.getPageInfo().getPageNum(),personLoginInfoDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=personLoginInfoService.listPagePersonLoginInfo(personLoginInfoDto.getPageInfo(),personLoginInfoDto.getPersonLoginInfo());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.MOST_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个个人登录,以便修改
	 * @param request 请求对象
     * @param id 个人登录主键ID
     * @return 个人登录信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle个人登录主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(personLoginInfoService.getPersonLoginInfoById(id),UtilConstant.MOST_MIDDLE_DATE);
	}
}
