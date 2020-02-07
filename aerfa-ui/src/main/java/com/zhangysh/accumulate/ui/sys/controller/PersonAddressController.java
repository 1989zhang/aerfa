package com.zhangysh.accumulate.ui.sys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonAddressDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonAddressVo;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;
import com.zhangysh.accumulate.ui.sys.service.IPersonAddressService;

/**
 * 个人联系地址调用相关方法
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
@Controller
@RequestMapping("/sys/person_address")
public class PersonAddressController {

    private String prefix="/sys/person_address";//返回界面路径即前缀

	@Resource
	private ILoginService loginService;
	@Autowired
	private IPersonAddressService personAddressService;
	
	/**
	 *跳转到sys个人联系地址页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的个人联系地址页面
	 ****/
	@RequestMapping(value="/to_person_address/{personId}")
	public String toSysPersonAddress(HttpServletRequest request, ModelMap modelMap,@PathVariable("personId") Long personId) {
		AefsysPersonAddress personAddress=new AefsysPersonAddress();
		personAddress.setPersonId(personId);
		modelMap.addAttribute("prefix",prefix);
		modelMap.addAttribute("personAddress",personAddress);
		return prefix+"/person_address";
	}
	
	/****
	 *获取展示个人联系地址列表，且分页显示
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysPersonAddress personAddress) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysPersonAddressDto personAddressDto=new AefsysPersonAddressDto();
		personAddressDto.setPageInfo(pageInfo);personAddressDto.setPersonAddress(personAddress);
		return personAddressService.getList(aerfatoken,personAddressDto);
	}
	
	/**
	 *跳转到sys个人联系地址新增页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequestMapping(value="/to_add/{personId}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("personId") Long personId) {
		AefsysPersonAddress personAddress=new AefsysPersonAddress();
		personAddress.setPersonId(personId);
		modelMap.addAttribute("prefix",prefix);
		modelMap.addAttribute("personAddress",personAddress);
		return prefix+"/add";
	}
	
    /***
	 *保存填写的个人联系地址对象
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param personAddress 保存的对象
	 ******/
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysPersonAddressVo personAddressVo) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysPersonAddress personAddress=JSON.parseObject(JSON.toJSONString(personAddressVo), AefsysPersonAddress.class);
		
		String prefixAddress=personAddressVo.getPrefixAddress();
		String suffixAddressStr=personAddressVo.getDetailAddress();
		String prefixAddressStr = "";
		if(StringUtil.isNotEmpty(prefixAddress)) {
			String[] prefixAddressArr=prefixAddress.split(MarkConstant.MARK_SPLIT_EN_VIRGULE);
			for(int i=0;i<prefixAddressArr.length;i++) {
				if(i==0) {
					personAddress.setProvince(prefixAddressArr[i]);
					prefixAddressStr+=prefixAddressArr[i];
				}else if(i==1) {
					personAddress.setCity(prefixAddressArr[i]);
					prefixAddressStr+=prefixAddressArr[i];
				}else if(i==2) {
					personAddress.setDistrict(prefixAddressArr[i]);
					prefixAddressStr+=prefixAddressArr[i];
				}else if(i==3) {
					personAddress.setTown(prefixAddressArr[i]);
					prefixAddressStr+=prefixAddressArr[i];
				}
			}
		}
		personAddress.setFullAddress(prefixAddressStr+suffixAddressStr);
		return personAddressService.saveAdd(aerfatoken, personAddress);
	}
	
	/****
	 *修改个人联系地址，先获取个人联系地址信息
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的页面
	 ****/
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retPersonAddressStr=personAddressService.getSingle(aerfatoken, id);
		AefsysPersonAddressVo personAddressVo=JSON.parseObject(retPersonAddressStr,AefsysPersonAddressVo.class);
		String prefixAddressStr="";
		if(StringUtil.isNotEmpty(personAddressVo.getProvince())) {
			prefixAddressStr+=personAddressVo.getProvince()+MarkConstant.MARK_SPLIT_EN_VIRGULE;
		} if(StringUtil.isNotEmpty(personAddressVo.getCity())) {
			prefixAddressStr+=personAddressVo.getCity()+MarkConstant.MARK_SPLIT_EN_VIRGULE;
		} if(StringUtil.isNotEmpty(personAddressVo.getDistrict())) {
			prefixAddressStr+=personAddressVo.getDistrict()+MarkConstant.MARK_SPLIT_EN_VIRGULE;
		} if(StringUtil.isNotEmpty(personAddressVo.getTown())) {
			prefixAddressStr+=personAddressVo.getTown()+MarkConstant.MARK_SPLIT_EN_VIRGULE;
		}
		if(StringUtil.isNotEmpty(prefixAddressStr)) {
			prefixAddressStr=prefixAddressStr.substring(0,prefixAddressStr.length()-1);
		}
		personAddressVo.setPrefixAddress(prefixAddressStr);
		modelMap.put("prefix", prefix);
		modelMap.put("personAddress", personAddressVo);
		return prefix+"/edit";
	}
	
	/***
	 *删除个人联系地址对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的ids集合，是路径获取参数
	 ***/
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return personAddressService.deletePersonAddressByIds(aerfatoken, ids);
    }
	
	/***
	 * 个人联系地址设置为默认地址
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param id 要设置的id，是路径获取参数
	 ***/
	@RequestMapping(value="/default/{id}")
    @ResponseBody
    public String setDefault(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		//设置默认地址
		personAddressService.setDefault(aerfatoken, id);
		//刷新redis缓存
		return loginService.refreshSessionByToken(aerfatoken);
    }
}