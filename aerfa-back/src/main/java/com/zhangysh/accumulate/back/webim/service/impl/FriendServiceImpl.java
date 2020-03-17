package com.zhangysh.accumulate.back.webim.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;
import com.zhangysh.accumulate.back.sys.base.BaseMybatisDao;
import com.zhangysh.accumulate.back.sys.service.IConfigDataService;
import com.zhangysh.accumulate.back.sys.service.IPersonService;
import com.zhangysh.accumulate.back.webim.dao.FriendDao;
import com.zhangysh.accumulate.back.webim.service.IFriendService;
import com.zhangysh.accumulate.back.webim.service.ITipsInfoService;
import com.zhangysh.accumulate.back.webim.service.IWebimPersonService;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.StringUtil;
/**
 * 好友 服务层实现
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
@Service
public class FriendServiceImpl implements IFriendService {

	@Autowired
	private IConfigDataService configDataService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IWebimPersonService webimPersonService;
	@Autowired
	private ITipsInfoService tipsInfoService;
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private BaseMybatisDao baseMybatisDao;
	

    @Override
	public AefwebimFriend getFriendById(Long id){
	    return friendDao.getFriendById(id);
	}
	
	@Override
	public BsTableDataInfo listPageFriend(BsTablePageInfo pageInfo,AefwebimFriend friend){
	    //pagehelper方法调用
		Page<AefwebimFriend> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		friendDao.listFriend(friend);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefwebimFriend> listBypksFriend(String ids){
		return friendDao.listBypksFriend(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefwebimFriend> listFriend(AefwebimFriend friend){
		return friendDao.listFriend(friend);
	}

	@Override
	public List<AefwebimFriendVo> listRecommendFriend(Long personId){
		return getTheSameOrgRecommendFriend(personId);
	}

	@Override
	public int insertFriend(AefwebimFriend friend){
	    return friendDao.insertFriend(friend);
	}
	
	@Override
	public String insertFriendAndAddTipsInfo(AefwebimApplyDto apply,AefsysPerson operPerson){
		//首先查找是否有已添加的好友，处理和未处理的都算已添加
		AefwebimFriend searchFriend=new AefwebimFriend();
		searchFriend.setFriendId(apply.getFriendId());
		searchFriend.setPersonId(apply.getPersonId());
		List<AefwebimFriend> searchFriendList=listFriend(searchFriend);
		if(searchFriendList.size()==0) {
			AefwebimFriend friend=JSON.parseObject(JSON.toJSONString(apply),AefwebimFriend.class);
			friend.setCreateTime(DateOperate.getCurrentUtilDate());
			friend.setCreateBy(operPerson.getPersonName());
			friend.setRelationStatus(WebimDefineConstant.WEBIM_FRIEND_RELATION_STATUS_WAIT);
			friendDao.insertFriend(friend);
		}
		//首先查找是否有未处理的提示信息
		AefwebimTipsInfo searchTipsInfo=new AefwebimTipsInfo();
		searchTipsInfo.setFromId(apply.getPersonId());
		searchTipsInfo.setToPersonId(apply.getFriendId());
		searchTipsInfo.setType(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND);
		List<AefwebimTipsInfo> searchTipsInfoList=tipsInfoService.listTipsInfo(searchTipsInfo);
		if(searchTipsInfoList.size()==0) {
			AefwebimTipsInfo tipsInfo=new AefwebimTipsInfo();
			tipsInfo.setFromId(apply.getPersonId());
			tipsInfo.setToPersonId(apply.getFriendId());
			tipsInfo.setType(WebimDefineConstant.WEBIM_TIPS_INFO_TYPE_FRIEND); 
			tipsInfo.setContent(WebimDefineConstant.WEBIM_APPLY_TIPS_FRIEND_ADD);
			tipsInfo.setRemark(apply.getRemark());
			tipsInfo.setStatus(WebimDefineConstant.WEBIM_TIPS_STATUS_UNHANDLE);
			tipsInfo.setCreateTime(DateOperate.getCurrentUtilDate());
			tipsInfo.setCreateBy(operPerson.getPersonName());
			tipsInfoService.insertTipsInfo(tipsInfo);
		}
		
		//返回信息逻辑：都有信息就返回重复申请失败，有一个新增数据就提示发送成功
		AefsysPerson friendPerson=personService.getPersonById(apply.getFriendId());
		String retTrueStr=StringUtil.format(WebimDefineConstant.WEBIM_APPLY_TIPS_CONFIRM, friendPerson.getNickName());
		String falseTrueStr=StringUtil.format(WebimDefineConstant.WEBIM_APPLY_TIPS_REPEAT, friendPerson.getNickName());
		if(searchFriendList.size()>0&&searchTipsInfoList.size()>0) {
			return JSON.toJSONString(ResultVo.error(CodeMsgConstant.SYS_DATA_OPERATE_ERROR.fillArgs(falseTrueStr)));
		}
		return JSON.toJSONString(ResultVo.success(retTrueStr));
	}
	
	@Override
	public int updateFriend(AefwebimFriend friend){
	    return friendDao.updateFriend(friend);
	}
	
	@Override
	public int deleteFriendById(Long id){
		return friendDao.deleteFriendById(id);
	}
	
	@Override
	public int deleteFriendByIds(String ids){
		return friendDao.deleteFriendByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	public Map<String,Object> getSearchPage(AefwebimSearchDto searchDto){
		Map<String,Object> retPageMap=new HashMap<String,Object>();
		
		//查询id匹配的人
		Long searchPersonId=ConvertUtil.toLong(searchDto.getValue(), 0L);
		if(searchPersonId>0) {
			//personService.getPersonById(searchPersonId);
			retPageMap.put(WebimDefineConstant.WEBIM_SEARCH_PAGE_COUNT, 1L);
		}else {//查询nickName匹配的人
			AefsysPerson searchPerson=new AefsysPerson();
			searchPerson.setNickName(searchDto.getValue());
			List<AefsysPerson> personList=personService.listPerson(searchPerson);
			retPageMap.put(WebimDefineConstant.WEBIM_SEARCH_PAGE_COUNT, personList.size());
		}
		retPageMap.put(WebimDefineConstant.WEBIM_SEARCH_PAGE_LIMIT, WebimDefineConstant.WEBIM_SEARCH_PAGE_LIMIT_NUMBER);
		return retPageMap;
	}
	
	@Override
	public List<AefwebimFriendVo> getSearchInfo(AefwebimSearchDto searchDto){
		List<AefwebimFriendVo> retFriendVoList=new ArrayList<AefwebimFriendVo>();
		Long searchPersonId=ConvertUtil.toLong(searchDto.getValue(), 0L);
		if(searchPersonId>0) {
			AefsysPerson sysPerson=personService.getPersonById(searchPersonId);
			AefwebimFriendVo friendVo=changeToWebimFriendVoBySysPerson(sysPerson);
			retFriendVoList.add(friendVo);
		}else {//查询nickName匹配的人
			AefsysPerson searchPerson=new AefsysPerson();
			searchPerson.setNickName(searchDto.getValue());
			List<AefsysPerson> personList=personService.listPerson(searchPerson);
			for(AefsysPerson sysPerson:personList) {
				retFriendVoList.add(changeToWebimFriendVoBySysPerson(sysPerson));
			}
		}
		return retFriendVoList;
	}
	
	@Override
	public AefwebimFriendVo changeToWebimFriendVoBySysPerson(AefsysPerson sysPerson) {
		//头像前缀配置路径
		AefsysConfigData picIpAddressConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_SYS_PIC_IP_ADDRESS);
        //拓展对象信息
		AefwebimPerson friendWebimPerson=webimPersonService.getWebimPersonBySysPersonId(sysPerson.getId());
		
		AefwebimFriendVo retWebimFriendVo=new AefwebimFriendVo();
		retWebimFriendVo.setId(sysPerson.getId());
		retWebimFriendVo.setUsername(sysPerson.getNickName()==null?sysPerson.getPersonName():sysPerson.getNickName());
		//从未登录的情况和签名为空的情况
		retWebimFriendVo.setSign(friendWebimPerson==null?"":friendWebimPerson.getSignature());
		if(StringUtil.isNotEmpty(sysPerson.getHeadPic())) {
			retWebimFriendVo.setAvatar(picIpAddressConfigData.getDataValue()+sysPerson.getHeadPic());	
		}else {
			retWebimFriendVo.setAvatar(SysDefineConstant.SYS_PERSON_DEFAULT_HEAD_PIC);
		}
		return retWebimFriendVo;
	}

	@Override
	public boolean dealWithFriendByParam(AefwebimFriend friend,Long mark){
		List<AefwebimFriend> searchFriendList=listFriend(friend);
    	if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_ACCEPT.equals(mark)){
			//同意的话修改好友
			for(int i=0;i<searchFriendList.size();i++){
				AefwebimFriend updateFriend=searchFriendList.get(i);
				updateFriend.setRelationStatus(WebimDefineConstant.WEBIM_FRIEND_RELATION_STATUS_CONFIRM);
				updateFriend.setUpdateTime(DateOperate.getCurrentUtilDate());
				updateFriend(updateFriend);
			}
		}else if(WebimDefineConstant.WEBIM_TIPS_STATUS_HANDLE_REFUSE.equals(mark)){
			//拒绝的话删除好友
			for(int i=0;i<searchFriendList.size();i++){
				deleteFriendById(searchFriendList.get(i).getId());
			}
		}
		return true;
	}


	/**
	 * 获取同一个单位下，不在好友列表的系统推荐好友列表
	 * @param personId 主人员ID
	 ***/
	private List<AefwebimFriendVo> getTheSameOrgRecommendFriend(Long personId){
		//找到人员所在单位下的人员
		AefsysPerson person=personService.getPersonById(personId);
		AefsysPerson searchPerson=new AefsysPerson();
		searchPerson.setOrgId(person.getOrgId());
		List<AefsysPerson> myOrgPersonList=personService.listPerson(searchPerson);
		//找到已添加过的好友
		AefwebimFriend searchFriend=new AefwebimFriend();
		searchFriend.setPersonId(personId);
		List<AefwebimFriend> friendList=friendDao.listFriend(searchFriend);
		//开始判断哪些需要返回的
		List<AefwebimFriendVo> retWebimFriendList=new ArrayList<AefwebimFriendVo>();
        for(AefsysPerson sysPerson:myOrgPersonList) {
        	boolean canAddToRet=true;
        	//已添加好友且排除自己
        	for(AefwebimFriend friendWebim:friendList) {
        		if(sysPerson.getId().equals(friendWebim.getFriendId())||personId.equals(sysPerson.getId())) {
        			canAddToRet=false;	
        		}
        	}
        	if(canAddToRet) {
        		retWebimFriendList.add(changeToWebimFriendVoBySysPerson(sysPerson));
        	}
        }
		return retWebimFriendList;
	}
}
