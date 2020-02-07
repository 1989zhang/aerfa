package com.zhangysh.accumulate.ui.comm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommNoteCalendar;
import com.zhangysh.accumulate.pojo.comm.viewobj.AefcommNoteCalendarVo;
import com.zhangysh.accumulate.pojo.comm.viewobj.NoteCalendarEventsVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.ui.comm.service.INoteCalendarService;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;

/**
 * 日历记事本调用相关方法
 * 
 * @author zhangysh
 * @date 2019年10月12日
 */
@Controller
@RequestMapping("/comm/note_calendar")
public class NoteCalendarController {

    private String prefix="/comm/note_calendar";//返回界面路径即前缀

	@Resource
	private ILoginService loginService;
	@Autowired
	private INoteCalendarService noteCalendarService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	/**
	 * 跳转到comm日历记事本页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的日历记事本页面
	 ****/
	@RequestMapping(value="/to_note_calendar")
	public String toSysNoteCalendar(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/note_calendar";
	}
	
	/****
	 * 获取个人某时间段内的记事本信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list_around")
    @ResponseBody
	public String getListAround(HttpServletRequest request, ModelMap modelMap,Long start,Long end) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);
		NoteCalendarEventsVo eventVo=new NoteCalendarEventsVo();
		eventVo.setStart(DateOperate.longToUtilDate(start*1000));//少三个0奇怪
		eventVo.setEnd(DateOperate.longToUtilDate(end*1000));//少三个0奇怪
		eventVo.setPersonId(personVo.getId());
		return noteCalendarService.getListAround(aerfatoken,eventVo);
	}
	
	/**
	 * 跳转到sys日历记事本新增页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add/{initDate}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("initDate") String initDate) {
		modelMap.addAttribute("prefix",prefix);
		modelMap.addAttribute("initDate",initDate);
		return prefix+"/add";
	}
	
    /***
	 * 保存填写的日历记事本对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param noteCalendar 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefcommNoteCalendar noteCalendar) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);
		noteCalendar.setPersonId(personVo.getId());
		return noteCalendarService.saveAdd(aerfatoken, noteCalendar);
	}
	
	/****
	 * 修改日历记事本，先获取日历记事本信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retNoteCalendarStr=noteCalendarService.getSingle(aerfatoken, id);
		AefcommNoteCalendarVo noteCalendarVo=JSON.parseObject(retNoteCalendarStr,AefcommNoteCalendarVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("noteCalendar", noteCalendarVo);
		return prefix+"/edit";
	}
	
	/***
	 * 删除日历记事本对象，可以删除多个，中间英文,隔开
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return noteCalendarService.deleteNoteCalendarByIds(aerfatoken, ids);
    }
}