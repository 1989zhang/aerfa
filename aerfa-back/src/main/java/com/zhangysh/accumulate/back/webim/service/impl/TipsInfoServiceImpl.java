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
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimGroupVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimMsgboxResultVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimMsgboxVo;
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

		/*List<AefwebimMsgboxVo> msgboxList=new ArrayList<AefwebimMsgboxVo>();
		AefwebimMsgboxVo msgboxVo1=new AefwebimMsgboxVo();
		msgboxVo1.setId(76L);
		msgboxVo1.setContent("申请添加你为好友");
		msgboxVo1.setUid(168L);
		msgboxVo1.setFrom(166488L);
		msgboxVo1.setFrom_group(0L);
		msgboxVo1.setType(1L);
		msgboxVo1.setRemark("有问题要问");
		msgboxVo1.setRead(1L);
		msgboxVo1.setTime("刚刚");
		AefwebimFriendVo friendVo1=new AefwebimFriendVo();
		friendVo1.setId(166488L);
		friendVo1.setAvatar("http://q.qlogo.cn/qqapp/101235792/B704597964F9BD0DB648292D1B09F7E8/100");
		friendVo1.setUsername("李彦宏");
		msgboxVo1.setUser(friendVo1);

		AefwebimMsgboxVo msgboxVo2=new AefwebimMsgboxVo();
		msgboxVo2.setId(75L);
		msgboxVo2.setContent("申请添加你为好友");
		msgboxVo2.setUid(168L);
		msgboxVo2.setFrom(347592L);
		msgboxVo2.setFrom_group(10L);
		msgboxVo2.setType(1L);
		msgboxVo2.setRemark("你好啊!");
		msgboxVo2.setRead(1L);
		msgboxVo2.setTime("刚刚");
		AefwebimFriendVo friendVo2=new AefwebimFriendVo();
		friendVo2.setId(347592L);
		friendVo2.setAvatar("http://q.qlogo.cn/qqapp/101235792/B78751375E0531675B1272AD994BA875/100");
		friendVo2.setUsername("麻花疼");
		msgboxVo2.setUser(friendVo2);


		AefwebimMsgboxVo msgboxVo3=new AefwebimMsgboxVo();
		msgboxVo3.setId(62L);
		msgboxVo3.setContent("雷军 拒绝了你的好友申请");
		msgboxVo3.setUid(168L);
		msgboxVo3.setTime("10天前");

		AefwebimMsgboxVo msgboxVo4=new AefwebimMsgboxVo();
		msgboxVo4.setId(60L);
		msgboxVo4.setContent("马小云 已经同意你的好友申请");
		msgboxVo4.setUid(168L);
		msgboxVo4.setTime("10天前");

		AefwebimMsgboxVo msgboxVo5=new AefwebimMsgboxVo();
		msgboxVo5.setId(61L);
		msgboxVo5.setContent("贤心 已经同意你的好友申请");
		msgboxVo5.setUid(168L);
		msgboxVo5.setTime("10天前");

		msgboxList.add(msgboxVo1);msgboxList.add(msgboxVo2);msgboxList.add(msgboxVo3);msgboxList.add(msgboxVo4);msgboxList.add(msgboxVo5);
		return JSON.toJSONString(AefwebimMsgboxResultVo.success(msgboxList,2));*/
	}

	@Override
	public boolean acceptInvite(AefwebimTipsInfo tipsInfo){

    	return true;
	}

	@Override
	public boolean refuseInvite(AefwebimTipsInfo tipsInfo){
		List<AefwebimTipsInfo> searchTipsInfoList=listTipsInfo(tipsInfo);
		if(searchTipsInfoList.size()>0){
			//首先改变已有提示信息状态
			AefwebimTipsInfo refuseTipsInfo=searchTipsInfoList.get(0);
			refuseTipsInfo.setHandleTime(DateOperate.getCurrentUtilDate());
			refuseTipsInfo.setStatus(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE);
			updateTipsInfo(refuseTipsInfo);
			//当是好友申请时再新增一条系统通知第一个人别人,已拒绝;群组先不考虑
			if(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND.equals(tipsInfo.getType())){
				AefsysPerson person=personService.getPersonById(tipsInfo.getToPersonId());
				AefwebimTipsInfo feedBackTipsInfo=new AefwebimTipsInfo();
				feedBackTipsInfo.setToPersonId(tipsInfo.getFromId());
				feedBackTipsInfo.setStatus(WebimDefineConstant.WEBIM_TIPS_STATUS_UNHANDLE);
				feedBackTipsInfo.setType(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_SYSTEM);
				feedBackTipsInfo.setContent(person.getNickName()+WebimDefineConstant.WEBIM_APPLY_TIPS_FRIEND_REFUSE);
				insertTipsInfo(feedBackTipsInfo);
			}
			//最后删除第一个人的好友申请
			AefwebimFriend searchFriend=new AefwebimFriend();
			searchFriend.setPersonId(refuseTipsInfo.getFromId());
			searchFriend.setFriendId(refuseTipsInfo.getToPersonId());
			searchFriend.setRelationStatus(WebimDefineConstant.WEBIM_FRIEND_RELATION_STATUS_WAIT);
			friendService.dealWithFriendByParam(searchFriend,WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE);
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
	
}
