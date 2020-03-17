package com.zhangysh.accumulate.back.webim.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimTipsInfoDto;

/**
 * 提示消息相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
public interface ITipsInfoService {

	/****
	 * 分页显示人员的消息提醒列表
	 * @param tipsInfoDto 查询条件
	 ***/
	String getWebimMsgbox(AefwebimTipsInfoDto tipsInfoDto);

	/**
	 * 处理提示消息,接收邀请信息.接受逻辑很复杂,详见代码逻辑
	 * @param tipsInfo 保存的对象
	 ****/
	boolean acceptInvite(AefwebimTipsInfo tipsInfo);

	/**
	 * 处理提示消息,拒绝邀请信息.拒绝逻辑很复杂,详见代码逻辑
	 * @param tipsInfo 保存的对象
	 ****/
	boolean refuseInvite(AefwebimTipsInfo tipsInfo);

	/**
     * 根据ID查询单个提示消息信息
     * 
     * @param id 提示消息主键ID
     * @return 提示消息信息
     */
	 AefwebimTipsInfo getTipsInfoById(Long id);
	
	/**
     * 根据条件查询提示消息分页列表
     * 
     * @param pageInfo 分页对象
     * @param tipsInfo 条件提示消息对象
     * @return 提示消息条件下结果集合
     */
	 BsTableDataInfo listPageTipsInfo(BsTablePageInfo pageInfo,AefwebimTipsInfo tipsInfo);
	
	/**
     * 根据主键ids查询提示消息不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 提示消息条件下结果集合
     */
	 List<AefwebimTipsInfo> listBypksTipsInfo(String ids);
	 
	/**
     * 根据条件查询提示消息不分页列表
     * 
     * @param tipsInfo 条件提示消息对象
     * @return 提示消息条件下结果集合
     */
	 List<AefwebimTipsInfo> listTipsInfo(AefwebimTipsInfo tipsInfo);
	 
	/**
     * 新增提示消息
     * 
     * @param tipsInfo 提示消息对象信息
     * @return 新增结果条数
     */
	 int insertTipsInfo(AefwebimTipsInfo tipsInfo);
	
	/**
     * 修改提示消息
     * 
     * @param tipsInfo 提示消息修改信息
     * @return 修改结果条数
     */
	 int updateTipsInfo(AefwebimTipsInfo tipsInfo);
	
	/**
     * 单个删除提示消息
     * 
     * @param id 提示消息ID
     * @return 删除结果条数
     */
	 int deleteTipsInfoById(Long id);
	 	
	/**
     * 删除提示消息信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteTipsInfoByIds(String ids);
	
}
