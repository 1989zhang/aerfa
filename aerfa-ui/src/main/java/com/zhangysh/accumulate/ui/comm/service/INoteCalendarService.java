package com.zhangysh.accumulate.ui.comm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommNoteCalendar;
import com.zhangysh.accumulate.pojo.comm.viewobj.NoteCalendarEventsVo;

/*****
 * 日历记事本相关调用后台方法
 * @author zhangysh
 * @date 2019年10月12日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface INoteCalendarService {

	/****
	 * 获取个人某时间段内的记事本信息
	 * @param aerfatoken token对象
	 * @param personId 日历记事本所属人的id
	 ***/
	@RequestMapping(value = "/comm/note_calendar/list_around",method = RequestMethod.POST)
	public String getListAround(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody NoteCalendarEventsVo eventVo);
	
	/****
	 * 获取单个日历记事本信息
	 * @param aerfatoken token对象
	 * @param id 日历记事本的id
	 ***/
	@RequestMapping(value = "/comm/note_calendar/single",method = RequestMethod.POST)
	public String getSingle(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

	/****
	 * 保存新增的日历记事本信息 
	 * @param aerfatoken token对象
	 * @param NoteCalendar 要保存的日历记事本对象
	 ***/
	@RequestMapping(value = "/comm/note_calendar/save",method = RequestMethod.POST)
	public String saveAdd(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody AefcommNoteCalendar noteCalendar);

	/****
	 * 删除日历记事本对象，可以删除多个，中间英文,隔开
	 * @param aerfatoken token对象
	 * @param ids 要删除的日历记事本ids集合，是路径获取参数
	 ***/
	@RequestMapping(value = "/comm/note_calendar/delete",method = RequestMethod.POST)
	public String deleteNoteCalendarByIds(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody String ids);

}