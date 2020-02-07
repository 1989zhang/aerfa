package com.zhangysh.accumulate.back.comm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommNoteCalendar;
import com.zhangysh.accumulate.pojo.comm.viewobj.NoteCalendarEventsVo;
import com.zhangysh.accumulate.back.comm.service.INoteCalendarService;

/**
 * 日历记事本调用相关方法
 * 
 * @author zhangysh
 * @date 2019年10月12日
 */
@Controller
@RequestMapping("/comm/note_calendar")
public class NoteCalendarController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(NoteCalendarController.class);

	@Autowired
	private INoteCalendarService noteCalendarService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
	
	/****
	 * 获取个人某时间段内的记事本信息并events对象返回
	 * @param request 请求对象
	 * @return 获取到的日历记事本对象集合JSON
	 ****/
	@RequestMapping(value="/list_around",method = RequestMethod.POST)
	@ResponseBody
	public String getListAround(HttpServletRequest request,@RequestBody NoteCalendarEventsVo eventVo) {
		AefcommNoteCalendar searchNoteCalendar=new AefcommNoteCalendar();
		searchNoteCalendar.setPersonId(eventVo.getPersonId());
		searchNoteCalendar.setStartDate(eventVo.getStart());
		searchNoteCalendar.setEndDate(eventVo.getEnd());
		List<AefcommNoteCalendar> noteCalendarList=noteCalendarService.listNoteCalendarAround(searchNoteCalendar);
		//转化为前台events对象
		List<NoteCalendarEventsVo> eventList=new ArrayList<NoteCalendarEventsVo>();
		for(int i=0;i<noteCalendarList.size();i++) {
			NoteCalendarEventsVo event=new NoteCalendarEventsVo();
			event.setId(noteCalendarList.get(i).getId());
			event.setTitle(noteCalendarList.get(i).getMark());
			event.setStart(noteCalendarList.get(i).getStartDate());
			event.setEnd(noteCalendarList.get(i).getEndDate());
			eventList.add(event);
		}
		return JSON.toJSONStringWithDateFormat(eventList,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个日历记事本,以便修改
	 * @param request 请求对象
     * @param id 日历记事本主键ID
     * @return 日历记事本信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle日历记事本主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(noteCalendarService.getNoteCalendarById(id),UtilConstant.NORMAL_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的日历记事本信息 
	 * @param request 请求对象
	 * @param noteCalendar 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveNoteCalendar(HttpServletRequest request,@RequestBody AefcommNoteCalendar noteCalendar) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( noteCalendar.getId()!=null ) {//修改方法
		    noteCalendar.setUpdateTime(DateOperate.getCurrentUtilDate());
			noteCalendar.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(noteCalendarService.updateNoteCalendar(noteCalendar));
		} else {//新增方法
			noteCalendar.setCreateTime(DateOperate.getCurrentUtilDate());
			noteCalendar.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(noteCalendarService.insertNoteCalendar(noteCalendar));
		}
	}
	
	/****
	 * 删除日历记事本对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的日历记事本ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="管理",menu="管理",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteNoteCalendarByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(noteCalendarService.deleteNoteCalendarByIds(ids));
	}
}
