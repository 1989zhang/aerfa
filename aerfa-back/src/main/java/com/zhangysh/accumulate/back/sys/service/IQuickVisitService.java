package com.zhangysh.accumulate.back.sys.service;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysQuickVisitVo;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

/**
 * 常用功能快速访问相关服务层接口
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
public interface IQuickVisitService {
	 
	/**
     * 根据人员ID查询出快捷列表，如果列表不在授权里面会删除快捷访问
     * 
     * @param personId 个人ID
     * @return  个人的常用功能快速访问条件下结果集合
     */
	 List<AefsysQuickVisitVo> listQuickVisitByPersonId(Long personId);
	 
	/**
     * 新增常用功能快速访问
     * 
     * @param quickVisit 常用功能快速访问对象信息
     * @return 新增结果条数
     */
	 int insertQuickVisit(AefsysQuickVisit quickVisit);
	
	/**
     * 修改常用功能快速访问
     * 
     * @param quickVisit 常用功能快速访问修改信息
     * @return 修改结果条数
     */
	 int updateQuickVisit(AefsysQuickVisit quickVisit);
	
	/**
     * 单个删除常用功能快速访问
     * 
     * @param id 常用功能快速访问ID
     * @return 删除结果条数
     */
	 int deleteQuickVisitById(Long id);

	/**
	 * 删除常用功能快速访问信息
	 *
	 * @param ids 需要删除的数据ID以,拼装
	 * @return 删除结果条数
	 */
	int deleteQuickVisitByIds(String ids);
	
}
