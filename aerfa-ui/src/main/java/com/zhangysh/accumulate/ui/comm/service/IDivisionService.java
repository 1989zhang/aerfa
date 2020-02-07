package com.zhangysh.accumulate.ui.comm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommDivisionDto;


/*****
 * 行政区划相关调用后台方法
 * @author zhangysh
 * @date 2019年05月25日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IDivisionService {

	/****
	 *分页显示行政区划信息
	 *@param aerfatoken token对象
	 *@param DivisionDto 查询条件
	 ***/
	@RequestMapping(value = "/comm/division/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefcommDivisionDto DivisionDto);

	/****
	 *获取单个行政区划信息
	 *@param aerfatoken token对象
	 *@param id 行政区划的id
	 ***/
	@RequestMapping(value = "/comm/division/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *验证行政区划区划代码唯一性 
	 *@param aerfatoken token对象
	 *@param division 要验证的对象内含区划代码属性和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/comm/division/check_code_unique",method = RequestMethod.POST)
	public String checkCodeUnique(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefcommDivision division);
	/****
	 *保存新增的行政区划信息 
	 *@param aerfatoken token对象
	 *@param Division 要保存的行政区划对象
	 ***/
	@RequestMapping(value = "/comm/division/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefcommDivision division);

	/****
	 *删除行政区划对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的行政区划ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/comm/division/delete",method = RequestMethod.POST)
	public String deleteDivisionByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

	/***
	 * 展示行政区划的树形结构
	 * @param aerfatoken token对象
	 ***/
	@RequestMapping(value = "/comm/division/tree",method = RequestMethod.POST)
	public String getTree(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long parentId);

	/***
	 * 获取city-picker后台json数据
	 * @param aerfatoken token对象
	 ***/
	@RequestMapping(value = "/comm/division/picker_data",method = RequestMethod.POST)
	public String getCityPickerData(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken);

}