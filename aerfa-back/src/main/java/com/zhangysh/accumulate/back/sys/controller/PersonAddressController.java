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

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonAddressDto;
import com.zhangysh.accumulate.back.sys.service.IPersonAddressService;

/**
 * 个人联系地址调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
@Controller
@RequestMapping("/sys/person_address")
public class PersonAddressController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(PersonAddressController.class);

	@Autowired
	private IPersonAddressService personAddressService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示个人联系地址信息列表
	 * @param request 请求对象
	 * @param AefsysPersonAddressDto 分页和查询对象
	 * @return 获取到的个人联系地址对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysPersonAddressDto personAddressDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",personAddressDto.getPageInfo().getPageNum(),personAddressDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=personAddressService.listPagePersonAddress(personAddressDto.getPageInfo(),personAddressDto.getPersonAddress());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个个人联系地址,以便修改
	 * @param request 请求对象
     * @param id 个人联系地址主键ID
     * @return 个人联系地址信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle个人联系地址主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(personAddressService.getPersonAddressById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 *保存新增和修改的个人联系地址信息 
	 *@param request 请求对象
	 *@param personAddress 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String savePersonAddress(HttpServletRequest request,@RequestBody AefsysPersonAddress personAddress) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( personAddress.getId()!=null ) {//修改方法
		    personAddress.setUpdateTime(DateOperate.getCurrentUtilDate());
			personAddress.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(personAddressService.updatePersonAddress(personAddress));
		} else {//新增方法
			personAddress.setCreateTime(DateOperate.getCurrentUtilDate());
			personAddress.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(personAddressService.insertPersonAddress(personAddress));
		}
	}
	
	/****
	 *删除个人联系地址对象，可以删除多个.
	 *@param request 请求对象
	 *@param ids 要删除的个人联系地址ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deletePersonAddressByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(personAddressService.deletePersonAddressByIds(ids));
	}
	
	/***
	 * 个人联系地址设置为默认地址
	 * @param aerfatoken token对象
	 * @param id 要设置的id参数
	 ****/
	@RequestMapping(value = "/default",method = RequestMethod.POST)
	@ResponseBody
	public String setDefault(HttpServletRequest request,@RequestBody Long id) {
		return toHandlerResultStr(personAddressService.setDefault(id));
	}
}
