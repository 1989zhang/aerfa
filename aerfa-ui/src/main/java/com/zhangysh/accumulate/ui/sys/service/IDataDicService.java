package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataDic;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysDataDicDto;


/*****
 * 数据字典相关调用后台方法
 * @author zhangysh
 * @date 2019年04月15日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IDataDicService {

	/****
	 *分页显示数据字典信息
	 *@param aerfatoken token对象
	 *@param DataDicDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/data_dic/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysDataDicDto DataDicDto);

	/****
	 *获取所有数据字典类型，不分页，所以不能用list，下拉框要用，因为是service要用所以没有http
	 *@param aerfatoken token对象
	 *@param id 数据字典的id
	 ***/
	@RequestMapping(value = "/sys/data_dic/type",method = RequestMethod.POST)
	public String getAllType();

	/****
	 *根据字典类型，不分页获取数据字典信息列表。要存redis所以单独列出来
	 *@param aerfatoken token对象
	 *@param DataDicDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/data_dic/data",method = RequestMethod.POST)
	public String getDataByType(@RequestBody String type);

	/****
	 *获取单个数据字典信息
	 *@param aerfatoken token对象
	 *@param id 数据字典的id
	 ***/
	@RequestMapping(value = "/sys/data_dic/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 *检查字典类型编码的唯一性
	 *@param aerfatoken token对象
	 *@param DataDic 要检查的数据字典包括：typeCode和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/sys/data_dic/check_data_dic_unique",method = RequestMethod.POST)
	public String checkDataDicUnique(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysDataDic dataDic);

	/****
	 *保存新增的数据字典信息 
	 *@param aerfatoken token对象
	 *@param DataDic 要保存的数据字典对象
	 ***/
	@RequestMapping(value = "/sys/data_dic/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefsysDataDic dataDic);

	/****
	 *删除数据字典对象，可以删除多个，中间英文,隔开
	 *@param aerfatoken token对象
	 *@param ids 要删除的数据字典ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/data_dic/delete",method = RequestMethod.POST)
	public String deleteDataDicByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

}