package com.zhangysh.accumulate.ui.comm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommInfoPublishDto;


/*****
 * 发布相关调用后台方法
 * @author zhangysh
 * @date 2020年02月16日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IInfoPublishService {

	/****
	 * 分页显示发布信息
	 * @param aerfatoken token对象
	 * @param InfoPublishDto 查询条件
	 ***/
	@RequestMapping(value = "/sys/info_publish/list",method = RequestMethod.POST)
	public String getList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefcommInfoPublishDto InfoPublishDto);

	/****
	 * 获取单个发布信息
	 * @param aerfatoken token对象
	 * @param id 发布的id
	 ***/
	@RequestMapping(value = "/sys/info_publish/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody Long id);

	/****
	 * 保存新增的发布信息 
	 * @param aerfatoken token对象
	 * @param infoPublish 要保存的发布对象
	 ***/
	@RequestMapping(value = "/sys/info_publish/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefcommInfoPublish infoPublish);

	/****
	 * 删除发布对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的发布ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/info_publish/delete",method = RequestMethod.POST)
	public String deleteInfoPublishByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody String ids);

}