package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonDto;

/*****
 * 人员相关调用后台服务：注意@FeignClient一般不指定url，但是我遇到linux调用windows调不到服务，必须知道url的情况：
 * 就要,加url="${feign.message.url}例如：http://59.195.7.175:80/message/
 * @author zhangysh
 * @date 2018年9月15日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IPersonService {
	/****
	 *获取人员list
	 *@param aerfatoken token对象
	 *@param personDto 查询条件和分页对象
	 ***/
	@RequestMapping(value = "/sys/person/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysPersonDto personDto);
	
	/****
	 *获取单个人员信息，且包含单位名称 
	 *@param aerfatoken token对象
	 *@param personId 人员id
	 ***/
	@RequestMapping(value = "/sys/person/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long personId);
	
	/****
	 *验证人员账号是否唯一
	 *@param aerfatoken token对象
	 *@param person 要检查的人员包括：名称，账号创建后不能修改
	 ***/
	@RequestMapping(value = "/sys/person/check_account_unique",method = RequestMethod.POST)
	public String checkAccountUnique(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysPerson person);

	/****
	 *保存新增的个人信息 
	 *@param aerfatoken token对象
	 *@param person 保存的对象
	 ***/
	@RequestMapping(value = "/sys/person/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysPerson person);

	/****
	 *删除个人对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的个人ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/person/delete",method = RequestMethod.POST)
	public String deletePersonByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

	/****
	 *修改本人密码时，先验证当前密码是否正确
	 *@param aerfatoken token对象
	 *@param oldPassword 要验证的当前个人旧密码
	 ***/
	@RequestMapping(value = "/sys/person/check_old_password",method = RequestMethod.POST)
	public String checkOldPassword(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String oldPassword);

	
}
