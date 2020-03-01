package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRoleResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.back.sys.dao.RoleResourceDao;
import com.zhangysh.accumulate.back.sys.service.IRoleResourceService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 角色资源关系 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月28日
 */
@Service
public class RoleResourceServiceImpl implements IRoleResourceService {

	@Autowired
	private RoleResourceDao roleResourceDao;

	@Override
	public List<AefsysRoleResource> listRoleResource(AefsysRoleResource roleResource){
		return roleResourceDao.listRoleResource(roleResource);
	}

	@Override
	public int insertRoleResource(AefsysRoleResource roleResource){
	    return roleResourceDao.insertRoleResource(roleResource);
	}

	@Override
	public int deleteRoleResourceByRoleId(Long roleId){
		return roleResourceDao.deleteRoleResourceByRoleId(roleId);
	}
	
	@Override
	public int deleteRoleResourceByResourceId(Long resourceId){
		return roleResourceDao.deleteRoleResourceByResourceId(resourceId);
	}
	
}
