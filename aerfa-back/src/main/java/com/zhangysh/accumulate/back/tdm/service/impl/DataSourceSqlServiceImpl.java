package com.zhangysh.accumulate.back.tdm.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.back.sys.base.BaseMybatisDao;
import com.zhangysh.accumulate.back.tdm.dao.DataSourceSqlDao;
import com.zhangysh.accumulate.back.tdm.exception.QueryDataException;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceFieldService;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceSqlService;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 数据源SQL定义 服务层实现
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Service
public class DataSourceSqlServiceImpl implements IDataSourceSqlService {

	@Autowired
	private DataSourceSqlDao dataSourceSqlDao;
	@Autowired
	private BaseMybatisDao baseMybatisDao;
	@Autowired
	private IDataSourceFieldService dataSourceFieldService;
	
    @Override
	public AeftdmDataSourceSql getDataSourceSqlById(Long id){
	    return dataSourceSqlDao.getDataSourceSqlById(id);
	}
	
	@Override
	public BsTableDataInfo listPageDataSourceSql(BsTablePageInfo pageInfo,AeftdmDataSourceSql dataSourceSql){
	    //pagehelper方法调用
		Page<AeftdmDataSourceSql> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		dataSourceSqlDao.listDataSourceSql(dataSourceSql);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AeftdmDataSourceSql> listDataSourceSql(AeftdmDataSourceSql dataSourceSql){
		return dataSourceSqlDao.listDataSourceSql(dataSourceSql);
	}

	@Override
	public int insertDataSourceSql(AeftdmDataSourceSql dataSourceSql) throws QueryDataException{
		//包含了request~就不管列的情况
		if(dataSourceSql.getSqlText().contains(MarkConstant.MARK_REQUEST_PARAM_PREFIX)) {
			return dataSourceSqlDao.insertDataSourceSql(dataSourceSql);
		}else {//当不包含request~则为查出数据初始化列的操作
			List<LinkedHashMap<String, Object>> listHashMap=null;
			try {
				listHashMap=baseMybatisDao.listBySql(dataSourceSql.getSqlText());
			}catch(Exception e) {
				e.printStackTrace();
				throw new QueryDataException("SQL未查询到数据，无法初始化列资源。");
			}
			if(listHashMap==null||listHashMap.size()==0) {
				throw new QueryDataException("SQL未查询到数据，无法初始化列资源。");
			}
			//先保存sql获得sqlid
			dataSourceSqlDao.insertDataSourceSql(dataSourceSql);
			Long sqlId=dataSourceSql.getId();
			Long templateId=dataSourceSql.getTemplateId();
			LinkedHashMap<String,Object> resultMap=listHashMap.get(0);
			int i=0;
			for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
				AeftdmDataSourceField dataSourceField=new AeftdmDataSourceField();
				dataSourceField.setTemplateId(templateId);
				dataSourceField.setSqlId(sqlId);
				dataSourceField.setFieldName(entry.getKey().toLowerCase());
				dataSourceField.setCreateTime(new Date());
				dataSourceFieldService.insertDataSourceField(dataSourceField);
				i++;
			}
			return i;
		}
	}
	
	@Override
	public int updateDataSourceSql(AeftdmDataSourceSql dataSourceSql){
	    return dataSourceSqlDao.updateDataSourceSql(dataSourceSql);
	}
	
	@Override
	public int deleteDataSourceSqlById(Long id){
		return dataSourceSqlDao.deleteDataSourceSqlById(id);
	}
	
	@Override
	public int deleteDataSourceSqlByIds(String ids){
		return dataSourceSqlDao.deleteDataSourceSqlByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	public List<BsTreeNode> listDataSourceWithTreeStructure(Long templateId){
		List<BsTreeNode> retList=new ArrayList<BsTreeNode>();
		AeftdmDataSourceSql searchDataSourceSql=new AeftdmDataSourceSql();
		searchDataSourceSql.setTemplateId(templateId);
		List<AeftdmDataSourceSql> dataSourceSqlList=listDataSourceSql(searchDataSourceSql);
		for(AeftdmDataSourceSql dataSourceSql:dataSourceSqlList) {
			BsTreeNode vo=new BsTreeNode();	
			vo.setId("DataSourceSqlId"+dataSourceSql.getId());
			vo.setText(dataSourceSql.getExplainName());
			vo.setNodes(listChildFieldBySql(dataSourceSql));
			retList.add(vo);
		}
		return retList;
	}
	
	/**
	 * 获取数据源对应的字段，属性结构
	 ***/
	private List<BsTreeNode> listChildFieldBySql(AeftdmDataSourceSql dataSourceSql){
		Long sqlId=dataSourceSql.getId();
		List<BsTreeNode> retList=new ArrayList<BsTreeNode>();
		AeftdmDataSourceField searchDataSourceField =new AeftdmDataSourceField();
		searchDataSourceField.setSqlId(sqlId);
		List<AeftdmDataSourceField> dataSourceFieldList=dataSourceFieldService.listDataSourceField(searchDataSourceField);
		for(AeftdmDataSourceField dataSourceField:dataSourceFieldList) {
			BsTreeNode vo=new BsTreeNode();	
			vo.setId(dataSourceField.getId()+"");
			vo.setText(dataSourceField.getFieldName());
			vo.getAttributes().put("DataSourceSqlName", dataSourceSql.getExplainName());
			retList.add(vo);
		}
		return retList;
	}
}
