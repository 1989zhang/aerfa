package com.zhangysh.accumulate.back.sys.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.service.IPersonService;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonDto;

/*****
 * 人员相关方法
 * @author zhangysh
 * @date 2018年9月12日
 *****/
@Controller
@RequestMapping("/sys/person")
public class PersonController extends BaseController{
	
	private static final Logger logger=LoggerFactory.getLogger(PersonController.class);
	
	
	@Autowired
	private IPersonService personService;
    @Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 *获取展示人员信息列表，默认加载所有，点击单位后加载单位下的人员 
	 *@param request 请求对象
	 *@param personDto 分页和查询对象
	 *@return 获取到的个人对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
    @ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysPersonDto personDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",personDto.getPageInfo().getPageNum(),personDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=personService.listPagePerson(personDto.getPageInfo(),personDto.getPerson());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 *获取展示单个人员信息，包含单位名称
	 *@param request 请求对象
	 *@param personId 人员的id
	 *@return 获取到的个人对象含单位名称
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long personId) {
		logger.info("getSingle人员id信息:{}",personId);
		return JSON.toJSONStringWithDateFormat(personService.getPersonWithOrgNameById(personId),UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 *验证单位全称是否唯一
	 *@param request 请求对象
	 *@param person 要检查的个人包括：名称和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/check_account_unique",method = RequestMethod.POST)
	@ResponseBody
	public String checkAccountUnique(HttpServletRequest request,@RequestBody AefsysPerson person) {
		return toUniqueResultStr(personService.checkAccountUnique(person).size());
	}
	
	/****
	 *保存新增的个人和修改个人信息 
	 *@param request 请求对象
	 *@param person 新增对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String savePerson(HttpServletRequest request,@RequestBody AefsysPerson person) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( person.getId()!=null ) {//修改方法
			person.setUpdateTime(DateOperate.getCurrentUtilDate());
			person.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(personService.updatePerson(person));
		} else {//新增方法
			person.setCreateTime(DateOperate.getCurrentUtilDate());
			person.setCreateBy(operPerson.getPersonName());
			person.setNickName(StringUtil.isEmpty(person.getNickName())?person.getPersonName():person.getNickName());
			return toHandlerResultStr(personService.insertPerson(person));
		}
	}
	
	/****
	 *删除个人对象，可以删除多个.
	 *@param request 请求对象
	 *@param ids 要删除的个人ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="系统管理",menu="人员管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deletePersonByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(personService.deletePersonByIds(ids));
	}
	
	/****
	 *修改本人密码时，先验证当前密码是否正确。
	 *验证逻辑：查找账号密码在数据库有没有这个人来判断，不是人找出来比对密码。
	 *@param request 请求对象
	 *@param oldPassword 要验证的当前个人旧密码
	 ***/
	@RequestMapping(value = "/check_old_password",method = RequestMethod.POST)
	@ResponseBody
	public String checkOldPassword(HttpServletRequest request,@RequestBody String oldPassword) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		boolean checkRet=personService.checkOldPassword(operPerson.getAccount(),oldPassword);
		return toHandlerResultStr(checkRet,null,CodeMsgConstant.SYS_DATA_VALIDATE_ERROR,null);
	}
}
