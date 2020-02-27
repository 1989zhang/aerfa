package com.zhangysh.accumulate.back.sys.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.back.manage.redis.IRedisService;
import com.zhangysh.accumulate.back.sys.dao.ConfigDataDao;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.back.sys.service.IConfigDataService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
/**
 * 配置 服务层实现
 * 
 * @author zhangysh
 * @date 2019年05月24日
 */
@Service
public class ConfigDataServiceImpl implements IConfigDataService {

	@Autowired
	private IRedisService redisService;
	@Autowired
	private ConfigDataDao configDataDao;

	@Override
	public AefsysConfigData getConfigDataFromRedisByCode(String dataCode) {
		String configDataJsonStr = redisService.getRedis(CacheConstant.REDIS_CONFIG_DATA_ALL_PREFIX, "", String.class);
		List<AefsysConfigData> configDataList=new ArrayList<AefsysConfigData>();
		if(StringUtil.isEmpty(configDataJsonStr)) {
			configDataList= getAllConfigDataAndRefreshRedis();
		}else {
			configDataList=JSON.parseArray(configDataJsonStr, AefsysConfigData.class);
		}
		for(AefsysConfigData configData:configDataList) {
			if(dataCode.equals(configData.getDataCode())) {
				return configData;
			}
		}

		//没有获取到配置的话就从数据库查询一遍，解决直接插入数据库的情况
		AefsysConfigData searchConfigData=new AefsysConfigData();
		searchConfigData.setDataCode(dataCode);
		List<AefsysConfigData> retConfigDataList=checkDataCodeUnique(searchConfigData);
		if(StringUtil.isNotEmpty(retConfigDataList)){
			getAllConfigDataAndRefreshRedis();//刷新redis
			return retConfigDataList.get(0);
		}
		return null;
	}
	
	@Override
	public List<AefsysConfigData> getAllConfigDataFromRedis(){
		String configDataJsonStr = redisService.getRedis(CacheConstant.REDIS_CONFIG_DATA_ALL_PREFIX, "", String.class);
		if(StringUtil.isEmpty(configDataJsonStr)) {
			return getAllConfigDataAndRefreshRedis();
		}
		return JSON.parseArray(configDataJsonStr, AefsysConfigData.class);
	}
	
    @Override
	public AefsysConfigData getConfigDataById(Long id){
	    return configDataDao.getConfigDataById(id);
	}
	
	@Override
	public BsTableDataInfo listPageConfigData(BsTablePageInfo pageInfo,AefsysConfigData configData){
	    //pagehelper方法调用
		Page<AefsysConfigData> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		configDataDao.listConfigData(configData);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefsysConfigData> listConfigData(AefsysConfigData configData){
		return configDataDao.listConfigData(configData);
	}

	@Override
	public List<AefsysConfigData> checkDataCodeUnique(AefsysConfigData configData){
		return configDataDao.checkDataCodeUnique(configData);
	}
	@Override
	public int insertConfigData(AefsysConfigData configData){
		int effectNum=configDataDao.insertConfigData(configData);
		getAllConfigDataAndRefreshRedis();//刷新redis
	    return effectNum;
	}
	
	@Override
	public int updateConfigData(AefsysConfigData configData){
		int effectNum=configDataDao.updateConfigData(configData);
		getAllConfigDataAndRefreshRedis();//刷新redis
	    return effectNum;
	}
	
	@Override
	public int deleteConfigDataById(Long id){
		int effectNum=configDataDao.deleteConfigDataById(id);
		getAllConfigDataAndRefreshRedis();//刷新redis
		return effectNum;
	}
	
	@Override
	public int deleteConfigDataByIds(String ids){
		int effectNum= configDataDao.deleteConfigDataByIds(ConvertUtil.toStrArray(ids));
		getAllConfigDataAndRefreshRedis();//刷新redis
		return effectNum;
	}
	
	/***
	 * 从数据库获取最新的集合配置信息，并刷新到redis中。
	 * 用于第一次展示配置信息和增删改了配置信息
	 *****/
	private List<AefsysConfigData> getAllConfigDataAndRefreshRedis() {
		AefsysConfigData configData=new AefsysConfigData();
		List<AefsysConfigData> configDataList=listConfigData(configData);
		String configDataJsonStr=JSON.toJSONStringWithDateFormat(configDataList,UtilConstant.NORMAL_MIDDLE_DATE);
		redisService.setRedis(CacheConstant.REDIS_CONFIG_DATA_ALL_PREFIX, "", configDataJsonStr, CacheConstant.REDIS_NEVER_EXPIRES_HOUR);
		return configDataList;
	}
}
