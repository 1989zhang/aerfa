package com.zhangysh.accumulate.back.comm.dao;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;	

/**
 * 行政区划数据层对应接口
 * 
 * @author zhangysh
 * @date 2019年05月25日
 */
@Mapper
public interface DivisionDao {
	/**
     * 根据ID查询单个行政区划信息
     * 
     * @param id 主键ID
     * @return 行政区划信息
     */
	 AefcommDivision getDivisionById(Long id);
	
	/**
     * 根据条件查询行政区划列表
     * 
     * @param division 条件对象
     * @return 行政区划条件下的集合
     */
	 List<AefcommDivision> listDivision(AefcommDivision division);
	 
	/****
	 * 行政区划区划代码唯一性检测，查询出行政区划list集合，检查区划代码和id
	 * 因为检测不用like所以单独列出来
	 * @param division 查询条件,内含区划代码属性和id,id为了排除自己
	 * @return 行政区划结果集合
	 ***/
	List<AefcommDivision> checkCodeUnique(AefcommDivision division);
	/**
     * 新增行政区划
     * 
     * @param division 行政区划对象信息
     * @return 新增结果条数
     */
	 int insertDivision(AefcommDivision division);
	
	/**
     * 修改行政区划
     * 
     * @param division 行政区划信息
     * @return 修改结果条数
     */
	 int updateDivision(AefcommDivision division);
	
	/**
     * 单个删除行政区划
     * 
     * @param id 行政区划ID
     * @return 删除结果条数
     */
	 int deleteDivisionById(Long id);
	
	/**
     * 批量删除行政区划
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDivisionByIds(String[] ids);
	
}