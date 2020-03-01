package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.zhangysh.accumulate.back.sys.base.BaseMybatisDao;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysDataPermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataPermission;
import com.zhangysh.accumulate.back.sys.dao.DataPermissionDao;
import com.zhangysh.accumulate.back.sys.service.IDataPermissionService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 数据权限 服务层实现
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Service
public class DataPermissionServiceImpl implements IDataPermissionService {

	@Autowired
	private DataPermissionDao dataPermissionDao;
	@Autowired
	private BaseMybatisDao baseMybatisDao;

    @Override
	public AefsysDataPermissionVo getDataPermissionById(Long id){
		AefsysDataPermissionVo dataPermissionVo=new AefsysDataPermissionVo();
		AefsysDataPermission dataPermission= dataPermissionDao.getDataPermissionById(id);
		BeanUtils.copyProperties(dataPermission,dataPermissionVo);
		return dataPermissionVo;
	}
	
	@Override
	public BsTableDataInfo listPageDataPermission(BsTablePageInfo pageInfo,AefsysDataPermission dataPermission){
	    //pagehelper方法调用
		Page<AefsysDataPermission> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		dataPermissionDao.listDataPermission(dataPermission);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysDataPermission> listBypksDataPermission(String ids){
		return dataPermissionDao.listBypksDataPermission(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefsysDataPermissionVo> listDataPermission(AefsysDataPermission dataPermission){
		 return transToDataPermissionVo(dataPermissionDao.listDataPermission(dataPermission));
	}

	@Override
	public int insertDataPermission(AefsysDataPermission dataPermission){
	    return dataPermissionDao.insertDataPermission(dataPermission);
	}
	
	@Override
	public int updateDataPermission(AefsysDataPermission dataPermission){
	    return dataPermissionDao.updateDataPermission(dataPermission);
	}
	
	@Override
	public int deleteDataPermissionById(Long id){
		return dataPermissionDao.deleteDataPermissionById(id);
	}
	
	@Override
	public int deleteDataPermissionByIds(String ids){
		return dataPermissionDao.deleteDataPermissionByIds(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefsysDataPermissionVo> getDataPermissionListByRoleId(Long roleId){
		AefsysDataPermission searchDataPermission=new AefsysDataPermission();
		searchDataPermission.setRoleId(roleId);
		return listDataPermission(searchDataPermission);
	}

	@Override
	public int cancelRoleDataPermission(Long roleId){
    	String sql="update aefsys_data_permission set role_id=null where role_id="+roleId;
		return baseMybatisDao.updateBySql(sql);
	}

	/**
	 * 把数据模型对象集合转化为视图模型集合
	 * @param dataPermissionList 数据模型对象集合
	 * @return 转化为的视图对象集合
	 */
	private List<AefsysDataPermissionVo> transToDataPermissionVo(List<AefsysDataPermission> dataPermissionList){
		List<AefsysDataPermissionVo> retDataPermissionVoList=new ArrayList<AefsysDataPermissionVo>();
		for(AefsysDataPermission dataPermission:dataPermissionList){
			AefsysDataPermissionVo retDataPermissionVo=new AefsysDataPermissionVo();
			BeanUtils.copyProperties(dataPermission,retDataPermissionVo);
			retDataPermissionVoList.add(retDataPermissionVo);
		}
		return retDataPermissionVoList;
	}
	
}
