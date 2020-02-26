package com.zhangysh.accumulate.back.comm.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.zhangysh.accumulate.back.comm.service.IInfoContentService;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoContent;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommInfoPublish;
import com.zhangysh.accumulate.back.comm.dao.InfoPublishDao;
import com.zhangysh.accumulate.back.comm.service.IInfoPublishService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.pojo.comm.viewobj.AefcommInfoPublishVo;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import org.springframework.transaction.annotation.Transactional;

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
	@Autowired
	private IInfoContentService infoContentService;

    @Override
	public AefcommInfoPublishVo getInfoPublishById(Long id){
		AefcommInfoPublish infoPublish=infoPublishDao.getInfoPublishById(id);
		AefcommInfoContent infoContent=infoContentService.getInfoContentByPublishId(id);
		AefcommInfoPublishVo retPublishVo=new AefcommInfoPublishVo();
		BeanUtils.copyProperties(infoPublish,retPublishVo);
		retPublishVo.setContent(infoContent.getContent());
	    return retPublishVo;
	}

	@Override
	public List<AefcommInfoPublishVo> listBypksInfoPublish(String ids){
		List<AefcommInfoPublish> infoPublishList=infoPublishDao.listBypksInfoPublish(ConvertUtil.toStrArray(ids));
		List<AefcommInfoPublishVo> retInfoPublishVoList=new ArrayList<AefcommInfoPublishVo>();
		for(AefcommInfoPublish infoPublish:infoPublishList){
			AefcommInfoPublishVo retPublishVo=new AefcommInfoPublishVo();
			BeanUtils.copyProperties(infoPublish,retPublishVo);
			retPublishVo.setContent(infoContentService.getInfoContentByPublishId(infoPublish.getId()).getContent());
			retInfoPublishVoList.add(retPublishVo);
		}
		return retInfoPublishVoList;
	}
	
	@Override
	public BsTableDataInfo listPageInfoPublish(BsTablePageInfo pageInfo,AefcommInfoPublish infoPublish){
	    //pagehelper方法调用
		Page<AefcommInfoPublish> page;
		//当没排序条件就执行默认排序
		if(StringUtil.isNotEmpty(pageInfo.getOrderBy())){
			page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		}else{
			page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
			infoPublish.getParams().put(MarkConstant.SORT_CONDITION,"top desc,order_no desc,pub_date desc");
		}
		infoPublishDao.listInfoPublish(infoPublish);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefcommInfoPublish> listInfoPublish(AefcommInfoPublish infoPublish){
		infoPublish.getParams().put(MarkConstant.SORT_CONDITION,"top desc,order_no desc,pub_date desc");
		return infoPublishDao.listInfoPublish(infoPublish);
	}

	@Override
	@Transactional
	public int insertInfoPublish(AefcommInfoPublishVo infoPublishVo){
		AefcommInfoPublish infoPublish=new AefcommInfoPublish();
		BeanUtils.copyProperties(infoPublishVo,infoPublish);
		//新增没有排序号的时候默认给个排序号
		if(StringUtil.isNull(infoPublish.getOrderNo())){
			infoPublish.setOrderNo(infoPublishDao.getAllRowCountByParam(null)+1L);
		}
		int insertRows=infoPublishDao.insertInfoPublish(infoPublish);
		//插入信息发布内容
		AefcommInfoContent infoContent=new AefcommInfoContent();
		infoContent.setContent(infoPublishVo.getContent());
		infoContent.setPublishId(infoPublish.getId());
		infoContentService.insertInfoContent(infoContent);
	    return insertRows;
	}
	
	@Override
	@Transactional
	public int updateInfoPublish(AefcommInfoPublishVo infoPublishVo){
		AefcommInfoPublish infoPublish=new AefcommInfoPublish();
		BeanUtils.copyProperties(infoPublishVo,infoPublish);
		int updateRows=infoPublishDao.updateInfoPublish(infoPublish);
		//修改信息发布内容
		AefcommInfoContent infoContent=infoContentService.getInfoContentByPublishId(infoPublish.getId());
		infoContent.setContent(infoPublishVo.getContent());
		infoContentService.updateInfoContent(infoContent);
	    return updateRows;
	}
	
	@Override
	@Transactional
	public int deleteInfoPublishById(Long id){
		int deleteRows=infoPublishDao.deleteInfoPublishById(id);
		infoContentService.deleteInfoContentByPublishId(id);
		return deleteRows;
	}
	
	@Override
	@Transactional
	public int deleteInfoPublishByIds(String ids){
		int deleteRows= infoPublishDao.deleteInfoPublishByIds(ConvertUtil.toStrArray(ids));
		String[] idArr=ConvertUtil.toStrArray(ids);
		for(int i=0;i<idArr.length;i++){
			Long infoPublishId=Long.valueOf(idArr[i]);
			infoContentService.deleteInfoContentByPublishId(infoPublishId);
		}
		return deleteRows;
	}
	
}
