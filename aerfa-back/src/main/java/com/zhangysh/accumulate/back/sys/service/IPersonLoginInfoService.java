package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonLoginInfo;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 个人登录相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年05月20日
 */
public interface IPersonLoginInfoService {
	/**
     * 根据ID查询单个个人登录信息
     * 
     * @param id 个人登录主键ID
     * @return 个人登录信息
     */
	 AefsysPersonLoginInfo getPersonLoginInfoById(Long id);
	 
	/**
     * 根据个人ID查询单个个人登录信息
     * 
     * @param personId 个人人员ID
     * @return 个人登录信息
     */
	 AefsysPersonLoginInfo getPersonLoginInfoByPersonId(Long personId);
	
	/**
     * 根据条件查询个人登录分页列表
     * 
     * @param pageInfo 分页对象
     * @param personLoginInfo 条件个人登录对象
     * @return 个人登录条件下结果集合
     */
	 BsTableDataInfo listPagePersonLoginInfo(BsTablePageInfo pageInfo,AefsysPersonLoginInfo personLoginInfo);
	
	/**
     * 根据条件查询个人登录不分页列表
     * 
     * @param personLoginInfo 条件个人登录对象
     * @return 个人登录条件下结果集合
     */
	 List<AefsysPersonLoginInfo> listPersonLoginInfo(AefsysPersonLoginInfo personLoginInfo);
	 
	/**
     * 新增个人登录
     * 
     * @param personLoginInfo 个人登录对象信息
     * @return 新增结果条数
     */
	 int insertPersonLoginInfo(AefsysPersonLoginInfo personLoginInfo);
	
	/**
     * 修改个人登录
     * 
     * @param personLoginInfo 个人登录修改信息
     * @return 修改结果条数
     */
	 int updatePersonLoginInfo(AefsysPersonLoginInfo personLoginInfo);
	
	/**
     * 单个删除个人登录
     * 
     * @param id 个人登录ID
     * @return 删除结果条数
     */
	 int deletePersonLoginInfoById(Long id);
	 	
	/**
     * 删除个人登录信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deletePersonLoginInfoByIds(String ids);
	
}
