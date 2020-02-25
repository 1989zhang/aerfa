package com.zhangysh.accumulate.back.comm.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoContent;
import com.zhangysh.accumulate.back.comm.dao.InfoContentDao;
import com.zhangysh.accumulate.back.comm.service.IInfoContentService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 发布内容 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月24日
 */
@Service
public class InfoContentServiceImpl implements IInfoContentService {

	@Autowired
	private InfoContentDao infoContentDao;

    @Override
	public AefcommInfoContent getInfoContentById(Long id){
	    return infoContentDao.getInfoContentById(id);
	}

	@Override
	public AefcommInfoContent getInfoContentByPublishId(Long publishId){
		return infoContentDao.getInfoContentByPublishId(publishId);
	}

	@Override
	public List<AefcommInfoContent> listBypksInfoContent(String ids){
		return infoContentDao.listBypksInfoContent(ConvertUtil.toStrArray(ids));
	}

	@Override
	public int insertInfoContent(AefcommInfoContent infoContent){
	    return infoContentDao.insertInfoContent(infoContent);
	}
	
	@Override
	public int updateInfoContent(AefcommInfoContent infoContent){
	    return infoContentDao.updateInfoContent(infoContent);
	}
	
	@Override
	public int deleteInfoContentById(Long id){
		return infoContentDao.deleteInfoContentById(id);
	}

	@Override
	public int deleteInfoContentByPublishId(Long publishId){
		return infoContentDao.deleteInfoContentByPublishId(publishId);
	}

	@Override
	public int deleteInfoContentByIds(String ids){
		return infoContentDao.deleteInfoContentByIds(ConvertUtil.toStrArray(ids));
	}
	
}
