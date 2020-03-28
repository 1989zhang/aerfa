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
			msgboxVo1.setFrom(tipsInfo.getFromPersonId());
			msgboxVo1.setType(tipsInfo.getType());
			msgboxVo1.setRemark(tipsInfo.getRemark());
			msgboxVo1.setStatus(tipsInfo.getStatus());
			msgboxVo1.setTime(DateOperate.UtilDatetoString(tipsInfo.getCreateTime(),UtilConstant.MOST_MIDDLE_DATE));
			if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND_APPLY.equals(tipsInfo.getType())){
				AefwebimFriendVo friendVo=new AefwebimFriendVo();
				Long fromPersonId=tipsInfo.getFromPersonId();
				AefsysPersonVo fromPersonVo=personService.getPersonWithExpandInfoById(fromPersonId);
				friendVo.setId(fromPersonId);
				friendVo.setAvatar(fromPersonVo.getHeadPic());
				friendVo.setUsername(fromPersonVo.getNickName());
				msgboxVo1.setUser(friendVo);
			//申请或邀请加入群聊查询群信息
			}else if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP_APPLY.equals(tipsInfo.getType())
			    || WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP_INVITE.equals(tipsInfo.getType()) ){
				AefwebimFriendVo friendVo=new AefwebimFriendVo();
				Long fromGroupId=Long.valueOf(tipsInfo.getExpand());
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
			dealWithTipsInfoExtend(acceptTipsInfo,WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_ACCEPT,tipsInfoInviteDto.getGroupId());
			//返回添加的好友json
			if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND_APPLY.equals(acceptTipsInfo.getType())){
				AefwebimFriendVo friendVo=new AefwebimFriendVo();
				friendVo.setType(WebimDefineConstant.WEBIM_INIT_USER_DATA_FRIEND);
				Long fromPersonId=tipsInfoInviteDto.getFromPersonId();
				AefsysPersonVo fromPersonVo=personService.getPersonWithExpandInfoById(fromPersonId);
				friendVo.setId(fromPersonId);
				friendVo.setAvatar(fromPersonVo.getHeadPic());
				friendVo.setUsername(fromPersonVo.getNickName());
				friendVo.setGroupid(tipsInfoInviteDto.getGroupId());
				resultList.add(friendVo);
			//返回接受邀请添加的群组json
			}else if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP_INVITE.equals(acceptTipsInfo.getType())) {
				Long fromGroupId = Long.valueOf(acceptTipsInfo.getExpand());
				AefwebimGroupVo fromGroupVo = groupService.getGroupWithExpandInfoById(fromGroupId);
				AefwebimGroupVo groupVo = new AefwebimGroupVo();
				groupVo.setType(WebimDefineConstant.WEBIM_INIT_USER_DATA_GROUP);
				groupVo.setId(fromGroupId);
				groupVo.setGroupname(fromGroupVo.getGroupName());
				groupVo.setAvatar(fromGroupVo.getAvatar());
				resultList.add(groupVo);
			}else if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP_APPLY.equals(acceptTipsInfo.getType())) {
				//返回个空的免得报错
				AefwebimGroupVo groupVo = new AefwebimGroupVo();
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
			dealWithTipsInfoExtend(refuseTipsInfo,WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE,tipsInfoInviteDto.getGroupId());
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
	private void dealWithTipsInfoExtend(AefwebimTipsInfo dealWithTipsInfo,Long mark,Long addOtherFriendGroupId){
		//首先改变已有提示信息状态
		dealWithTipsInfo.setHandleTime(DateOperate.getCurrentUtilDate());
		dealWithTipsInfo.setStatus(mark);
		updateTipsInfo(dealWithTipsInfo);
		//反馈信息的处理
		dealWithFeedBackTipsInfo(dealWithTipsInfo,mark);
		//好友关系的处理
		friendService.dealWithFriendByParam(dealWithTipsInfo,mark,addOtherFriendGroupId);
	}

	/**
	 * 接收人处理了提示信息后 ，需要新增系统提示信息，把处理结果反馈给发起人
	 * @param dealWithTipsInfo 处理的消息对象，可取其中逻辑参数
	 *
	 ****/
	private void dealWithFeedBackTipsInfo(AefwebimTipsInfo dealWithTipsInfo,Long mark){
		String tipsInfoType=dealWithTipsInfo.getType();
		AefsysPerson person=personService.getPersonById(dealWithTipsInfo.getToPersonId());
		AefwebimTipsInfo feedBackTipsInfo=new AefwebimTipsInfo();
		feedBackTipsInfo.setToPersonId(dealWithTipsInfo.getFromPersonId());
		feedBackTipsInfo.setStatus(WebimDefineConstant.WEBIM_TIPS_STATUS_UNHANDLE);
		feedBackTipsInfo.setType(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_SYSTEM_TIPS);
		//当是好友申请时再新增一条系统通知发起人已同意或已拒绝
		if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND_APPLY.equals(tipsInfoType)){
			if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_ACCEPT.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_FRIEND_ACCEPT);
			}else if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_FRIEND_REFUSE);
			}
		//当是群组申请时，反馈给发起人信息
		}else if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP_APPLY.equals(tipsInfoType)){
			if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_ACCEPT.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_GROUP_APPLY_ACCEPT);
			}else if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_GROUP_APPLY_REFUSE);
			}
		//当是群组邀请时，反馈给发起人信息
		}else if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_GROUP_INVITE.equals(tipsInfoType)){
			if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_ACCEPT.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_GROUP_INVITE_ACCEPT);
			}else if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE.equals(mark)){
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_GROUP_INVITE_REFUSE);
			}
		}
		insertTipsInfo(feedBackTipsInfo);
	}
}
