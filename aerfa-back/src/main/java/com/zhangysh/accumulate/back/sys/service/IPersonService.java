package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
/*****
 * 人员相关service接口
 * @author zhangysh
 * @date 2018年9月12日
 *****/
public interface IPersonService {
	
    /**
     * 根据人员id获取人员实体，不含单位对象信息，只有单位id
     * @param personId 人员主键
     * @return 人员用户实体对象
     */
	AefsysPerson getPersonById(Long personId);
	
    /**
     * 根据账号密码信息获取人员，不含单位对象信息，只有单位id
     * @param account 账号
     * @param password 密码
     * @return 人员用户实体对象
     */
	AefsysPerson getPersonByAccountPassword(String account,String password);
	
    /**
     * 根据人员id获取人员实体，含单位对象名称信息
     * @param personId 人员主键
     * @return 人员用户实体对象
     */
	AefsysPersonVo getPersonWithOrgNameById(Long personId);

	/****
	 *获取分页形式的人员列表，为了效率里面不含单位对象信息，只有单位id
	 *@param pageInfo 分页排序条件
	 *@param person 查询条件
	 *@return 查询到的table结果
	 ***/
	BsTableDataInfo listPagePerson(BsTablePageInfo pageInfo,AefsysPerson person);
	
	/****
	 *普通模糊查询条件下的人员list集合，未分页排序等 
	 *@param person 查询条件 
	 ****/
	List<AefsysPerson> listPerson(AefsysPerson person);
	
	/****
	 *唯一性个人账号检测，查询出个人list集合，未分页排序等 
	 *@param person 查询条件 
	 ****/
	List<AefsysPerson> checkAccountUnique(AefsysPerson person);
	
	/**
     * 新增个人信息
     * 
     * @param person 个人对象信息
     * @return 新增结果条数
     */
	 int insertPerson(AefsysPerson person);
	 
	/**
     * 修改个人信息
     * 
     * @param person 个人对象信息
     * @return 修改结果条数
     */
	 int updatePerson(AefsysPerson person); 
	 
	/**
	 * 删除个人对象，可以删除多个.
     * 
     * @param ids 要删除的个人ids,中间英文,隔开
     * @return 删除结果条数
     */
	 int deletePersonByIds(String ids);
	 
	/**
	 * 修改本人密码时，先验证当前密码是否正确。
	 * 验证逻辑：查找账号密码在数据库有没有这个人来判断，不是人找出来比对密码。
	 * @param account 验证信息的账号
	 * @param oldPassword 验证信息的密码
	 * @return 是否验证通过，旧密码匹配
	 ****/
	 boolean checkOldPassword(String account,String oldPassword);
}
