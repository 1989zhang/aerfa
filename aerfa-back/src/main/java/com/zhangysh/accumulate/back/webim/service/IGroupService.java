package com.zhangysh.accumulate.back.webim.service;
import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimGroup;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimApplyDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimGroupVo;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 群组相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
public interface IGroupService {
	/**
     * 根据ID查询单个群组信息
     * 
     * @param id 群组主键ID
     * @return 群组信息
     */
	 AefwebimGroup getGroupById(Long id);

	/**
	 * 根据ID查询单个群组信息,拓展信息包括:
	 * 处理默认图片
	 *
	 * @param id 群组主键ID
	 * @return 群组信息
	 */
	AefwebimGroupVo getGroupWithExpandInfoById(Long id);
	
	/**
     * 根据条件查询群组分页列表
     * 
     * @param pageInfo 分页对象
     * @param group 条件群组对象
     * @return 群组条件下结果集合
     */
	 BsTableDataInfo listPageGroup(BsTablePageInfo pageInfo,AefwebimGroup group);
	
	/**
     * 根据主键ids查询群组不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 群组条件下结果集合
     */
	 List<AefwebimGroup> listBypksGroup(String ids);
	 
	/**
     * 根据条件查询群组不分页列表
     * 
     * @param group 条件群组对象
     * @return 群组条件下结果集合
     */
	 List<AefwebimGroupVo> listGroup(AefwebimGroup group);
	 
	/**
     * 新增群组,并返回新增对象和拓展属性
     * 
     * @param group 群组对象信息
     * @return 新增结果条数
     */
	AefwebimGroupVo insertGroup(AefwebimGroup group);
	
	/**
     * 修改群组
     * 
     * @param group 群组修改信息
     * @return 修改结果条数
     */
	 int updateGroup(AefwebimGroup group);
	
	/**
     * 单个删除群组
     * 
     * @param id 群组ID
     * @return 删除结果条数
     */
	 int deleteGroupById(Long id);
	 	
	/**
     * 删除群组信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteGroupByIds(String ids);
	
	/**
	 * 获取查找信息，包括查找群组条数
	 * 
	 * @param searchDto 查询条件
	 * @return 页面显示数量
	 *****/
	 Map<String,Object> getSearchPage(AefwebimSearchDto searchDto);
	 
	/**
	 * 获取查找信息，包括查找群组详细信息
	 * 
	 * @param searchDto 查询条件
	 * @return 页面显示数量
	 *****/
	 List<AefwebimGroupVo> getSearchInfo(AefwebimSearchDto searchDto);
	 
	/**
     * 保存新增的加群组申请，因为信息复杂所以返回string
     * 
     * @param apply 申请对象信息
     * @return 新增结果条数
     */
	 String insertGroupFriendAndAddTipsInfo(AefwebimApplyDto apply,AefsysPerson operPerson);

	 /**
	  * 获取人员需要展示的普通群组包括自己创建的和加入的
	  * @param personId 人员ID
	  ***/
	 List<AefwebimGroupVo> getNormalGroupByPerson(Long personId);
}
