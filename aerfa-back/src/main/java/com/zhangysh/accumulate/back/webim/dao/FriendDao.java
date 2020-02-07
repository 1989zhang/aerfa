package com.zhangysh.accumulate.back.webim.dao;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 好友数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
@Mapper
public interface FriendDao {
	/**
     * 根据ID查询单个好友信息
     * 
     * @param id 主键ID
     * @return 好友信息
     */
	 AefwebimFriend getFriendById(Long id);
	
	/**
     * 根据条件查询好友列表
     * 
     * @param friend 条件对象
     * @return 好友条件下的集合
     */
	 List<AefwebimFriend> listFriend(AefwebimFriend friend);
	 
	/**
     * 根据主键ids查询好友
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 好友条件下的集合
     */
	 List<AefwebimFriend> listBypksFriend(String[] ids);
	 
	/**
     * 新增好友
     * 
     * @param friend 好友对象信息
     * @return 新增结果条数
     */
	 int insertFriend(AefwebimFriend friend);
	
	/**
     * 修改好友
     * 
     * @param friend 好友信息
     * @return 修改结果条数
     */
	 int updateFriend(AefwebimFriend friend);
	
	/**
     * 单个删除好友
     * 
     * @param id 好友ID
     * @return 删除结果条数
     */
	 int deleteFriendById(Long id);
	
	/**
     * 批量删除好友
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteFriendByIds(String[] ids);
	
}