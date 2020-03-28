package com.zhangysh.accumulate.back.webim.service;
import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 好友相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
public interface IFriendService {
	/**
     * 根据ID查询单个好友信息
     * 
     * @param id 好友主键ID
     * @return 好友信息
     */
	 AefwebimFriend getFriendById(Long id);
	
	/**
     * 根据条件查询好友分页列表
     * 
     * @param pageInfo 分页对象
     * @param friend 条件好友对象
     * @return 好友条件下结果集合
     */
	 BsTableDataInfo listPageFriend(BsTablePageInfo pageInfo,AefwebimFriend friend);
	
	/**
     * 根据主键ids查询好友不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 好友条件下结果集合
     */
	 List<AefwebimFriend> listBypksFriend(String ids);
	 
	/**
     * 根据条件查询好友不分页列表
     * 
     * @param friend 条件好友对象
     * @return 好友条件下结果集合
     */
	 List<AefwebimFriend> listFriend(AefwebimFriend friend);

	/**
	 * 根据人员id获取系统推荐的好友，目前逻辑如下：
	 * 推荐本单位下不是本人好友的人
	 * @param personId 人员id
	 * @return 推荐好友AefwebimFriendVo的list
	 ***/
	List<AefwebimFriendVo> listRecommendFriend(Long personId);

	/**
     * 新增好友
     * 
     * @param friend 好友对象信息
     * @return 新增结果条数
     */
	 int insertFriend(AefwebimFriend friend);
	 
	/**
     * 新增好友并生成提示信息，因为信息复杂所以返回string
     * 
     * @param operPerson 好友对象信息
     * @return 新增结果条数
     */
	 String insertFriendAndAddTipsInfo(AefwebimApplyDto apply,AefsysPerson operPerson);
	 
	/**
     * 修改好友
     * 
     * @param friend 好友修改信息
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
     * 删除好友信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteFriendByIds(String ids);
	 
	/**
	 * 获取查找信息，包括查找好友条数
	 * 
	 * @param searchDto 查询条件
	 * @return 页面显示数量
	 *****/
	 Map<String,Object> getSearchPage(AefwebimSearchDto searchDto);
	 
	/**
	 * 获取查找信息，包括查找好友详细信息
	 * 
	 * @param searchDto 查询条件
	 * @return 页面显示数量
	 *****/
	 List<AefwebimFriendVo> getSearchInfo(AefwebimSearchDto searchDto);
	 
	 /***
	  * 根据sysPerson系统人员对象转换为前台显示好友对象
	  * 
	  * @param sysPerson 系统人员对象
	  * @return 前台webim显示AefwebimFriendVo对象
	  ****/
	 AefwebimFriendVo changeToWebimFriendVoBySysPerson(AefsysPerson sysPerson);

	/**
	 * 处理不带ID参数的好友信息，用于提示信息拓展处理好友申请：
	 * 如果是同意则修改好友状态且新增一个好友，如果是拒绝则删除原来申请
	 *
	 * @param dealWithTipsInfo 消息的参数
	 * @param mark 接受好友或拒绝好友申请标记，同消息提示状态
	 * @param addOtherFriendGroupId 当接受好友时，添加互为好友的另一个所在的好友组ID
	 * @return 处理结果条数
	 */
	boolean dealWithFriendByParam(AefwebimTipsInfo dealWithTipsInfo,Long mark, Long addOtherFriendGroupId);
	 
}
