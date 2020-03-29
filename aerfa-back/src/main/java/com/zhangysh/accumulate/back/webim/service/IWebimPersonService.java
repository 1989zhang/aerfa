package com.zhangysh.accumulate.back.webim.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimPerson;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimPersonVo;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 个人相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年10月27日
 */
public interface IWebimPersonService {
	/**
     * 根据ID查询单个拓展webim个人信息
     * 
     * @param id 个人主键ID
     * @return 个人信息
     */
	 AefwebimPerson getWebimPersonById(Long id);
	
	/**
     * 根据系统PersonId查询单个拓展webim个人信息
     * 
     * @param sysPersonId 系统个人ID
     * @return 个人信息
     */
	 AefwebimPerson getWebimPersonBySysPersonId(Long sysPersonId);
	 
	/**
     * 根据条件查询个人分页列表
     * 
     * @param pageInfo 分页对象
     * @param person 条件个人对象
     * @return 个人条件下结果集合
     */
	 BsTableDataInfo listPagePerson(BsTablePageInfo pageInfo,AefwebimPerson person);
	
	/**
     * 根据主键ids查询个人不分页列表
     * 
     * @param ids 需要查询的数据ID以,拼装
     * @return 个人条件下结果集合
     */
	 List<AefwebimPerson> listBypksPerson(String ids);
	 
	/**
     * 根据条件查询个人不分页列表
     * 
     * @param person 条件个人对象
     * @return 个人条件下结果集合
     */
	 List<AefwebimPerson> listPerson(AefwebimPerson person);
	 
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
     * @param person 个人修改信息
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
     * 删除个人信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deletePersonByIds(String ids);
	
	 /***
	  * 获取个人拓展信息
	  * @param searchDto 查询条件，value为查询id
	  ***/
	 AefwebimPersonVo getInformation(AefwebimSearchDto searchDto);

	/***
	 * 保存修改的我的个人信息
	 * @param operPerson 操作者
	 * @param webimPersonVo 保存的对象
	 **/
    boolean saveMyInformation(AefsysPerson operPerson,AefwebimPersonVo webimPersonVo);
}
