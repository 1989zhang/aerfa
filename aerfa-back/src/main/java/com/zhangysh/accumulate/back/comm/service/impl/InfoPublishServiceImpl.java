package com.zhangysh.accumulate.back.comm.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.back.comm.dao.InfoPublishDao;
import com.zhangysh.accumulate.back.comm.service.IInfoPublishService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 发布 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Service
public class InfoPublishServiceImpl implements IInfoPublishService {

	@Autowired
	private InfoPublishDao infoPublishDao;

    @Override
	public AefcommInfoPublish getInfoPublishById(Long id){
	    return infoPublishDao.getInfoPublishById(id);
	}
	
	@Override
	public BsTableDataInfo listPageInfoPublish(BsTablePageInfo pageInfo,AefcommInfoPublish infoPublish){
	    //pagehelper方法调用
		Page<AefcommInfoPublish> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		infoPublishDao.listInfoPublish(infoPublish);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefcommInfoPublish> listBypksInfoPublish(String ids){
		return infoPublishDao.listBypksInfoPublish(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefcommInfoPublish> listInfoPublish(AefcommInfoPublish infoPublish){
		return infoPublishDao.listInfoPublish(infoPublish);
	}

	@Override
	public int insertInfoPublish(AefcommInfoPublish infoPublish){
	    return infoPublishDao.insertInfoPublish(infoPublish);
	}
	
	@Override
	public int updateInfoPublish(AefcommInfoPublish infoPublish){
	    return infoPublishDao.updateInfoPublish(infoPublish);
	}
	
	@Override
	public int deleteInfoPublishById(Long id){
		return infoPublishDao.deleteInfoPublishById(id);
	}
	
	@Override
	public int deleteInfoPublishByIds(String ids){
		return infoPublishDao.deleteInfoPublishByIds(ConvertUtil.toStrArray(ids));
	}
	
}
