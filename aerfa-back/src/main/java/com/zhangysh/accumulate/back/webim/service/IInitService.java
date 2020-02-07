package com.zhangysh.accumulate.back.webim.service;

import java.util.Map;

/*****
 * 获取webim初始化信息
 * @author zhangysh
 * @date 2019年10月8日
 *****/
public interface IInitService {

	/**
	 * 获取初始显示信息:包括个人信息，好友组，群组信息,personid和friendid对应好友关系
	 * @param personId 取sessionid实际为个人person的id
	 * @return 返回信息json
	 ****/
	Map<String,Object> getUserData(Long personId);
	
	/**
	 * 获取群组下面的人员列表信息,personid为所属成员
	 * @param id 群组id
	 * @return 返回信息json
	 ****/
	Map<String,Object> getGroupMembers(Long groupId);
	
}
