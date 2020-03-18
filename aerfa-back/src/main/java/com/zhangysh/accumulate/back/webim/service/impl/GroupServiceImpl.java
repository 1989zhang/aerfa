package com.zhangysh.accumulate.back.webim.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.back.sys.service.IConfigDataService;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimGroupVo;
import com.zhangysh.accumulate.back.webim.dao.GroupDao;
import com.zhangysh.accumulate.back.webim.service.IFriendService;
import com.zhangysh.accumulate.back.webim.service.IGroupService;
import com.zhangysh.accumulate.back.webim.service.ITipsInfoService;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.StringUtil;
/**
 * 群组 服务层实现
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
@Service
public class GroupServiceImpl implements IGroupService {

	@Autowired
	private GroupDao groupDao;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private ITipsInfoService tipsInfoService;
	@Autowired
	private IConfigDataService configDataService;

    @Override
	public AefwebimGroup getGroupById(Long id){
	    return groupDao.getGroupById(id);
	}

	@Override
	public AefwebimGroupVo getGroupWithExpandInfoById(Long id){
		AefwebimGroup group=getGroupById(id);
		return changeGroupToGroupVo(group);
	}

	@Override
	public BsTableDataInfo listPageGroup(BsTablePageInfo pageInfo,AefwebimGroup group){
	    //pagehelper方法调用
		Page<AefwebimGroup> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		groupDao.listGroup(group);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefwebimGroup> listBypksGroup(String ids){
		return groupDao.listBypksGroup(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefwebimGroupVo> listGroup(AefwebimGroup searchGroup){
    	List<AefwebimGroupVo> retGroupList=new ArrayList<AefwebimGroupVo>();
		List<AefwebimGroup> groupList=groupDao.listGroup(searchGroup);
		for(AefwebimGroup group:groupList){
			retGroupList.add(changeGroupToGroupVo(group));
		}
		return retGroupList;
	}

	@Override
	public int insertGroup(AefwebimGroup group){
	    return groupDao.insertGroup(group);
	}
	
	@Override
	public int updateGroup(AefwebimGroup group){
	    return groupDao.updateGroup(group);
	}
	
	@Override
	public int deleteGroupById(Long id){
		return groupDao.deleteGroupById(id);
	}
	
	@Override
	public int deleteGroupByIds(String ids){
		return groupDao.deleteGroupByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	public Map<String,Object> getSearchPage(AefwebimSearchDto searchDto){
		Map<String,Object> retPageMap=new HashMap<String,Object>();
		//查询id匹配的群组
		Long searchGroupId=ConvertUtil.toLong(searchDto.getValue(), 0L);
		if(searchGroupId>0) {
			retPageMap.put(WebimDefineConstant.WEBIM_SEARCH_PAGE_COUNT, 1L);
		}else {//查询groupName匹配的群组
			AefwebimGroup searchGroup=new AefwebimGroup();
			searchGroup.setGroupName(searchDto.getValue());
			List<AefwebimGroupVo> groupVoList=this.listGroup(searchGroup);
			retPageMap.put(WebimDefineConstant.WEBIM_SEARCH_PAGE_COUNT, groupVoList.size());
		}
		retPageMap.put(WebimDefineConstant.WEBIM_SEARCH_PAGE_LIMIT, WebimDefineConstant.WEBIM_SEARCH_PAGE_LIMIT_NUMBER);
		return retPageMap;
	}
	
	@Override
	public List<AefwebimGroupVo> getSearchInfo(AefwebimSearchDto searchDto){
		List<AefwebimGroupVo> retGroupVoList=new ArrayList<AefwebimGroupVo>();
		//查询id匹配的群组
		Long searchGroupId=ConvertUtil.toLong(searchDto.getValue(), 0L);
		if(searchGroupId>0) {
			AefwebimGroup group=this.getGroupById(searchGroupId);
			//普通群组才展示，好友群组不展示
			if(WebimDefineConstant.WEBIM_GROUP_TYPE_GROUP.equals(group.getGroupType())) {
				AefwebimGroupVo groupVo=new AefwebimGroupVo();
				groupVo.setId(group.getId());
				groupVo.setGroupName(group.getGroupName());
				groupVo.setAvatar(group.getAvatar());
				retGroupVoList.add(groupVo);
			}
		}else {
			AefwebimGroup searchGroup=new AefwebimGroup();
			searchGroup.setGroupName(searchDto.getValue());
			searchGroup.setGroupType(WebimDefineConstant.WEBIM_GROUP_TYPE_GROUP);
			List<AefwebimGroupVo> groupVoList=this.listGroup(searchGroup);
			retGroupVoList=groupVoList;
		}
		return retGroupVoList;
	}
	
	@Override
	public String insertGroupFriendAndAddTipsInfo(AefwebimApplyDto apply,AefsysPerson operPerson) {
		AefwebimFriend searchFriend=new AefwebimFriend();
		searchFriend.setPersonId(apply.getPersonId());
		searchFriend.setGroupId(apply.getGroupId());
		List<AefwebimFriend> searchFriendList=friendService.listFriend(searchFriend);
		if(searchFriendList.size()==0) {
			AefwebimFriend friend=JSON.parseObject(JSON.toJSONString(apply),AefwebimFriend.class);
			friend.setCreateTime(DateOperate.getCurrentUtilDate());
			friend.setCreateBy(operPerson.getPersonName());
			friend.setRelationStatus(WebimDefineConstant.WEBIM_FRIEND_RELATION_STATUS_WAIT);
			friendService.insertFriend(friend);
		}
		//首先查找是否有未处理的提示信息,发送到的消息为群创建者的id
		AefwebimGroup webimGroup=getGroupById(apply.getGroupId());
		AefwebimTipsInfo searchTipsInfo=new AefwebimTipsInfo();
		searchTipsInfo.setFromId(apply.getPersonId());
		searchTipsInfo.setToPersonId(webimGroup.getOwnerId());
		searchTipsInfo.setType(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP);
		List<AefwebimTipsInfo> searchTipsInfoList=tipsInfoService.listTipsInfo(searchTipsInfo);
		if(searchTipsInfoList.size()==0) {
			AefwebimTipsInfo tipsInfo=new AefwebimTipsInfo();
			tipsInfo.setFromId(apply.getPersonId());
			tipsInfo.setToPersonId(webimGroup.getOwnerId());
			tipsInfo.setType(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP); 
			tipsInfo.setContent(WebimDefineConstant.WEBIM_APPLY_TIPS_GROUP_APPLY);
			tipsInfo.setRemark(apply.getRemark());
			tipsInfo.setStatus(WebimDefineConstant.WEBIM_TIPS_STATUS_UNHANDLE);
			tipsInfo.setCreateTime(DateOperate.getCurrentUtilDate());
			tipsInfo.setCreateBy(operPerson.getPersonName());
			tipsInfoService.insertTipsInfo(tipsInfo);
		}
		
		//返回信息逻辑：都有信息就返回重复申请失败，有一个新增数据就提示发送成功
		String retTrueStr=StringUtil.format(WebimDefineConstant.WEBIM_APPLY_TIPS_CONFIRM, webimGroup.getGroupName());
		String falseTrueStr=StringUtil.format(WebimDefineConstant.WEBIM_APPLY_TIPS_REPEAT, webimGroup.getGroupName());
		if(searchFriendList.size()>0&&searchTipsInfoList.size()>0) {
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_OPERATE_ERROR.fillArgs(falseTrueStr)));
		}
		return JSON.toJSONString(ResultVo.success(retTrueStr));
	}

	/**
	 * 把数据库查询到的AefwebimGroup对象转化为拓展页面展示对象AefwebimGroupVo
	 *
	 */
	private AefwebimGroupVo changeGroupToGroupVo(AefwebimGroup group){
		//头像前缀配置路径
		AefsysConfigData picIpAddressConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_SYS_PIC_IP_ADDRESS);

		AefwebimGroupVo groupVo=new AefwebimGroupVo();
		BeanUtils.copyProperties(group,groupVo);
		//单独处理群组名称
		groupVo.setGroupname(groupVo.getGroupName());
		//拓展头像地址处理
		if(StringUtil.isNotEmpty(groupVo.getAvatar())) {
			groupVo.setAvatar(picIpAddressConfigData.getDataValue()+groupVo.getAvatar());
		}else{
			groupVo.setAvatar(WebimDefineConstant.WEBIM_GROUP_DEFAULT_AVATAR);
		}
		return groupVo;
	}
}
