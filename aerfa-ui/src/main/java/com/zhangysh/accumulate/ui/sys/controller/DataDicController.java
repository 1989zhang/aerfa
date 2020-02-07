package com.zhangysh.accumulate.ui.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataDic;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysDataDicDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysDataDicVo;
import com.zhangysh.accumulate.ui.sys.service.IDataDicService;

/**
 * 数据字典调用相关方法
 * 
 * @author zhangysh
 * @date 2019年04月15日
 */
@Controller
@RequestMapping("/sys/dic")
public class DataDicController {

    private String prefix="/sys/dic";//返回界面路径即前缀

	@Autowired
	private IDataDicService dataDicService;
	
	/***
	 * 主方法：根据字典类型获取字典集合。
	 * @param type 字典类型编码
	 * @return 字典类型集合
	 ***/
	@RequestMapping(value="/dic")
    @ResponseBody
	public String getDic(String type) {
		String dataDicVoListJson=dataDicService.getDataByType(type);
		return dataDicVoListJson;
	}
	
	
	/**
	 *跳转到sys数据字典类型页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的数据字典页面
	 ****/
	@RequestMapping(value="/to_dic_type")
	public String toSysDicType(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/dic_type";
	}
	
	/**
	 *跳转到sys数据字典列表页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的数据字典页面
	 ****/
	@RequestMapping(value="/to_dic_data/{id}")
	public String toSysDicData(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String dicTypeJsonStr=dataDicService.getSingle(aerfatoken, id);
		modelMap.put("dicType", JSON.parseObject(dicTypeJsonStr,AefsysDataDicVo.class));
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/dic_data";
	}
	
	/****
	 *获取展示数据字典列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list_type")
    @ResponseBody
	public String getTypeList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysDataDic dataDic) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		//是字典类型
		dataDic.setIsType(1L);
		AefsysDataDicDto dataDicDto=new AefsysDataDicDto();
		dataDicDto.setPageInfo(pageInfo);dataDicDto.setDataDic(dataDic);
		return dataDicService.getList(aerfatoken,dataDicDto);
	}
	
	/****
	 *获取展示数据字典列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list_data")
    @ResponseBody
	public String getDataList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysDataDic dataDic) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		//不是字典类型
		dataDic.setIsType(0L);
		AefsysDataDicDto dataDicDto=new AefsysDataDicDto();
		dataDicDto.setPageInfo(pageInfo);dataDicDto.setDataDic(dataDic);
		return dataDicService.getList(aerfatoken,dataDicDto);
	}
	
	/**
	 *跳转到sys数据字典新增类型页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add_type")
	public String toAddType(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add_type";
	}
	
	/**
	 *跳转到sys数据字典新增列表页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add_data/{typeCode}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("typeCode") String typeCode) {
		modelMap.put("typeCode",typeCode);
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/add_data";
	}
	
	/*****
	 *检查字典类型编码的唯一性 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param dataDic 要检查的数据字典包括：typeCode和id,id为了排除自己
	 ****/
	@RequestMapping(value="/check_data_dic_unique")
    @ResponseBody
	public String checkDataDicUnique(HttpServletRequest request, ModelMap modelMap,AefsysDataDic dataDic) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataDicService.checkDataDicUnique(aerfatoken,dataDic);
	}
	
	
    /***
	 *保存填写的数据字典对象
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param dataDic 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysDataDic dataDic) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataDicService.saveAdd(aerfatoken, dataDic);
	}
	
	/****
	 *修改数据字典，先获取数据字典信息,然后跳转到类型
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit_type/{id}")
	public String toEditType(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retDataDicStr=dataDicService.getSingle(aerfatoken, id);
		AefsysDataDic dataDic=JSON.parseObject(retDataDicStr,AefsysDataDic.class);
		modelMap.put("prefix", prefix);
		modelMap.put("dataDic", dataDic);
		return prefix+"/edit_type";
	}
	
	/****
	 *修改数据字典，先获取数据字典信息,然后跳转到列表
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit_data/{id}")
	public String toEditData(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retDataDicStr=dataDicService.getSingle(aerfatoken, id);
		AefsysDataDic dataDic=JSON.parseObject(retDataDicStr,AefsysDataDic.class);
		modelMap.put("prefix", prefix);
		modelMap.put("dataDic", dataDic);
		return prefix+"/edit_data";
	}
	
	/***
	 *删除数据字典对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return dataDicService.deleteDataDicByIds(aerfatoken, ids);
    }
}