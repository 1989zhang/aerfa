package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonRole;
import com.zhangysh.accumulate.back.sys.dao.PersonRoleDao;
import com.zhangysh.accumulate.back.sys.service.IPersonRoleService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 人员角色关系 服务层实现
 * 
 * @author zhangysh
 * @date 2020年03月01日
 */
@Service
public class PersonRoleServiceImpl implements IPersonRoleService {

	@Autowired
	private PersonRoleDao personRoleDao;

	@Override
	public List<AefsysPersonRole> listPersonRole(AefsysPersonRole personRole){
		return personRoleDao.listPersonRole(personRole);
	}

	@Override
	public int insertPersonRole(AefsysPersonRole personRole){
	    return personRoleDao.insertPersonRole(personRole);
	}
	
	@Override
	public int deletePersonRoleByPersonId(Long personId){
		return personRoleDao.deletePersonRoleByPersonId(personId);
	}
	
	@Override
	public int deletePersonRoleByRoleId(Long roleId){
		return personRoleDao.deletePersonRoleByRoleId(roleId);
	}
	
}
