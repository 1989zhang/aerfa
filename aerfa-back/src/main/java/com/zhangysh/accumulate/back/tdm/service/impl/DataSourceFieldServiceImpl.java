package com.zhangysh.accumulate.back.tdm.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import com.zhangysh.accumulate.back.tdm.dao.DataSourceFieldDao;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceFieldService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 数据源字段映射 服务层实现
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Service
public class DataSourceFieldServiceImpl implements IDataSourceFieldService {

	@Autowired
	private DataSourceFieldDao dataSourceFieldDao;

    @Override
	public AeftdmDataSourceField getDataSourceFieldById(Long id){
	    return dataSourceFieldDao.getDataSourceFieldById(id);
	}
	
	@Override
	public BsTableDataInfo listPageDataSourceField(BsTablePageInfo pageInfo,AeftdmDataSourceField dataSourceField){
	    //pagehelper方法调用
		Page<AeftdmDataSourceField> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		dataSourceFieldDao.listDataSourceField(dataSourceField);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AeftdmDataSourceField> listBypksDataSourceField(String ids){
		return dataSourceFieldDao.listBypksDataSourceField(ConvertUtil.toStrArray(ids));
	}
	 
	@Override
	public List<AeftdmDataSourceField> listDataSourceField(AeftdmDataSourceField dataSourceField){
		return dataSourceFieldDao.listDataSourceField(dataSourceField);
	}

	@Override
	public int insertDataSourceField(AeftdmDataSourceField dataSourceField){
	    return dataSourceFieldDao.insertDataSourceField(dataSourceField);
	}
	
	@Override
	public int updateDataSourceField(AeftdmDataSourceField dataSourceField){
	    return dataSourceFieldDao.updateDataSourceField(dataSourceField);
	}
	
	@Override
	public int deleteDataSourceFieldById(Long id){
		return dataSourceFieldDao.deleteDataSourceFieldById(id);
	}
	
	@Override
	public int deleteDataSourceFieldByIds(String ids){
		return dataSourceFieldDao.deleteDataSourceFieldByIds(ConvertUtil.toStrArray(ids));
	}
	
}
