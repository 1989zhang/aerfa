package com.zhangysh.accumulate.back.iqa.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import com.zhangysh.accumulate.back.iqa.dao.CategoryDao;
import com.zhangysh.accumulate.back.iqa.service.ICategoryService;
import com.zhangysh.accumulate.back.sys.base.BaseMybatisDao;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 知识类别 服务层实现
 * 
 * @author zhangysh
 * @date 2019年12月22日
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryDao categoryDao;
    @Autowired
    private BaseMybatisDao baseMybatisDao;
    
    @Override
	public AefiqaCategory getCategoryById(Long id){
	    return categoryDao.getCategoryById(id);
	}
	
    @Override
    public List<BsTreeNode> listCategoryWithTreeStructure(){
    	List<BsTreeNode> retList=new ArrayList<BsTreeNode>();
    	List<AefiqaCategory> categoryList=listCategory(null);
    	for(AefiqaCategory category:categoryList) {
    		BsTreeNode vo=new BsTreeNode();	
			vo.setId(category.getId()+"");
			vo.setText(category.getCategoryName());
			vo.setIcon("glyphicon glyphicon-book");
			retList.add(vo);
    	}
    	return retList;
    }
    
	@Override
	public BsTableDataInfo listPageCategory(BsTablePageInfo pageInfo,AefiqaCategory category){
	    //pagehelper方法调用
		Page<AefiqaCategory> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		categoryDao.listCategory(category);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefiqaCategory> listBypksCategory(String ids){
		return categoryDao.listBypksCategory(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefiqaCategory> listCategory(AefiqaCategory category){
		//因为pageInfo.getOrderBy()可根据列排序,所以就直接用PageHelper的排序方法
		PageHelper.orderBy("order_no asc");
		return categoryDao.listCategory(category);
	}

	@Override
	public int insertCategory(AefiqaCategory category){
	    return categoryDao.insertCategory(category);
	}
	
	@Override
	public int updateCategory(AefiqaCategory category){
	    return categoryDao.updateCategory(category);
	}
	
	@Override
	public int deleteCategoryById(Long id){
		return categoryDao.deleteCategoryById(id);
	}
	
	@Override
	public int deleteCategoryByIds(String ids){
		String[] strIds=ConvertUtil.toStrArray(ids);
		String idInStr="(";
		for(int i=0;i<strIds.length;i++) {
			idInStr+=strIds[i]+",";
		}
		idInStr=idInStr.substring(0,idInStr.length()-1);
		idInStr=idInStr+")";
		String updateQuestionSql="update aefiqa_question set category_id=null where category_id in "+idInStr;
		baseMybatisDao.updateBySql(updateQuestionSql);
		return categoryDao.deleteCategoryByIds(ConvertUtil.toStrArray(ids));
	}
	
}
