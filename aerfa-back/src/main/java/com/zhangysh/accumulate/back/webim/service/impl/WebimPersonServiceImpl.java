package com.zhangysh.accumulate.back.webim.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.common.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimPerson;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimSearchDto;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimFriendVo;
import com.zhangysh.accumulate.pojo.webim.viewobj.AefwebimPersonVo;
import com.zhangysh.accumulate.back.sys.service.IPersonService;
import com.zhangysh.accumulate.back.webim.dao.WebimPersonDao;
import com.zhangysh.accumulate.back.webim.service.IFriendService;
import com.zhangysh.accumulate.back.webim.service.IWebimPersonService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 个人 服务层实现
 * 
 * @author zhangysh
 * @date 2019年10月27日
 */
@Service
public class WebimPersonServiceImpl implements IWebimPersonService {


	@Autowired
	private IPersonService personService;
    @Autowired
    private IFriendService friendService;
	@Autowired
	private WebimPersonDao webimPersonDao;
	
    @Override
	public AefwebimPerson getWebimPersonById(Long id){
	    return webimPersonDao.getPersonById(id);
	}
	
    @Override
   	public AefwebimPerson getWebimPersonBySysPersonId(Long sysPersonId) {
    	return webimPersonDao.getWebimPersonBySysPersonId(sysPersonId);
    }
    
	@Override
	public BsTableDataInfo listPagePerson(BsTablePageInfo pageInfo,AefwebimPerson person){
	    //pagehelper方法调用
		Page<AefwebimPerson> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		webimPersonDao.listPerson(person);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefwebimPerson> listBypksPerson(String ids){
		return webimPersonDao.listBypksPerson(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefwebimPerson> listPerson(AefwebimPerson person){
		return webimPersonDao.listPerson(person);
	}

	@Override
	public int insertPerson(AefwebimPerson person){
	    return webimPersonDao.insertPerson(person);
	}
	
	@Override
	public int updatePerson(AefwebimPerson person){
	    return webimPersonDao.updatePerson(person);
	}
	
	@Override
	public int deletePersonById(Long id){
		return webimPersonDao.deletePersonById(id);
	}
	
	@Override
	public int deletePersonByIds(String ids){
		return webimPersonDao.deletePersonByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	public AefwebimPersonVo getInformation(AefwebimSearchDto searchDto){
		Long personId =Long.valueOf(searchDto.getValue());
		AefsysPerson sysPerson=personService.getPersonById(personId);
		AefwebimPerson webimPerson=getWebimPersonBySysPersonId(personId);
		AefwebimFriendVo webimFriendVo=friendService.changeToWebimFriendVoBySysPerson(sysPerson);
		//待优化，字段重复null要覆盖
		AefwebimPersonVo webimPersonVo=new AefwebimPersonVo();
		BeanUtils.copyProperties(webimFriendVo, webimPersonVo);
		BeanUtils.copyProperties(webimPerson, webimPersonVo);
		BeanUtils.copyProperties(sysPerson, webimPersonVo);
		//出生日期null不好处理
		if(StringUtil.isNull(webimPersonVo.getBirthday())){
			webimPersonVo.setBirthday(webimPersonVo.getCreateTime());
		}
		return webimPersonVo;
	}
}
