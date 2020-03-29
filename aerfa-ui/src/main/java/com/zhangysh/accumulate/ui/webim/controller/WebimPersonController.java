package com.zhangysh.accumulate.ui.webim.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimPersonVo;
import com.zhangysh.accumulate.ui.webim.service.IWebimPersonService;

/*****
 * 即时通讯webim个人拓展信息相关
 * @author zhangysh
 * @date 2019年11月24日
 *****/
@Controller
@RequestMapping("/webim/person")
public class WebimPersonController {

	private String prefix="/webim/person";//返回界面路径即前缀
	
	@Resource
	private IWebimPersonService webimPersonService;

	/****
	 * 日期格式局部转换，为了转换:webimPersonVo.birthday
	 * 转换日期 注意这里的转化要和传进来的字符串的格式一致 如2019-12-12 就应该为yyyy-MM-dd
	 **/
	@InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
	
	/****
	 * 跳转到好友信息页面
	 * @param request 请求对象
	 * @param model spring的mvc返回对象
	 * @return thymeleaf在templates下的页面名称查找好友界面 
	 ****/
	@RequestMapping(value="/to_information")
	public String toInformation(HttpServletRequest request,ModelMap modelMap,Long id,String type) {
		modelMap.addAttribute("prefix",prefix);
		modelMap.addAttribute("id",id);
		modelMap.addAttribute("type",type);
		if(WebimDefineConstant.WEBIM_INIT_USER_DATA_FRIEND.equals(type)) {
			return "webim/module/information";
		}else {
			modelMap.addAttribute("prefix","/webim/group");//返回界面路径即前缀
			return "webim/module/group";
		}
		
	}
	
	/***
	 * 点击查看好友获取好友基本信息
	 * @param request 请求对象
	 * @param model spring的mvc返回对象
	 * @param id 人员ID
	 * @param type 查看对象类型应为friend
	 ***/
	@RequestMapping(value="/get_information")
	@ResponseBody
	public String getInformation(HttpServletRequest request,ModelMap modelMap,Long id,String type) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		modelMap.addAttribute("prefix",prefix);
		AefwebimSearchDto searchDto=new AefwebimSearchDto();
		searchDto.setType(type);
		searchDto.setValue(id+"");
		return webimPersonService.getInformation(aerfatoken,searchDto);
	}
	
	/***
	 * 保存修改的我的个人信息
	 * @param webimPersonVo 我的个人信息
	 ***/
	@RequestMapping(value="/save_my_information")
	@ResponseBody
	public String saveMyInformation(HttpServletRequest request,AefwebimPersonVo webimPersonVo) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return webimPersonService.saveMyInformation(aerfatoken,webimPersonVo);
	}
	
}
