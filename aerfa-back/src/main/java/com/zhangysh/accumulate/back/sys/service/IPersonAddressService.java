package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 个人联系地址相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
public interface IPersonAddressService {
	/**
     * 根据ID查询单个个人联系地址信息
     * 
     * @param id 个人联系地址主键ID
     * @return 个人联系地址信息
     */
	 AefsysPersonAddress getPersonAddressById(Long id);
	
	/**
     * 根据个人id获取个人默认的联系地址信息
     * 
     * @param personId 个人ID
     * @return 默认的联系地址信息
     */
	 AefsysPersonAddress getPersonAddressByPersonId(Long personId);
		 
	/**
     * 根据条件查询个人联系地址分页列表
     * 
     * @param pageInfo 分页对象
     * @param personAddress 条件个人联系地址对象
     * @return 个人联系地址条件下结果集合
     */
	 BsTableDataInfo listPagePersonAddress(BsTablePageInfo pageInfo,AefsysPersonAddress personAddress);
	
	/**
     * 根据条件查询个人联系地址不分页列表
     * 
     * @param personAddress 条件个人联系地址对象
     * @return 个人联系地址条件下结果集合
     */
	 List<AefsysPersonAddress> listPersonAddress(AefsysPersonAddress personAddress);
	 
	/**
     * 新增个人联系地址
     * 
     * @param personAddress 个人联系地址对象信息
     * @return 新增结果条数
     */
	 int insertPersonAddress(AefsysPersonAddress personAddress);
	
	/**
     * 修改个人联系地址
     * 
     * @param personAddress 个人联系地址修改信息
     * @return 修改结果条数
     */
	 int updatePersonAddress(AefsysPersonAddress personAddress);
	
	/**
     * 单个删除个人联系地址
     * 
     * @param id 个人联系地址ID
     * @return 删除结果条数
     */
	 int deletePersonAddressById(Long id);
	 	
	/**
     * 删除个人联系地址信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deletePersonAddressByIds(String ids);
	
	/***
	 * 个人联系地址设置为默认地址,先找出个人对应的联系地址为默认的对象设置为非默认，再把当前对象设置为默认。
	 * @param aerfatoken token对象
	 * @param id 要设置的id参数
	 ****/
	 boolean setDefault(Long id);
}
