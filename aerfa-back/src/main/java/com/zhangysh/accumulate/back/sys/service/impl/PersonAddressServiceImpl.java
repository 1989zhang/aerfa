package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonAddress;
import com.zhangysh.accumulate.back.sys.dao.PersonAddressDao;
import com.zhangysh.accumulate.back.sys.service.IPersonAddressService;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 个人联系地址 服务层实现
 * 
 * @author zhangysh
 * @date 2019年05月26日
 */
@Service
public class PersonAddressServiceImpl implements IPersonAddressService {

	@Autowired
	private PersonAddressDao personAddressDao;

    @Override
	public AefsysPersonAddress getPersonAddressById(Long id){
	    return personAddressDao.getPersonAddressById(id);
	}
	
    @Override
	public AefsysPersonAddress getPersonAddressByPersonId(Long personId){
    	AefsysPersonAddress searchPersonAddress=new AefsysPersonAddress();
    	searchPersonAddress.setPersonId(personId);
    	searchPersonAddress.setIsDefault(SysDefineConstant.DB_DEFAULT_STATUS_DEFAULT);
    	List<AefsysPersonAddress> dbPersonAddressList=listPersonAddress(searchPersonAddress);
    	if(dbPersonAddressList!=null&&dbPersonAddressList.size()>0) {
    		return dbPersonAddressList.get(SysDefineConstant.DB_DEFAULT_FIRST_ARR);
    	}
    	return null;
	}
    
	@Override
	public BsTableDataInfo listPagePersonAddress(BsTablePageInfo pageInfo,AefsysPersonAddress personAddress){
	    //pagehelper方法调用
		Page<AefsysPersonAddress> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		personAddressDao.listPersonAddress(personAddress);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysPersonAddress> listPersonAddress(AefsysPersonAddress personAddress){
		return personAddressDao.listPersonAddress(personAddress);
	}

	@Override
	public int insertPersonAddress(AefsysPersonAddress personAddress){
	    return personAddressDao.insertPersonAddress(personAddress);
	}
	
	@Override
	public int updatePersonAddress(AefsysPersonAddress personAddress){
	    return personAddressDao.updatePersonAddress(personAddress);
	}
	
	@Override
	public int deletePersonAddressById(Long id){
		return personAddressDao.deletePersonAddressById(id);
	}
	
	@Override
	public int deletePersonAddressByIds(String ids){
		return personAddressDao.deletePersonAddressByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	public boolean setDefault(Long id) {
		AefsysPersonAddress currentAddress=getPersonAddressById(id);
		//不为默认才执行以下操作
		if(!SysDefineConstant.DB_DEFAULT_STATUS_DEFAULT.equals(currentAddress.getIsDefault())) {
			//找出原来的默认对象设置为非默认
			AefsysPersonAddress searchPersonAddress=new AefsysPersonAddress();
			searchPersonAddress.setIsDefault(SysDefineConstant.DB_DEFAULT_STATUS_DEFAULT);
			List<AefsysPersonAddress> defaultPersonAddressList=listPersonAddress(searchPersonAddress);
			if(defaultPersonAddressList!=null && defaultPersonAddressList.size()>0) {
				for(AefsysPersonAddress dbPersonAddress:defaultPersonAddressList) {
					dbPersonAddress.setIsDefault(SysDefineConstant.DB_DEFAULT_STATUS_NOT_DEFAULT);
					updatePersonAddress(dbPersonAddress);
				}
			}
			//修改当前为默认
			currentAddress.setIsDefault(SysDefineConstant.DB_DEFAULT_STATUS_DEFAULT);
			updatePersonAddress(currentAddress);
		}
		return true;
	}
}
