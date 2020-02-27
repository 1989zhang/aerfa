package com.zhangysh.accumulate.ui.sys.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysPersonDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysOrgVo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;
import com.zhangysh.accumulate.ui.sys.service.ILoginService;
import com.zhangysh.accumulate.ui.sys.service.IOrgService;
import com.zhangysh.accumulate.ui.sys.service.IPersonService;
import com.zhangysh.accumulate.ui.sys.service.IUploadFileService;

/*****
 * 人员相关方法
 * @author zhangysh
 * @date 2018年8月30日
 *****/
@Controller
@RequestMapping("/sys/person")
public class PersonController {
	
	private String prefix="/sys/person";//返回界面路径即前缀
	private static final Logger logger=LoggerFactory.getLogger(PersonController.class);
	
	@Resource
	private IOrgService orgService;
	@Resource
	private IPersonService personService;
	@Resource
	private ILoginService loginService;
	@Resource
	private IUploadFileService uploadFileService;
	
	/**
	 *跳转到sys个人页面
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位页面
	 ****/
	@RequiresPermissions("sys:person:view")
	@RequestMapping(value="/to_person")
	public String toSysPerson(HttpServletRequest request, ModelMap modelMap) {
		modelMap.addAttribute("prefix",prefix);
		return prefix+"/person";
	}
	
	/****
	 *获取展示人员信息列表，默认加载所有，点击单位后加载单位下的人员 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return Bootstrap的table对象
	 ****/
	@RequiresPermissions("sys:person:list")
	@RequestMapping(value="/list")
    @ResponseBody
	public String getList(HttpServletRequest request, ModelMap modelMap,BsTablePageInfo pageInfo,AefsysPerson person) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		AefsysPersonDto personDto=new AefsysPersonDto();
		personDto.setPageInfo(pageInfo);personDto.setPerson(person);
		return personService.getList(aerfatoken, personDto);
	}
	
	/*****
	 *跳转到sys个人新增页面,默认显示点击的部门名称
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象
	 *@return templates下的单位新增页面
	 ****/
	@RequiresPermissions("sys:person:add")
	@RequestMapping(value="/to_add/{orgId}")
	public String toAdd(HttpServletRequest request, ModelMap modelMap,@PathVariable("orgId") Long orgId) {
		modelMap.put("prefix", prefix);
		String aerfatoken=HttpStorageUtil.getToken(request);
		//查询出点击的部门id和名称
		String orgJsonStr=orgService.getSingle(aerfatoken, orgId);
		AefsysOrgVo sysOrgVo=JSON.parseObject(orgJsonStr, AefsysOrgVo.class);
		AefsysPersonVo sysPersonVo=new AefsysPersonVo();
		//部门信息转化到人员对象方便前台显示
		sysPersonVo.setOrgId(sysOrgVo.getId());
		sysPersonVo.setOrgName(sysOrgVo.getFullName());
		modelMap.put("person", sysPersonVo);
		return prefix+"/add";
	}
	
	/*****
	 *检查个人账号唯一性 ,账号创建后不能修改，作为人的唯一码
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param person 要检查的对象内含账号属性
	 ****/
	@RequestMapping(value="/check_account_unique")
    @ResponseBody
	public String checkAccountUnique(HttpServletRequest request, ModelMap modelMap,AefsysPerson person) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return personService.checkAccountUnique(aerfatoken,person);
	}
	
	/***
	 *保存填写的个人信息 
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param person 保存的对象
	 ******/
	@RequiresPermissions(value={"sys:person:add","sys:person:edit"},logical= Logical.OR)
	@RequestMapping(value="/save_add")
    @ResponseBody
    public String saveAdd(HttpServletRequest request, ModelMap modelMap,AefsysPerson person) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		return personService.saveAdd(aerfatoken,person);
	}
	
	/****
	 *修改个人信息，获取个人基本信息
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的重置密码页面
	 ****/
	@RequiresPermissions("sys:person:edit")
	@RequestMapping(value="/to_edit/{id}")
	public String toEdit(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retPersonStr=personService.getSingle(aerfatoken, id);
		AefsysPersonVo personVo=JSON.parseObject(retPersonStr,AefsysPersonVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("person", personVo);
		return prefix+"/edit";
	}
	
	/***
	 *删除个人对象，可以删除多个，中间英文,隔开
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@param ids 要删除的个人ids集合，是路径获取参数
	 ***/
	@RequiresPermissions("sys:person:remove")
	@RequestMapping(value="/remove/{ids}")
    @ResponseBody
    public String remove(HttpServletRequest request, ModelMap modelMap,@PathVariable("ids") String ids){   
		String aerfatoken=HttpStorageUtil.getToken(request);
		return personService.deletePersonByIds(aerfatoken, ids);
    }
	
	/****
	 * 人员列表删除后面 重置个人密码，最主要是个人的id
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @return templates下的重置密码页面
	 ****/
	@RequiresPermissions("sys:person:resetPwd")
	@RequestMapping(value="/to_reset_pwd/{id}")
	public String toResetPwd(HttpServletRequest request, ModelMap modelMap,@PathVariable("id") Long id) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String retPersonStr=personService.getSingle(aerfatoken, id);
		AefsysPersonVo personVo=JSON.parseObject(retPersonStr,AefsysPersonVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("person", personVo);
		return prefix+"/reset_pwd";
	}
	
	/***
	 * 保存人员列表重置的个人密码，后面加上了保存自己修改密码所以保存就不做权限控制
	 * @param request 请求对象
	 * @param modelMap spring的mvc返回对象
	 * @param person 保存的对象
	 ******/
	@RequestMapping(value="/save_reset_info")
    @ResponseBody
    public String saveResetInfo(HttpServletRequest request, ModelMap modelMap,AefsysPerson person) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		//修改个人基本信息
		personService.saveAdd(aerfatoken,person);
		//刷新redis缓存
		return loginService.refreshSessionByToken(aerfatoken);
	}	
	
	/****
	 *重制本人密码方法，获取个人基本信息 ，最主要是个人的id和account
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的本人重置密码页面
	 ****/
	@RequestMapping(value="/to_reset_own_pwd")
	public String toResetOwnPwd(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("person", personVo);
		return prefix+"/reset_own_pwd";
	}
	
	/**
	 * 修改本人密码时，先验证当前密码是否正确
	 * @param request 请求对象,里面包含token等信息
	 * @param oldPassword 要验证的当前个人旧密码
	 * @return 远程地址只能输出 "true" 或 "false"，不能有其他输出。
	 */
	@RequestMapping(value="/check_old_password")
	@ResponseBody
    public String checkOldPassword(HttpServletRequest request, ModelMap modelMap,String oldPassword) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String checkResultStr=personService.checkOldPassword(aerfatoken,oldPassword);
		JSONObject jsonObject=JSON.parseObject(checkResultStr);
		if(MarkConstant.MARK_RESULT_VO_SUCESS.equals(jsonObject.getInteger(MarkConstant.MARK_RESULT_VO_CODE))) {
			return "true";
		}
		return "false";
	}
	
	/****
	 *重制本人密码方法，获取个人基本信息 ，最主要是个人的id和account
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的本人重置密码页面
	 ****/
	@RequestMapping(value="/to_reset_own_info")
	public String toResetOwnInfo(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		//单位个人对象转化
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);
		String orgObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_ORG)+"";
		AefsysOrg orgVo=JSON.parseObject(orgObjectJson,AefsysOrg.class);
		modelMap.put("prefix", prefix);
		modelMap.put("person", personVo);
		modelMap.put("org", orgVo);
		return prefix+"/reset_own_info";
	}
	
	/****
	 *重制本人头像方法，获取个人基本信息 ，最主要是个人的id和头像路径
	 *@param request 请求对象
	 *@param modelMap spring的mvc返回对象 
	 *@return templates下的本人重置头像页面
	 ****/
	@RequestMapping(value="/to_reset_own_avatar")
	public String toResetOwnAvatar(HttpServletRequest request, ModelMap modelMap) {
		String aerfatoken=HttpStorageUtil.getToken(request);
		String sessionInfoStr=loginService.getSessionByToken(aerfatoken);
		TokenModel tokenModel=JSON.parseObject(sessionInfoStr,TokenModel.class);
		String personObjectJson =tokenModel.getSession().get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
		AefsysPersonVo personVo=JSON.parseObject(personObjectJson,AefsysPersonVo.class);
		modelMap.put("prefix", prefix);
		modelMap.put("person", personVo);
		return prefix+"/reset_own_avatar";
	}
	
	/***
	 *保存修改的个人头像信息
	 *@param request 请求对象
	 *@param id 人员ID
	 *@param file 保存的图片对象
	 ******/
	@RequestMapping(value="/update_avatar")
    @ResponseBody
    public String updateAvatar(HttpServletRequest request,@RequestParam("id") Long id,@RequestParam("avatarfile") MultipartFile file) {
		try {
			if (!file.isEmpty()){
				String aerfatoken=HttpStorageUtil.getToken(request);
				AefufsUploadFileDto uploadFileDto=new AefufsUploadFileDto();
				uploadFileDto.setFileName("avatar"+id+".jpg");
				uploadFileDto.setFileBase64Data(InputStreamUtil.ByteToBase64(file.getBytes()));
				uploadFileDto.setCreateBy("userid"+id);
				String ufsRetStr=uploadFileService.uploadFile(uploadFileDto);
				JSONObject ufsJson =JSON.parseObject(ufsRetStr);
				Integer code=ufsJson.getInteger(MarkConstant.MARK_RESULT_VO_CODE);
				if( code > MarkConstant.MARK_RESULT_VO_SUCESS ) { return ufsRetStr; }
				AefufsUploadFile uploadFile=JSON.parseObject(ufsJson.getString("data"), AefufsUploadFile.class);
				AefsysPerson person=new AefsysPerson();
				person.setId(id);
				person.setHeadPic(uploadFile.getFileLink());
				//修改个人头像路径·
				personService.saveAdd(aerfatoken,person);
				//刷新redis缓存
				return loginService.refreshSessionByToken(aerfatoken);
				 
			}
		} catch (IOException e) {
			e.printStackTrace();
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage())));
		}
		return JSON.toJSONString(ResultVo.error(CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs("图片内容为空！")));
	}
}
