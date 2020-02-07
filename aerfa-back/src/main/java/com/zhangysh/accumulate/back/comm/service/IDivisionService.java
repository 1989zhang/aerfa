package com.zhangysh.accumulate.back.comm.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;

/**
 * 行政区划相关服务层接口
 * 
 * @author zhangysh
 * @date 2019年05月25日
 */
public interface IDivisionService {
	/**
     * 根据ID查询单个行政区划信息
     * 
     * @param id 行政区划主键ID
     * @return 行政区划信息
     */
	 AefcommDivision getDivisionById(Long id);
	
	/**
     * 根据条件查询行政区划分页列表
     * 
     * @param pageInfo 分页对象
     * @param division 条件行政区划对象
     * @return 行政区划条件下结果集合
     */
	 BsTableDataInfo listPageDivision(BsTablePageInfo pageInfo,AefcommDivision division);
	
	/**
     * 根据条件查询行政区划不分页列表
     * 
     * @param division 条件行政区划对象
     * @return 行政区划条件下结果集合
     */
	 List<AefcommDivision> listDivision(AefcommDivision division);
	 
	/****
	 * 行政区划区划代码唯一性检测，查询出行政区划list集合，未分页排序等 
	 * @param division 查询条件,内含区划代码属性和id,id为了排除自己
	 ****/
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
     * @param division 行政区划修改信息
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
     * 删除行政区划信息
     * 
     * @param ids 需要删除的数据ID以,拼装
     * @return 删除结果条数
     */
	 int deleteDivisionByIds(String ids);
	
	/****
	 * 异步加载展示行政区划的树形结构
	 * */
	 List<BsTreeNode> listDivisionWithTreeStructure(Long parentId);
	 
	 /****
	  * citypicker获取的区划json数据，由于只有此插件用，其他不通用，且只查找省市县三级。
	  * 且设置到redis中,由于数据相当较大，获取也是当时获取，所以存redis有效期1天。
	  * 格式参照ui下src\main\resources\static\sys\myplugins\city-picker\js\city-picker.data.js
	  ***/
	 String getCityPickerDivisionDataAloneFromRedis();
	 
}
