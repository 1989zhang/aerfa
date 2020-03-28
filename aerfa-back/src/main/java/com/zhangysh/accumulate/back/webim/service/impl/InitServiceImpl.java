package com.zhangysh.accumulate.back.webim.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.back.sys.base.BaseMybatisDao;
import com.zhangysh.accumulate.back.sys.service.IConfigDataService;
import com.zhangysh.accumulate.back.sys.service.IPersonService;
import com.zhangysh.accumulate.back.webim.service.IFriendService;
import com.zhangysh.accumulate.back.webim.service.IGroupService;
import com.zhangysh.accumulate.back.webim.service.IInitService;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;

/*****
 * 获取webim初始化信息实现
 * @author zhangysh
 * @date 2019年10月8日
 *****/
@Service
public class InitServiceImpl implements IInitService{

	@Autowired
	private IConfigDataService configDataService;
	@Autowired
	private IPersonService personService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IFriendService friendService;
    
	@Override
	public Map<String,Object> getUserData(Long personId) {
		Map<String,Object> retMap=new HashMap<String,Object>();
		AefsysConfigData picIpAddressConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_SYS_PIC_IP_ADDRESS);
		AefsysPerson sysPerson=personService.getPersonById(personId);

		//1查出个人信息并组装
		AefwebimFriendVo mineInfo=friendService.changeToWebimFriendVoBySysPerson(sysPerson);
		mineInfo.setStatus(WebimDefineConstant.WEBIM_STATUS_ONLINE);
		retMap.put(WebimDefineConstant.WEBIM_INIT_USER_DATA_MINE, mineInfo);
		
		//2.1查询出个人拥有的好友群组
		AefwebimGroup searchFriendGroup=new AefwebimGroup();
		searchFriendGroup.setOwnerId(sysPerson.getId());
		searchFriendGroup.setGroupType(WebimDefineConstant.WEBIM_GROUP_TYPE_FRIEND);
		List<AefwebimGroupVo> friendGroup=groupService.listGroup(searchFriendGroup);
		//2.2查询出好友群组下的人并组装
		List<Map<String,Object>> friendGroupList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<friendGroup.size();i++) {
			Map<String,Object> friendGroupMap=new HashMap<String,Object>();
			friendGroupMap.put(WebimDefineConstant.WEBIM_INIT_GROUP_DATA_GROUPNAME, friendGroup.get(i).getGroupName());
			friendGroupMap.put(WebimDefineConstant.WEBIM_INIT_DATA_ID, friendGroup.get(i).getId());
			//2.3查询出一个好友组下的人员列表
			AefwebimFriend searchFriend =new AefwebimFriend();
			searchFriend.setGroupId(friendGroup.get(i).getId());
			searchFriend.setRelationStatus(WebimDefineConstant.WEBIM_FRIEND_RELATION_STATUS_CONFIRM);
			List<AefwebimFriend> friendList=friendService.listFriend(searchFriend);
			List<AefwebimFriendVo> friendVoList=new ArrayList<AefwebimFriendVo>();
			for(int j=0;j<friendList.size();j++) {
				AefwebimFriend friend=friendList.get(j);
				Long friendId=friend.getFriendId();
				AefsysPerson friendSysPerson=personService.getPersonById(friendId);
				AefwebimFriendVo friendVo=friendService.changeToWebimFriendVoBySysPerson(friendSysPerson);
				friendVoList.add(friendVo);
			}
			friendGroupMap.put(WebimDefineConstant.WEBIM_INIT_GROUP_DATA_LIST, friendVoList);
			friendGroupList.add(friendGroupMap);
		}
		retMap.put(WebimDefineConstant.WEBIM_INIT_USER_DATA_FRIEND, friendGroupList);
		
		//3查询出普通群组先不显示人,只要在群中就应显示普通群
		List<AefwebimGroupVo> normalGroupList=groupService.getNormalGroupByPerson(sysPerson.getId());
		retMap.put(WebimDefineConstant.WEBIM_INIT_USER_DATA_GROUP, normalGroupList);
		return retMap;
	}
	
	@Override
	public Map<String,Object> getGroupMembers(Long groupId){
		Map<String,Object> retMap=new HashMap<String,Object>();
		//找出人员信息
		AefwebimFriend searchFriend =new AefwebimFriend();
		searchFriend.setGroupId(groupId);
		List<AefwebimFriend> friendList=friendService.listFriend(searchFriend);
		List<AefwebimFriendVo> friendVoList=new ArrayList<AefwebimFriendVo>();
		for(int i=0;i<friendList.size();i++) {
			AefwebimFriend friend=friendList.get(i);
			Long personId=friend.getPersonId();
			AefsysPerson friendSysPerson=personService.getPersonById(personId);
			AefwebimFriendVo friendVo=friendService.changeToWebimFriendVoBySysPerson(friendSysPerson);
			friendVo.setGroupId(groupId);//为方便前台获取信息补充参数字段
			friendVoList.add(friendVo);
		}
		retMap.put(WebimDefineConstant.WEBIM_INIT_GROUP_DATA_LIST, friendVoList);
		return retMap;
	}
}
