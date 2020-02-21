package com.zhangysh.accumulate.ui.sys.service;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;

/*****
 * 常用功能快速访问相关调用后台方法
 * @author zhangysh
 * @date 2020年02月21日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IQuickVisitService {

	/****
	 * 保存新增的常用功能快速访问信息 
	 * @param aerfatoken token对象
	 * @param quickVisit 要保存的常用功能快速访问对象
	 ***/
	@RequestMapping(value = "/sys/quick_visit/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefsysQuickVisit quickVisit);

	/****
	 * 删除常用功能快速访问对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的常用功能快速访问ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/sys/quick_visit/delete",method = RequestMethod.POST)
	public String deleteQuickVisitByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody String ids);

}