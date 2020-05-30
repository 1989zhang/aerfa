package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysConfigDataDto;


/*****
 * 配置相关调用后台方法
 * @author zhangysh
 * @date 2019年05月24日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IConfigDataService {

	/****
	 *分页显示配置信息
	 *@param aerfatoken token对象
	 *@param ConfigDataDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/config_data/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysConfigDataDto ConfigDataDto);

	/****
	 * 获取所有配置信息，如系统名称等，因为没登录就不要token信息
	 ***/
	@RequestMapping(value = "/sys/config_data/all",method = RequestMethod.POST)
	public String getAll();
	
	/****
	 *获取单个配置信息
	 *@param aerfatoken token对象
	 *@param id 配置的id
	 ***/
	@RequestMapping(value = "/sys/config_data/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *根据配置文件的code获取单个配置信息
	 *@param aerfatoken token对象
	 *@param dataCode 配置的dataCode
	 ***/
	@RequestMapping(value = "/sys/config_data/by_code",method = RequestMethod.POST)
	public String getSingleByCode(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String dataCode);

	/****
	 *验证配置参数编码唯一性 
	 *@param aerfatoken token对象
	 *@param configData 要验证的对象内含参数编码属性和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/sys/config_data/check_data_code_unique",method = RequestMethod.POST)
	public String checkDataCodeUnique(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysConfigData configData);
	/****
	 *保存新增的配置信息 
	 *@param aerfatoken token对象
	 *@param configData 要保存的配置对象
	 ***/
	@RequestMapping(value = "/sys/config_data/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysConfigData configData);

	/****
	 *删除配置对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的配置ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/config_data/delete",method = RequestMethod.POST)
	public String deleteConfigDataByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

}