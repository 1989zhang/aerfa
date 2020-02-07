package com.zhangysh.accumulate.ui.comm.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;
import com.zhangysh.accumulate.pojo.comm.viewobj.AefcommWorkDayVo;
import com.zhangysh.accumulate.ui.comm.service.IWorkDayService;

/**
 *@author 创建者：zhangysh
 */
@Controller
@RequestMapping("/comm/work_day")
public class WorkDayController {

	private String prefix="/comm/work_day";//返回界面路径即前缀

	@Autowired
	private IWorkDayService workDayService;
	
	/**
	 * 跳转到工作日页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的工作日页面
	 ****/
	@RequestMapping(value="/to_work_day")
	public String toWorkDay(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/work_day";
	}
	
	/***
	 * 初始化年所在的月份和日期
	 * @param year 年份 
	 ***/
	@RequestMapping(value="/render_date")
    @ResponseBody
	public String getRenderDateByYear(HttpServletRequest request, ModelMap modelMap,int year) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return workDayService.getRenderDateByYear(aerfatoken, year);
	}
	
	/***
	 * 点击改变日期的上班不上班状态
	 * @param dateStr 日期
	 ***/
	@RequestMapping(value="/change_status")
    @ResponseBody
	public String changeWorkDayStatus(HttpServletRequest request, ModelMap modelMap,String dateStr) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return workDayService.changeWorkDayStatus(aerfatoken, dateStr);
	}
	
	/**
	 * 跳转到工作时间管理
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的工作日页面
	 ****/
	@RequestMapping(value="/to_work_time")
	public String toWorkTime(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retWorkDayStr=workDayService.getSingle(aerfatoken,SysDefineConstant.DB_WORK_DAY_TIME_DEFINE_ID);
		AefcommWorkDayVo workTime = JSON.parseObject(retWorkDayStr,AefcommWorkDayVo.class);
		String startDate =workTime.getWorkDate().split(MarkConstant.MARK_SPLIT_EN_TILDE)[0];
		String endDate =workTime.getWorkDate().split(MarkConstant.MARK_SPLIT_EN_TILDE)[1];
		workTime.setStartDate(startDate);
		workTime.setEndDate(endDate);
		modelMap.put("prefix",prefix);
		modelMap.put("workTime",workTime);
		return prefix+"/work_time";
	}
	
	/***
	 * 保存工作时间的定义
	 ***/
	@RequestMapping(value="/save_work_time")
    @ResponseBody
	public String saveWorkTime(HttpServletRequest request, ModelMap modelMap,AefcommWorkDayVo workDayVo) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefcommWorkDay workDay=new AefcommWorkDay();
		BeanUtils.copyProperties(workDayVo, workDay);
		workDay.setWorkDate(workDayVo.getStartDate()+MarkConstant.MARK_SPLIT_EN_TILDE+workDayVo.getEndDate());
		return workDayService.saveWorkTime(aerfatoken, workDay);
	}
	
}
