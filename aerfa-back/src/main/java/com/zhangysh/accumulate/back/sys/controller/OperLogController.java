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
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysOperLogDto;
import com.zhangysh.accumulate.back.sys.service.IOperLogService;

/**
 * 操作日志调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月20日
 */
@Controller
@RequestMapping("/sys/oper_log")
public class OperLogController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(OperLogController.class);

	@Autowired
	private IOperLogService operLogService;
    
	/****
	 * 获取展示操作日志信息列表
	 * @param request 请求对象
	 * @param AefsysOperLogDto 分页和查询对象
	 * @return 获取到的操作日志对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysOperLogDto operLogDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",operLogDto.getPageInfo().getPageNum(),operLogDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=operLogService.listPageOperLog(operLogDto.getPageInfo(),operLogDto.getOperLog());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.MOST_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个操作日志,以便修改
	 * @param request 请求对象
     * @param id 操作日志主键ID
     * @return 操作日志信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle操作日志主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(operLogService.getOperLogById(id),UtilConstant.MOST_MIDDLE_DATE);
	}
	
	/****
	 *删除操作日志对象，可以删除多个.
	 *@param request 请求对象
	 *@param ids 要删除的操作日志ids,中间英文,隔开
	 ***/
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteOperLogByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(operLogService.deleteOperLogByIds(ids));
	}
}
