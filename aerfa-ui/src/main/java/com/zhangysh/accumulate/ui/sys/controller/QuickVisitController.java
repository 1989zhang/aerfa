package com.zhangysh.accumulate.ui.sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysQuickVisitVo;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;
import com.zhangysh.accumulate.ui.sys.service.IQuickVisitService;

import java.util.ArrayList;
import java.util.List;

/**
 * 常用功能快速访问调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
@Controller
@RequestMapping("/sys/quick_visit")
public class QuickVisitController {

    private String prefix="/sys/quick_visit";//返回界面路径即前缀

	@Autowired
	private IQuickVisitService quickVisitService;
	@Resource
	private ILoginService loginService;

	/**
	 * 跳转到sys常用功能快速访问页面
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的常用功能快速访问页面
	 ****/
	@RequestMapping(value="/to_quick_visit")
	public String toSysQuickVisit(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		//快捷访问对象资源转换
		String quickListJsonStr =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_QUICK)+"";
		List<JSONObject> quickVisitVoList=JSON.parseObject(quickListJsonStr, List.class);
		modelMap.addAttribute("prefix",prefix);
		modelMap.addAttribute("quick",quickVisitVoList);
		return prefix+"/quick_visit";
	}

	/****
	 * 修改常用功能快速访问，先获取常用功能快速访问信息
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		modelMap.put("prefix", prefix);
		return prefix+"/edit";
	}

    /***
	 * 保存填写的常用功能快速访问对象
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param quickVisit 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysQuickVisit quickVisit) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return quickVisitService.saveAdd(aerfatoken, quickVisit);
	}

	/***
	 * 删除常用功能快速访问对象，可以删除多个，中间英文,隔开
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象 
	 * @param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return quickVisitService.deleteQuickVisitByIds(aerfatoken, ids);
    }
}