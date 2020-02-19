package com.zhangysh.accumulate.back.sys.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.zhangysh.accumulate.back.sys.service.*;
import com.zhangysh.accumulate.common.util.GeneralUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.*;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.webim.service.IFriendService;
import com.zhangysh.accumulate.back.webim.service.IGroupService;
import com.zhangysh.accumulate.back.webim.service.IWebimPersonService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.common.util.UuidUtil;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysLoginDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimPerson;

/*****
 * 登录相关接口具体实现
 * @author zhangysh
 * @date 2019年5月27日
 *****/
@Service
public class LoginServiceImpl implements ILoginService{

	@Autowired
	private IOrgService orgService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IPersonAddressService personAddressService;
    @Autowired
    private IRedisRelatedService redisRelatedService;
    @Autowired 
    private IConfigDataService configDataService;
    @Autowired 
    private IPersonLoginInfoService personLoginInfoService;
    @Autowired 
    private IWebimPersonService webimPersonService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IFriendService friendService;
	@Autowired
	private IResourceService resourceService;
	@Autowired
	private IRoleService roleService;

	@Override
	public String checkLoginInfo(AefsysLoginDto loginDto) {
		//账号密码为空，直接返回
		if(StringUtil.isEmpty(loginDto.getAccount())||StringUtil.isEmpty(loginDto.getPassword())) {
			return  JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_ACCOUNT_PASSWORD_EMPTY_ERROR));
		}
		AefsysPerson sysPerson=personService.getPersonByAccountPassword(loginDto.getAccount(), loginDto.getPassword());
		String aerfatoken=UuidUtil.getID();
		//用户存在即账号密码正确
		if(sysPerson!=null) {
			//登录刷新redis信息
			setNewestTokenInfoToRedis(aerfatoken,sysPerson);
			//创建或更新个人登录信息PersonLoginInfo
			setNewestPersonLoginInfo(sysPerson,loginDto);
			//创建即时通讯相关信息
			setAddWebimRelatedInfo(sysPerson);
			
			return  JSON.toJSONString(ResultVo.success(aerfatoken));
		}else {
			return  JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_ACCOUNT_PASSWORD_WRONG_ERROR));
		}
	}
	
	@Override
	public String getSessionByToken(String aerfatoken) {
		return JSON.toJSONString(redisRelatedService.getTokenModelByToken(aerfatoken));
	}
	
	@Override
	public boolean refreshSessionByToken(String aerfatoken) {
		TokenModel tokenModel=redisRelatedService.getTokenModelByToken(aerfatoken);
		Long personId=tokenModel.getPersonId();
		AefsysPerson sysPerson=personService.getPersonById(personId);
		if(sysPerson!=null) {
			setNewestTokenInfoToRedis(aerfatoken,sysPerson);
			return true;
		}
		return false;
	}
	
	/***
	 * 根据个人查询出的个人对象和uuid的token，设置个人相关的最新信息到redis中
	 * @param aerfatoken uuid的token
	 * @param sysPerson 查询出的只有的个人信息
	 ****/
	private void setNewestTokenInfoToRedis(String aerfatoken,AefsysPerson sysPerson) {
		AefsysPersonVo sysPersonVo=new AefsysPersonVo();
		sysPersonVo=JSON.parseObject(JSON.toJSONString(sysPerson), AefsysPersonVo.class);
		//token的model对象构建
		TokenModel tokenModel=new TokenModel();
		Map<String, Object> session = new HashMap<String, Object>();

        //查询出联系地址
		AefsysPersonAddress personAddress=personAddressService.getPersonAddressByPersonId(sysPerson.getId());
		if(personAddress!=null) {sysPersonVo.setAddress(personAddress.getFullAddress());}
		//查询组装头像访问全路径
		List<AefsysConfigData> configDataList=configDataService.getAllConfigDataFromRedis();
		for(AefsysConfigData configData:configDataList) {
			if(SysDefineConstant.CONFIG_DATA_PIC_IP_ADDRESS.equals(configData.getDataCode())) {
				if(StringUtil.isNotEmpty(sysPerson.getHeadPic())) {
					sysPersonVo.setHeadPic(configData.getDataValue()+sysPerson.getHeadPic());	
				}else {
					sysPersonVo.setHeadPic(WebimDefineConstant.WEBIM_DEFAULT_PERSONAL_AVATAR);
				}
			}
		}

		//查询出人员所在单位
		AefsysOrg sysOrg=orgService.getOrgById(sysPerson.getOrgId());
		if(sysOrg!=null) {sysPersonVo.setOrgName(sysOrg.getFullName());}

		//查询出人员的角色权限和角色对应的资源
		List<AefsysRole> roleList=roleService.getPersonRolesByPersonId(sysPerson.getId());
		List<AefsysResourceVo> resourceList=resourceService.getPersonStructResourcesByPersonId(sysPerson.getId());

		session.put(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON, sysPersonVo);
		session.put(CacheConstant.TOKENMODEL_SESSION_KEY_ORG, sysOrg);
		session.put(CacheConstant.TOKENMODEL_SESSION_KEY_ROLE,roleList);
		session.put(CacheConstant.TOKENMODEL_SESSION_KEY_RESOURCE,resourceList);

		//设置属性到对象里面
		tokenModel.setPersonId(sysPerson.getId());
		tokenModel.setToken(aerfatoken);
		tokenModel.setSession(session);
		redisRelatedService.setTokenInfo(aerfatoken, tokenModel);
	}

	/****
	 * 创建或更新个人登录信息PersonLoginInfo：登录信息是一对一存在就修改不存在就新增
	 * @param sysPerson 查询到的个人对象
	 ***/
	private void setNewestPersonLoginInfo(AefsysPerson sysPerson,AefsysLoginDto loginDto) {
		AefsysPersonLoginInfo personLoginInfo=personLoginInfoService.getPersonLoginInfoByPersonId(sysPerson.getId());
		//设置最新登录信息对象
		AefsysPersonLoginInfo newestPersonLoginInfo=new AefsysPersonLoginInfo();
		newestPersonLoginInfo.setPersonId(sysPerson.getId());
		newestPersonLoginInfo.setPersonName(sysPerson.getPersonName());
		newestPersonLoginInfo.setNickName(sysPerson.getNickName());
		newestPersonLoginInfo.setLoginVoucher(sysPerson.getAccount());
		newestPersonLoginInfo.setLoginInTime(DateOperate.getCurrentUtilDate());
		newestPersonLoginInfo.setLoginStatus(SysDefineConstant.DB_LOGIN_STATUS_ON_LINE);
		newestPersonLoginInfo.setServerUiIp(loginDto.getServerIp());
		newestPersonLoginInfo.setLoginIp(loginDto.getClientIp());
		newestPersonLoginInfo.setOsType(loginDto.getOsType());
		newestPersonLoginInfo.setBrowserType(loginDto.getBrowserType());
		if(personLoginInfo!=null) {
			newestPersonLoginInfo.setId(personLoginInfo.getId());
			personLoginInfoService.updatePersonLoginInfo(newestPersonLoginInfo);
		}else {
			personLoginInfoService.insertPersonLoginInfo(newestPersonLoginInfo);
		}
	}
	
	/**
	 * 创建即时通讯相关信息如下:
	 * 个人拓展信息WebimPerson，信息是一对一存在就不管，没有就新增;
	 * 添加默认组'我的好友',和默认好友智能小法webimAIxf;
	 * @param sysPerson 查询到的个人对象
	 * ***/
	private void setAddWebimRelatedInfo(AefsysPerson sysPerson) {
		Long personId=sysPerson.getId();
		//个人拓展信息WebimPerson
		AefwebimPerson searchWebimPerson =new AefwebimPerson();
		searchWebimPerson.setPersonId(personId);
		List<AefwebimPerson> webimPersonList=webimPersonService.listPerson(searchWebimPerson);
		if(webimPersonList.size()==0) {	
			searchWebimPerson.setSignature(WebimDefineConstant.WEBIM_DEFAULT_SIGNATURE);
			webimPersonService.insertPerson(searchWebimPerson);//webim个人拓展信息不存在,则组装参数插入数据
		}
		
		//添加我的好友默认组
		Long defaultGroupId;
		AefwebimGroup searchGroup=new AefwebimGroup();//先查找是否有'我的好友'群组
		searchGroup.setGroupName(WebimDefineConstant.WEBIM_DEFAULT_FRIEND_GROUP_NAME);
		searchGroup.setGroupType(WebimDefineConstant.WEBIM_GROUP_TYPE_FRIEND);
		searchGroup.setOwnerId(personId);
		List<AefwebimGroup> defaultGroupList=groupService.listGroup(searchGroup);
		if(defaultGroupList.size()>0) {
			defaultGroupId=defaultGroupList.get(0).getId();//存在不需要新增
		}else {
			AefwebimGroup group=new AefwebimGroup();
			group.setGroupName(WebimDefineConstant.WEBIM_DEFAULT_FRIEND_GROUP_NAME);
			group.setGroupType(WebimDefineConstant.WEBIM_GROUP_TYPE_FRIEND);
			group.setOwnerId(personId);
			group.setCreateTime(DateOperate.getCurrentUtilDate());
			groupService.insertGroup(group);
			defaultGroupId=group.getId();//添加我的好友默认组
		}	
		
		//添加默认系统好友,智能小法webimAIxf
		AefwebimFriend searchFriend =new AefwebimFriend();
		searchFriend.setPersonId(personId);
		searchFriend.setFriendId(WebimDefineConstant.WEBIM_AIXF_PERSON_ID);
		List<AefwebimFriend> friendList=friendService.listFriend(searchFriend);//查找好友是否添加了智能小法webimaixf
		if(friendList.size()==0&&!WebimDefineConstant.WEBIM_AIXF_PERSON_ID.equals(personId)) {//没有添加智能小法,且不是智能小法自己才添加
			AefwebimFriend webimAixfFriend =new AefwebimFriend();
			webimAixfFriend.setPersonId(personId);
			webimAixfFriend.setGroupId(defaultGroupId);
			webimAixfFriend.setFriendId(WebimDefineConstant.WEBIM_AIXF_PERSON_ID);
			friendService.insertFriend(webimAixfFriend);//添加智能小法
		}
	}
}
