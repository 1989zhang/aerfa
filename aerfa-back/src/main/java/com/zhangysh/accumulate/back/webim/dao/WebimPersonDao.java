package com.zhangysh.accumulate.back.webim.dao;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimPerson;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 个人数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年10月27日
 */
@Mapper
public interface WebimPersonDao {
	/**
     * 根据ID查询单个个人信息
     * 
     * @param id 主键ID
     * @return 个人信息
     */
	 AefwebimPerson getPersonById(Long id);
	
	/**
     * 根据系统PersonId查询单个拓展webim个人信息
     * 
     * @param personId 系统个人id
     * @return 个人信息
     */
	 AefwebimPerson getWebimPersonBySysPersonId(Long personId);
	 
	/**
     * 根据条件查询个人列表
     * 
     * @param person 条件对象
     * @return 个人条件下的集合
     */
	 List<AefwebimPerson> listPerson(AefwebimPerson person);
	 
	/**
     * 根据主键ids查询个人
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 个人条件下的集合
     */
	 List<AefwebimPerson> listBypksPerson(String[] ids);
	 
	/**
     * 新增个人
     * 
     * @param person 个人对象信息
     * @return 新增结果条数
     */
	 int insertPerson(AefwebimPerson person);
	
	/**
     * 修改个人
     * 
     * @param person 个人信息
     * @return 修改结果条数
     */
	 int updatePerson(AefwebimPerson person);
	
	/**
     * 单个删除个人
     * 
     * @param id 个人ID
     * @return 删除结果条数
     */
	 int deletePersonById(Long id);
	
	/**
     * 批量删除个人
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deletePersonByIds(String[] ids);
	
}