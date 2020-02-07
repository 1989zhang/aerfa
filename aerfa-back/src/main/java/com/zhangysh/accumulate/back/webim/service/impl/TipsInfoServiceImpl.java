package com.zhangysh.accumulate.back.webim.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimTipsInfo;
import com.zhangysh.accumulate.back.webim.dao.TipsInfoDao;
import com.zhangysh.accumulate.back.webim.service.ITipsInfoService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 提示消息 服务层实现
 * 
 * @author zhangysh
 * @date 2019年10月22日
 */
@Service
public class TipsInfoServiceImpl implements ITipsInfoService {

	@Autowired
	private TipsInfoDao tipsInfoDao;

    @Override
	public AefwebimTipsInfo getTipsInfoById(Long id){
	    return tipsInfoDao.getTipsInfoById(id);
	}
	
	@Override
	public BsTableDataInfo listPageTipsInfo(BsTablePageInfo pageInfo,AefwebimTipsInfo tipsInfo){
	    //pagehelper方法调用
		Page<AefwebimTipsInfo> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		tipsInfoDao.listTipsInfo(tipsInfo);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefwebimTipsInfo> listBypksTipsInfo(String ids){
		return tipsInfoDao.listBypksTipsInfo(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefwebimTipsInfo> listTipsInfo(AefwebimTipsInfo tipsInfo){
		return tipsInfoDao.listTipsInfo(tipsInfo);
	}

	@Override
	public int insertTipsInfo(AefwebimTipsInfo tipsInfo){
	    return tipsInfoDao.insertTipsInfo(tipsInfo);
	}
	
	@Override
	public int updateTipsInfo(AefwebimTipsInfo tipsInfo){
	    return tipsInfoDao.updateTipsInfo(tipsInfo);
	}
	
	@Override
	public int deleteTipsInfoById(Long id){
		return tipsInfoDao.deleteTipsInfoById(id);
	}
	
	@Override
	public int deleteTipsInfoByIds(String ids){
		return tipsInfoDao.deleteTipsInfoByIds(ConvertUtil.toStrArray(ids));
	}
	
}
