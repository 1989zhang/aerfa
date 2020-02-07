package com.zhangysh.accumulate.back.sys.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.back.manage.redis.IRedisService;
import com.zhangysh.accumulate.back.sys.dao.DataDicDao;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataDic;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysDataDicVo;
import com.zhangysh.accumulate.back.sys.service.IDataDicService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
/**
 * 数据字典 服务层实现
 * 
 * @author zhangysh
 * @date 2019年04月15日
 */
@Service
public class DataDicServiceImpl implements IDataDicService {

	@Autowired
	private DataDicDao dataDicDao;
	@Autowired
	private IRedisService redisService;
	
    @Override
	public AefsysDataDic getDataDicById(Long id){
	    return dataDicDao.getDataDicById(id);
	}
	
	@Override
	public BsTableDataInfo listPageDataDic(BsTablePageInfo pageInfo,AefsysDataDic dataDic){
	    //pagehelper方法调用
		Page<AefsysDataDic> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		dataDicDao.listDataDic(dataDic);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysDataDicVo> listDataDic(AefsysDataDic dataDic){
		List<AefsysDataDicVo> retDataDicList=new ArrayList<AefsysDataDicVo>();
		List<AefsysDataDic> dbDataDicList=dataDicDao.listDataDic(dataDic);
		if(dbDataDicList!=null && dbDataDicList.size()>0) {
			for(AefsysDataDic dataDicDo:dbDataDicList) {
				AefsysDataDicVo dataDicVo=JSON.parseObject(JSON.toJSONString(dataDicDo),AefsysDataDicVo.class);
				retDataDicList.add(dataDicVo);
			}
		}
		return retDataDicList;
	}
	
	@Override
	public List<AefsysDataDicVo> checkDataDicUnique(AefsysDataDic dataDic){
		List<AefsysDataDicVo> retDataDicList=new ArrayList<AefsysDataDicVo>();
		List<AefsysDataDic> dbListDataDic=dataDicDao.checkDataDicUnique(dataDic);
		if(dbListDataDic!=null && dbListDataDic.size()>0) {
			for(AefsysDataDic dataDicDo:dbListDataDic) {
				AefsysDataDicVo dataDicVo=JSON.parseObject(JSON.toJSONString(dataDicDo),AefsysDataDicVo.class);
				retDataDicList.add(dataDicVo);
			}
		}
		return retDataDicList;
	}
	
	@Override
	public int insertDataDic(AefsysDataDic dataDic){
	    return dataDicDao.insertDataDic(dataDic);
	}
	
	@Override
	public int updateDataDic(AefsysDataDic dataDic){
	    return dataDicDao.updateDataDic(dataDic);
	}
	
	@Override
	public int deleteDataDicById(Long id){
		return dataDicDao.deleteDataDicById(id);
	}
	
	@Override
	public int deleteDataDicByIds(String ids){
		return dataDicDao.deleteDataDicByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	public String getDataDicByTypeCodeFromRedis(String typeCode) {
		String dicDataJsonStr = redisService.getRedis(CacheConstant.REDIS_DIC_TYPE_PREFIX, typeCode, String.class);
		//为空从数据库里面取，且存redis
		if(StringUtil.isEmpty(dicDataJsonStr)) {
			//根据类别不分页查询
			AefsysDataDic searchDataDic=new AefsysDataDic();
			searchDataDic.setIsType(0L);//不是字典类型
			searchDataDic.setTypeCode(typeCode);
			List<AefsysDataDicVo> dataDicVoList=listDataDic(searchDataDic);
			dicDataJsonStr=JSON.toJSONStringWithDateFormat(dataDicVoList,UtilConstant.NORMAL_MIDDLE_DATE);
			redisService.setRedis(CacheConstant.REDIS_DIC_TYPE_PREFIX, typeCode, dicDataJsonStr, CacheConstant.REDIS_NEVER_EXPIRES_HOUR);
		}
	    return dicDataJsonStr;
	}
	 
	@Override
	public AefsysDataDicVo getDicTypeByTypeCode(String typeCode) {
		AefsysDataDic searchDataDic=new AefsysDataDic();
		searchDataDic.setIsType(1L);//字典类型
		searchDataDic.setTypeCode(typeCode);
		List<AefsysDataDicVo> dataDicVoList=listDataDic(searchDataDic);
		if(dataDicVoList!=null&&dataDicVoList.size()>0) {
			return dataDicVoList.get(0);
		}
		return null;
	}
}
