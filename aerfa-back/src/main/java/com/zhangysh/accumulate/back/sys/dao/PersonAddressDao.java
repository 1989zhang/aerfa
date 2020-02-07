package com.zhangysh.accumulate.back.sys.dao;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 个人联系地址数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
@Mapper
public interface PersonAddressDao {
	/**
     * 根据ID查询单个个人联系地址信息
     * 
     * @param id 主键ID
     * @return 个人联系地址信息
     */
	 AefsysPersonAddress getPersonAddressById(Long id);
	
	/**
     * 根据条件查询个人联系地址列表
     * 
     * @param personAddress 条件对象
     * @return 个人联系地址条件下的集合
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
     * @param personAddress 个人联系地址信息
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
     * 批量删除个人联系地址
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deletePersonAddressByIds(String[] ids);
	
}