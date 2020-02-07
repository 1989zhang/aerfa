package com.zhangysh.accumulate.ui.tdm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmFillRuleDataDto;

/*****
 * 模板填充规则相关调用后台方法
 * @author zhangysh
 * @date 2019年06月19日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IFillRuleService {
	
	/****
	 * 根据数据源字段主键ids查询数据源字段映射信息列表
	 * @param ids 数据源字段id
	 ***/
	@RequestMapping(value = "/tdm/fill_rule/setting_field_list",method = RequestMethod.POST)
	public String getSettingFieldList(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

    /***
	 * 保存填写的模板填充规则对象
	 * @param tableData bootstrap表格对象json字符
	 */
	@RequestMapping(value = "/tdm/fill_rule/save_table_data",method = RequestMethod.POST)
	public String saveTableData(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AeftdmFillRuleDataDto tableDataDto);
	
   /***
	* 根据模板和位置信息删除填充规则
	* @param tableDataDto 参数对象
	*/
	@RequestMapping(value = "/tdm/fill_rule/delete_by_mark",method = RequestMethod.POST)
	public String deleteFillRuleByMark(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AeftdmFillRuleDataDto tableDataDto);

}