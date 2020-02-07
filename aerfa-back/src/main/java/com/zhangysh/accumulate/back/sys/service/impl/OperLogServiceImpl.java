package com.zhangysh.accumulate.back.sys.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.back.sys.dao.OperLogDao;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOperLog;
import com.zhangysh.accumulate.back.sys.service.IOperLogService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 操作日志 服务层实现
 * 
 * @author 创建者：zhangysh
 * @date 2018年10月20日
 */
@Service
public class OperLogServiceImpl implements IOperLogService {

	private static final Logger logger = LoggerFactory.getLogger(OperLogServiceImpl.class);

	@Autowired
	private OperLogDao operLogDao;

    @Override
	public AefsysOperLog getOperLogById(Long id){
	    return operLogDao.getOperLogById(id);
	}
	
	@Override
	public BsTableDataInfo listPageOperLog(BsTablePageInfo pageInfo,AefsysOperLog operLog){
	    //pagehelper方法调用
		Page<AefsysOperLog> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		operLogDao.listOperLog(operLog);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public int insertOperLog(AefsysOperLog operLog){
	    return operLogDao.insertOperLog(operLog);
	}
	
	@Override
	public int updateOperLog(AefsysOperLog operLog){
	    return operLogDao.updateOperLog(operLog);
	}
	
	@Override
	public int deleteOperLogById(Long id){
	    logger.info("删除操作日志id为{}:"+id);
		return operLogDao.deleteOperLogById(id);
	}
	
	@Override
	public int deleteOperLogByIds(String ids){
		return operLogDao.deleteOperLogByIds(ConvertUtil.toStrArray(ids));
	}
	
}
