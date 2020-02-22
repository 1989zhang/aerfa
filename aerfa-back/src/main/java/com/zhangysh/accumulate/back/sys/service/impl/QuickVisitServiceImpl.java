package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.sys.service.IResourceService;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysQuickVisitVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysQuickVisit;
import com.zhangysh.accumulate.back.sys.dao.QuickVisitDao;
import com.zhangysh.accumulate.back.sys.service.IQuickVisitService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 常用功能快速访问 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月21日
 */
@Service
public class QuickVisitServiceImpl implements IQuickVisitService {

	@Autowired
	private IResourceService resourceService;
	@Autowired
	private QuickVisitDao quickVisitDao;

	@Override
	public List<AefsysQuickVisitVo> listQuickVisitByPersonId(Long personId){
		List<AefsysResource> directResourceList =resourceService.getDirectResourcesByPersonId(personId);

		AefsysQuickVisit searchQuickVisit=new AefsysQuickVisit();
		searchQuickVisit.setPersonId(personId);
		List<AefsysQuickVisit> quickVisitList=quickVisitDao.listQuickVisit(searchQuickVisit);

		List<AefsysQuickVisitVo> retVisitVoList=new ArrayList<AefsysQuickVisitVo>();
		for(AefsysQuickVisit quickVisit:quickVisitList){
			boolean canAddToRet=false;
			for(AefsysResource resource:directResourceList){
				if(resource.getId().equals(quickVisit.getResourceId())){
					canAddToRet=true;
					//在权限里面直接添加
					AefsysQuickVisitVo quickVisitVo=JSON.parseObject(JSON.toJSONString(quickVisit),AefsysQuickVisitVo.class);
					quickVisitVo.setResource(resource);
					retVisitVoList.add(quickVisitVo);
					break;
				}
			}
			//不在里面不添加且要删除
			if(!canAddToRet){
				deleteQuickVisitById(quickVisit.getId());
			}
		}
		return retVisitVoList;
	}

	@Override
	public int insertQuickVisit(AefsysQuickVisit quickVisit){
	    return quickVisitDao.insertQuickVisit(quickVisit);
	}
	
	@Override
	public int updateQuickVisit(AefsysQuickVisit quickVisit){
	    return quickVisitDao.updateQuickVisit(quickVisit);
	}
	
	@Override
	public int deleteQuickVisitById(Long id){
		return quickVisitDao.deleteQuickVisitById(id);
	}

	@Override
	public int deleteQuickVisitByIds(String ids){
		return quickVisitDao.deleteQuickVisitByIds(ConvertUtil.toStrArray(ids));
	}
}
