package com.zhangysh.accumulate.back.tdm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.tdm.service.IFillRuleService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmFillRuleDataDto;


/**
 * 模板填充规则调用相关方法
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Controller
@RequestMapping("/tdm/fill_rule")
public class FillRuleController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(FillRuleController.class);

	@Autowired
	private IFillRuleService fillRuleService;
	
	/**
	 * 根据列的id获取列设置样式列表
	 ***/
	@RequestMapping(value="/setting_field_list")
    @ResponseBody
    public String getSettingFieldList(HttpServletRequest request,@RequestBody String nodeIds) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		BsTableDataInfo tableInfo = fillRuleService.getSettingFieldList(nodeIds);
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
    /***
	 * 保存填写的模板填充规则对象
	 * @param tableDataDto bootstrap表格对象json字符
	 */
	@RequestMapping(value="/save_table_data")
    @ResponseBody
    public String saveTableData(HttpServletRequest request,@RequestBody AeftdmFillRuleDataDto tableDataDto) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return toHandlerResultStr(fillRuleService.saveTableData(tableDataDto));
	}
	
   /***
	* 根据模板和位置信息删除填充规则
	* @param tableDataDto 参数对象
	*/
	@RequestMapping(value = "/delete_by_mark",method = RequestMethod.POST)
    @ResponseBody
	public String deleteFillRuleByMark(HttpServletRequest request,@RequestBody AeftdmFillRuleDataDto tableDataDto) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return toHandlerResultStr(fillRuleService.deleteFillRuleByMark(tableDataDto));
	}


	/**
	 * 根据模板ID获取里面已绑定的替换字符
	 * @param request 请求对象
	 * @param templateId 模板ID
	 */
	@RequestMapping(value = "/replace_char_arr",method = RequestMethod.POST)
	@ResponseBody
	public String getReplaceCharArr(HttpServletRequest request,@RequestBody Long templateId){
		String replaceCharStr= fillRuleService.getReplaceCharArr(templateId);
		return toHandlerResultStr(true,replaceCharStr,null,null);
	}

}
