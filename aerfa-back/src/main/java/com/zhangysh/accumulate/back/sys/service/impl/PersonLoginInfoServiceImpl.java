package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.back.sys.dao.PersonLoginInfoDao;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonLoginInfo;
import com.zhangysh.accumulate.back.sys.service.IPersonLoginInfoService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 个人登录 服务层实现
 * 
 * @author zhangysh
 * @date 2019年05月20日
 */
@Service
public class PersonLoginInfoServiceImpl implements IPersonLoginInfoService {

	@Autowired
	private PersonLoginInfoDao personLoginInfoDao;

    @Override
	public AefsysPersonLoginInfo getPersonLoginInfoById(Long id){
	    return personLoginInfoDao.getPersonLoginInfoById(id);
	}
	
    @Override
	public AefsysPersonLoginInfo getPersonLoginInfoByPersonId(Long personId) {
    	return personLoginInfoDao.getPersonLoginInfoByPersonId(personId);
    }
    
	@Override
	public BsTableDataInfo listPagePersonLoginInfo(BsTablePageInfo pageInfo,AefsysPersonLoginInfo personLoginInfo){
	    //pagehelper方法调用
		Page<AefsysPersonLoginInfo> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		personLoginInfoDao.listPersonLoginInfo(personLoginInfo);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysPersonLoginInfo> listPersonLoginInfo(AefsysPersonLoginInfo personLoginInfo){
		return personLoginInfoDao.listPersonLoginInfo(personLoginInfo);
	}
	 
	@Override
	public int insertPersonLoginInfo(AefsysPersonLoginInfo personLoginInfo){
	    return personLoginInfoDao.insertPersonLoginInfo(personLoginInfo);
	}
	
	@Override
	public int updatePersonLoginInfo(AefsysPersonLoginInfo personLoginInfo){
	    return personLoginInfoDao.updatePersonLoginInfo(personLoginInfo);
	}
	
	@Override
	public int deletePersonLoginInfoById(Long id){
		return personLoginInfoDao.deletePersonLoginInfoById(id);
	}
	
	@Override
	public int deletePersonLoginInfoByIds(String ids){
		return personLoginInfoDao.deletePersonLoginInfoByIds(ConvertUtil.toStrArray(ids));
	}
	
}
