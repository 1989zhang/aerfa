package com.zhangysh.accumulate.ui.sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysQuickVisitVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysResourceVo;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;
import com.zhangysh.accumulate.ui.sys.util.TransformUtil;
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

	/**
	 * 跳转到添加快捷资源选择界面,不带ID
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 */
	@RequestMapping(value="/to_quick_resource")
	public String toSysQuickResource(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/quick_resource";
	}
	/**
	 * 跳转到添加快捷资源选择界面,是修改就带quickVisit的ID
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @param id quickVisit的ID
	 */
	@RequestMapping(value="/to_quick_resource/{id}")
	public String toSysQuickResource(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		modelMap.addAttribute("quickVisitId",id);
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/quick_resource";
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
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		//个人对象转化,补充个人对象
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);
		//快捷访问对象资源转换
		String quickListJsonStr =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_QUICK)+"";
		List<JSONObject> quickVisitVoList=JSON.parseObject(quickListJsonStr, List.class);
		//当为新增的时候补充personId和orderNo属性
		Long orderNo=Long.parseLong((quickVisitVoList.size()+1)+"");
		if(quickVisit.getId()==null){
			quickVisit.setPersonId(personVo.getId());
			quickVisit.setOrderNo(orderNo);
		}

		String retOutStr = quickVisitService.saveAdd(aerfatoken, quickVisit);
		JSONObject retJson=JSON.parseObject(retOutStr);
		if(MarkConstant.MARK_RESULT_VO_SUCESS.equals(retJson.getInteger(MarkConstant.MARK_RESULT_VO_CODE))){
			loginService.refreshSessionByToken(aerfatoken);
		}
		return retOutStr;
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
		String retOutStr=quickVisitService.deleteQuickVisitByIds(aerfatoken, ids);
		JSONObject retJson=JSON.parseObject(retOutStr);
		if(MarkConstant.MARK_RESULT_VO_SUCESS.equals(retJson.getInteger(MarkConstant.MARK_RESULT_VO_CODE))){
			loginService.refreshSessionByToken(aerfatoken);
		}
		return retOutStr;
    }

    /**
	 * 显示有权限资源列表，供添加快速访问用
	 ***/
	@RequestMapping(value="/list_resource")
	@ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap){
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		//资源对象转化
		String topResourceListJsonStr =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_RESOURCE)+"";
		//资源带父子结构所以重新组装成平行的
		List<AefsysResourceVo> resourceVoList= TransformUtil.TransformResourceStructToList(topResourceListJsonStr);
		//AefsysResourceVo因为带子对象所以要转为AefsysResource；且为展示好看不多选择方便，不要按钮级别
		List<AefsysResource> resourceList=new ArrayList<AefsysResource>();
		for(AefsysResourceVo resourceVo:resourceVoList){
			AefsysResource addResource=JSON.parseObject(JSON.toJSONString(resourceVo),AefsysResource.class);
			if(!SysDefineConstant.DIC_RESOURCE_TYPE_BUTTON.equals(addResource.getResourceType())){
				resourceList.add(addResource);
			}
		}
		return JSON.toJSONString(resourceList);
	}
}