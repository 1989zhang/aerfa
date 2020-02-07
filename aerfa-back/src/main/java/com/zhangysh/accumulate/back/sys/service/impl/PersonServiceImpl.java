package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.back.sys.dao.PersonDao;
import com.zhangysh.accumulate.back.sys.service.IOrgService;
import com.zhangysh.accumulate.back.sys.service.IPersonService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;

/*****
 * 人员相关service实现
 * @author zhangysh
 * @date 2018年7月15日
 *****/
@Service
public class PersonServiceImpl implements IPersonService{
	
	@Autowired
	private PersonDao personDao;

	@Autowired
	private IOrgService orgService;
	
	@Override
	public AefsysPerson getPersonById(Long personId) {
		return personDao.getPersonById(personId);
	}

	@Override
	public AefsysPerson getPersonByAccountPassword(String account,String password) {
		Map<String, Object> accountPasswordParamMap=new HashMap<String, Object>(4);
		accountPasswordParamMap.put("account", account);
		accountPasswordParamMap.put("password", password);
		return personDao.getPersonByAccountPassword(accountPasswordParamMap);
	}
	
	@Override
	public AefsysPersonVo getPersonWithOrgNameById(Long personId) {
		AefsysPerson sysPerson=getPersonById(personId);
		AefsysPersonVo sysPersonVo=JSON.parseObject(JSON.toJSONString(sysPerson), AefsysPersonVo.class);
		sysPersonVo.setOrgName(orgService.getOrgById(sysPerson.getOrgId()).getFullName());
		return sysPersonVo;
	}
	
	@Override
	public BsTableDataInfo listPagePerson(BsTablePageInfo pageInfo,AefsysPerson person) {
		//pagehelper方法调用
		Page<AefsysPerson> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		personDao.listPerson(person);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
		
		//自定义拦截器方法调用
		/*BsTableDataInfo tableInfo=new BsTableDataInfo();
		Pagination pageParm=new Pagination();
		pageParm.setCurrentPage(pageInfo.getPageNum());
		pageParm.setPageNumber(pageInfo.getPageSize());
		List<AefsysPerson> personList=personDao.listPersons(person,pageParm);
		tableInfo.setTotal(pageParm.getTotalNumber());
		tableInfo.setRows(personList);*/
		return tableInfo;
	}
	
	@Override
	public List<AefsysPerson> listPerson(AefsysPerson person){
		return personDao.listPerson(person);
	}
	
	@Override
	public List<AefsysPerson> checkAccountUnique(AefsysPerson person){
		return personDao.checkAccountUnique(person);
	}

	@Override
	public int insertPerson(AefsysPerson person) {
		person.setCreateTime(DateOperate.getCurrentUtilDate());
		return personDao.insertPerson(person);
	}
	 
	@Override
	public int updatePerson(AefsysPerson person) {
		return personDao.updatePerson(person);
	}
	
	@Override
	public int deletePersonByIds(String ids) {
		if(StringUtil.isNotEmpty(ids)) {
			String[] idStrArr=ids.split(",");
			for(int i=0;i<idStrArr.length;i++) {
				personDao.deletePersonById(Long.parseLong(idStrArr[i]));
			}
			return idStrArr.length;
		}
		return 0;
	}
	
	@Override
	public boolean checkOldPassword(String account,String oldPassword) {
		AefsysPerson dbPerson=getPersonByAccountPassword(account,oldPassword);
		if(dbPerson==null||dbPerson.getId()==null) {
			return false;
		}
		return true;
	}
}
