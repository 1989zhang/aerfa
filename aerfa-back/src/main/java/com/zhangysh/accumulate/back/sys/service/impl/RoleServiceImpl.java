package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.back.sys.dao.RoleDao;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import com.zhangysh.accumulate.back.sys.service.IRoleService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 角色 服务层实现
 * 
 * @author zhangysh
 * @date 2019年05月16日
 */
@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private RoleDao roleDao;

    @Override
	public AefsysRole getRoleById(Long id){
	    return roleDao.getRoleById(id);
	}
	
	@Override
	public BsTableDataInfo listPageRole(BsTablePageInfo pageInfo,AefsysRole role){
	    //pagehelper方法调用
		Page<AefsysRole> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		roleDao.listRole(role);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysRole> listRole(AefsysRole role){
		return roleDao.listRole(role);
	}
	 
	@Override
	public int insertRole(AefsysRole role){
	    return roleDao.insertRole(role);
	}
	
	@Override
	public int updateRole(AefsysRole role){
	    return roleDao.updateRole(role);
	}
	
	@Override
	public int deleteRoleById(Long id){
		return roleDao.deleteRoleById(id);
	}
	
	@Override
	public int deleteRoleByIds(String ids){
		return roleDao.deleteRoleByIds(ConvertUtil.toStrArray(ids));
	}
	
}
