package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonAddressDto;


/*****
 * 个人联系地址相关调用后台方法
 * @author zhangysh
 * @date 2019年05月26日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IPersonAddressService {

	/****
	 *分页显示个人联系地址信息
	 *@param aerfatoken token对象
	 *@param PersonAddressDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/person_address/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysPersonAddressDto PersonAddressDto);

	/****
	 *获取单个个人联系地址信息
	 *@param aerfatoken token对象
	 *@param id 个人联系地址的id
	 ***/
	@RequestMapping(value = "/sys/person_address/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *保存新增的个人联系地址信息 
	 *@param aerfatoken token对象
	 *@param PersonAddress 要保存的个人联系地址对象
	 ***/
	@RequestMapping(value = "/sys/person_address/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysPersonAddress personAddress);

	/****
	 *删除个人联系地址对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的个人联系地址ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/person_address/delete",method = RequestMethod.POST)
	public String deletePersonAddressByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

	/****
	 * 个人联系地址设置为默认地址
	 * @param aerfatoken token对象
	 * @param id 要设置的id，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/person_address/default",method = RequestMethod.POST)
	public String setDefault(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

}