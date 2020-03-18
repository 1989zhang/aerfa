package com.zhangysh.accumulate.back.webim.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhangysh.accumulate.back.sys.service.IPersonService;
import com.zhangysh.accumulate.back.webim.service.IFriendService;
import com.zhangysh.accumulate.back.webim.service.IGroupService;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoInviteDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimGroupVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimMsgboxResultVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimMsgboxVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.back.webim.dao.TipsInfoDao;
import com.zhangysh.accumulate.back.webim.service.ITipsInfoService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.alibaba.fastjson.JSON;
/**
 * 提示消息 服务层实现
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
@Service
public class TipsInfoServiceImpl implements ITipsInfoService {

	@Autowired
	private IPersonService personService;
	@Autowired
	private IGroupService groupService;
	@Autowired
	private IFriendService friendService;
	@Autowired
	private TipsInfoDao tipsInfoDao;


    @Override
	public AefwebimTipsInfo getTipsInfoById(Long id){
	    return tipsInfoDao.getTipsInfoById(id);
	}

	@Override
	public String getWebimMsgbox(AefwebimTipsInfoDto tipsInfoDto){
		//pagehelper方法调用
		BsTablePageInfo pageInfo=tipsInfoDto.getPageInfo();
		AefwebimTipsInfo searchTipsInfo=tipsInfoDto.getTipsInfo();
		Page<AefwebimTipsInfo> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		tipsInfoDao.listTipsInfo(searchTipsInfo);

		List<AefwebimTipsInfo> resultList=page.getResult();
		List<AefwebimMsgboxVo> msgboxVoList=new ArrayList<AefwebimMsgboxVo>();
		for(int i=0;i<resultList.size();i++){
			AefwebimTipsInfo tipsInfo=resultList.get(i);
			AefwebimMsgboxVo msgboxVo1=new AefwebimMsgboxVo();
			msgboxVo1.setId(tipsInfo.getId());
			msgboxVo1.setContent(tipsInfo.getContent());
			msgboxVo1.setUid(tipsInfo.getToPersonId());
			msgboxVo1.setFrom(tipsInfo.getFromId());
			msgboxVo1.setType(tipsInfo.getType());
			msgboxVo1.setRemark(tipsInfo.getRemark());
			msgboxVo1.setStatus(tipsInfo.getStatus());
			msgboxVo1.setTime(DateOperate.UtilDatetoString(tipsInfo.getCreateTime(),UtilConstant.MOST_MIDDLE_DATE));
			if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND.equals(tipsInfo.getType())){
				AefwebimFriendVo friendVo=new AefwebimFriendVo();
				Long fromPersonId=tipsInfo.getFromId();
				AefsysPersonVo fromPersonVo=personService.getPersonWithExpandInfoById(fromPersonId);
				friendVo.setId(fromPersonId);
				friendVo.setAvatar(fromPersonVo.getHeadPic());
				friendVo.setUsername(fromPersonVo.getNickName());
				msgboxVo1.setUser(friendVo);
			}else if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP.equals(tipsInfo.getType())){
				AefwebimFriendVo friendVo=new AefwebimFriendVo();
				Long fromGroupId=tipsInfo.getFromId();
				AefwebimGroupVo fromGroupVo=groupService.getGroupWithExpandInfoById(fromGroupId);
				friendVo.setId(fromGroupId);
				friendVo.setAvatar(fromGroupVo.getAvatar());
				friendVo.setUsername(fromGroupVo.getGroupName());
				msgboxVo1.setUser(friendVo);
			}
			msgboxVoList.add(msgboxVo1);
		}
		return JSON.toJSONString(AefwebimMsgboxResultVo.success(msgboxVoList,page.getPages()));
	}

	@Override
	public List<Object> acceptInvite(AefwebimTipsInfoInviteDto tipsInfoInviteDto){
		List<AefwebimTipsInfo> searchTipsInfoList=listTipsInfo(tipsInfoInviteDto);
		List<Object> resultList=new ArrayList();
		//一般是一条一条处理
		if(searchTipsInfoList.size()>0){
			AefwebimTipsInfo acceptTipsInfo=searchTipsInfoList.get(0);
			dealWithTipsInfoInvite(acceptTipsInfo,WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_ACCEPT,tipsInfoInviteDto.getGroupId());
 			//开始处理返回的添加对象
			Long fromId=acceptTipsInfo.getFromId();
			//返回添加的好友json
			if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND.equals(acceptTipsInfo.getType())){
				AefwebimFriendVo friendVo=new AefwebimFriendVo();
				friendVo.setType(acceptTipsInfo.getType());
				Long fromPersonId=tipsInfoInviteDto.getFromId();
				AefsysPersonVo fromPersonVo=personService.getPersonWithExpandInfoById(fromPersonId);
				friendVo.setId(fromPersonId);
				friendVo.setAvatar(fromPersonVo.getHeadPic());
				friendVo.setUsername(fromPersonVo.getNickName());
				friendVo.setGroupid(tipsInfoInviteDto.getGroupId());
				resultList.add(friendVo);
			//返回添加的群组json
			}else if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP.equals(acceptTipsInfo.getType())){
				Long fromGroupId=acceptTipsInfo.getFromId();
				AefwebimGroupVo fromGroupVo=groupService.getGroupWithExpandInfoById(fromGroupId);
				AefwebimGroupVo groupVo=new AefwebimGroupVo();
				groupVo.setType(acceptTipsInfo.getType());
				groupVo.setId(fromGroupId);
				groupVo.setGroupname(fromGroupVo.getGroupName());
				groupVo.setAvatar(fromGroupVo.getAvatar());
				resultList.add(groupVo);
			}
		}
		return resultList;
	}

	@Override
	public boolean refuseInvite(AefwebimTipsInfoInviteDto tipsInfoInviteDto){
		List<AefwebimTipsInfo> searchTipsInfoList=listTipsInfo(tipsInfoInviteDto);
		//一般是一条一条处理
		if(searchTipsInfoList.size()>0){
			AefwebimTipsInfo refuseTipsInfo=searchTipsInfoList.get(0);
			dealWithTipsInfoInvite(refuseTipsInfo,WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE,tipsInfoInviteDto.getGroupId());
			return true;
		}
		return false;
	}


	@Override
	public BsTableDataInfo listPageTipsInfo(BsTablePageInfo pageInfo,AefwebimTipsInfo tipsInfo){
	    //pagehelper方法调用
		Page<AefwebimTipsInfo> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		tipsInfoDao.listTipsInfo(tipsInfo);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefwebimTipsInfo> listBypksTipsInfo(String ids){
		return tipsInfoDao.listBypksTipsInfo(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefwebimTipsInfo> listTipsInfo(AefwebimTipsInfo tipsInfo){
		return tipsInfoDao.listTipsInfo(tipsInfo);
	}

	@Override
	public int insertTipsInfo(AefwebimTipsInfo tipsInfo){
	    return tipsInfoDao.insertTipsInfo(tipsInfo);
	}
	
	@Override
	public int updateTipsInfo(AefwebimTipsInfo tipsInfo){
	    return tipsInfoDao.updateTipsInfo(tipsInfo);
	}
	
	@Override
	public int deleteTipsInfoById(Long id){
		return tipsInfoDao.deleteTipsInfoById(id);
	}
	
	@Override
	public int deleteTipsInfoByIds(String ids){
		return tipsInfoDao.deleteTipsInfoByIds(ConvertUtil.toStrArray(ids));
	}

	/**
	 * 处理消息提示的各种请求，包括好友或群组的通过和拒绝
	 * @param dealWithTipsInfo 要处理的消息提示对象
	 * @param mark 接受好友或拒绝好友申请标记，同消息提示状态
	 * @param addOtherFriendGroupId 当接受好友时，添加互为好友的另一个所在的好友组ID
	 */
	private void dealWithTipsInfoInvite(AefwebimTipsInfo dealWithTipsInfo,Long mark,Long addOtherFriendGroupId){
		//首先改变已有提示信息状态
		dealWithTipsInfo.setHandleTime(DateOperate.getCurrentUtilDate());
		dealWithTipsInfo.setStatus(mark);
		updateTipsInfo(dealWithTipsInfo);
		//当是好友申请时再新增一条系统通知第一个人别人,已同意;群组先不考虑
		if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND.equals(dealWithTipsInfo.getType())){
			AefsysPerson person=personService.getPersonById(dealWithTipsInfo.getToPersonId());
			AefwebimTipsInfo feedBackTipsInfo=new AefwebimTipsInfo();
			feedBackTipsInfo.setToPersonId(dealWithTipsInfo.getFromId());
			feedBackTipsInfo.setStatus(WebimDefineConstant.WEBIM_TIPS_STATUS_UNHANDLE);
			feedBackTipsInfo.setType(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_SYSTEM);
			if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_ACCEPT.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_FRIEND_ACCEPT);
			}else if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_FRIEND_REFUSE);
			}
			insertTipsInfo(feedBackTipsInfo);
		}
		//修改第一个人的好友申请状态
		AefwebimFriend searchFriend=new AefwebimFriend();
		searchFriend.setPersonId(dealWithTipsInfo.getFromId());
		searchFriend.setFriendId(dealWithTipsInfo.getToPersonId());
		searchFriend.setRelationStatus(WebimDefineConstant.WEBIM_FRIEND_RELATION_STATUS_WAIT);
		friendService.dealWithFriendByParam(searchFriend,mark,addOtherFriendGroupId);
	}
}
