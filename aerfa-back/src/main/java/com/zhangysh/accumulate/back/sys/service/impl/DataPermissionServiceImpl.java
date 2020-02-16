package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.back.sys.dao.DataPermissionDao;
import com.zhangysh.accumulate.back.sys.service.IDataPermissionService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 数据权限 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Service
public class DataPermissionServiceImpl implements IDataPermissionService {

	@Autowired
	private DataPermissionDao dataPermissionDao;

    @Override
	public AefsysDataPermission getDataPermissionById(Long id){
	    return dataPermissionDao.getDataPermissionById(id);
	}
	
	@Override
	public BsTableDataInfo listPageDataPermission(BsTablePageInfo pageInfo,AefsysDataPermission dataPermission){
	    //pagehelper方法调用
		Page<AefsysDataPermission> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		dataPermissionDao.listDataPermission(dataPermission);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysDataPermission> listBypksDataPermission(String ids){
		return dataPermissionDao.listBypksDataPermission(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefsysDataPermission> listDataPermission(AefsysDataPermission dataPermission){
		return dataPermissionDao.listDataPermission(dataPermission);
	}

	@Override
	public int insertDataPermission(AefsysDataPermission dataPermission){
	    return dataPermissionDao.insertDataPermission(dataPermission);
	}
	
	@Override
	public int updateDataPermission(AefsysDataPermission dataPermission){
	    return dataPermissionDao.updateDataPermission(dataPermission);
	}
	
	@Override
	public int deleteDataPermissionById(Long id){
		return dataPermissionDao.deleteDataPermissionById(id);
	}
	
	@Override
	public int deleteDataPermissionByIds(String ids){
		return dataPermissionDao.deleteDataPermissionByIds(ConvertUtil.toStrArray(ids));
	}
	
}
