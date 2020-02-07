package com.zhangysh.accumulate.back.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
/**
 *@author 创建者：zhangysh
 */
@Mapper
public interface PersonDao {
    /**
     * 根据人员id获取人员实体
     * @param id 主键
     * @return 人员用户实体对象
     */
	AefsysPerson getPersonById(Long id);
	
    /**
     * 根据账号密码信息获取人员，不含单位对象信息，只有单位id
     * @param account 账号
     * @param password 密码
     * @return 人员用户实体对象
     */
	AefsysPerson getPersonByAccountPassword(Map<String, Object> accountPasswordMap);
	
	/**
	 *自定义拦截器分页方法，通过@Param指定参数，xml用.获取参数
	 *分页参数一定要指定为page
	 ***List<AefsysPerson> listPersons(@Param("person")AefsysPerson person,@Param("page")Pagination pageParm);
	 *分页形式获取人员列表，分页条件通过service的PageHelper设置了
	 *@param person 查询条件
	 *@return 分页条件下的人员集合
	 ***/
	List<AefsysPerson> listPerson(AefsysPerson person);
	
	/****
	 *唯一性个人账号检测，查询出个人list集合，检查账号和id
	 *因为检测不用like所以单独列出来
	 *@param person 人员条件
	 *@return 人员结果集合
	 ***/
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
     * 根据个人id，删除个人信息
     * 
     * @param id 个人对象id
     * @return 删除结果条数
     */
	 int deletePersonById(Long id);
}
