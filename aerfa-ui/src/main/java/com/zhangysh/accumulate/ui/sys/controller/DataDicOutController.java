package com.zhangysh.accumulate.ui.sys.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.pojo.support.dataobj.ColumnInfo;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysDataDicVo;
import com.zhangysh.accumulate.ui.support.service.IGenerateCodeService;
import com.zhangysh.accumulate.ui.sys.service.IDataDicService;

/*****
 * 下拉或数据字典通过此方法显示到前台，目前前台只支持service获取，所以标志为service
 * @author zhangysh
 * @date 2019年4月14日
 *****/
@Service("dic")
public class DataDicOutController {
    
	@Autowired
	private IDataDicService dataDicService;
	@Autowired 
	private IGenerateCodeService generateCodeService;
	
	/***
	 *根据字典类型获取字典集合，为了方便前台美观，所以名称简写了。且获取不到cookie
	 *@param type 字典类型编码
	 *@return 字典类型集合，必须是List<AefsysDataDic>,不能是String
	 ***/
	public List<AefsysDataDicVo> getDic(String type) {
		String dataDicVoListJson=dataDicService.getDataByType(type);
		return JSON.parseArray(dataDicVoListJson, AefsysDataDicVo.class);
	}
	
	/***
	 * 获取下拉选项通用方法，根据 type执行不同的方法，为了前台统一返回转化为数据字典类型
	 * @param type 类型判断执行哪个service方法
	 * @param param 参数service方法参数
	 ***/
	public List<AefsysDataDicVo> getSelect(String type,String param) {
		if("tableColumn".equals(type)) {
			return getTableColumnsSelect(param);
		}else if("dicType".equals(type)) {
			return getDicAllType();
		}
		return null;
	}
	
	/***
	 *获取表有哪些列下拉选项，结果转化为字典类型 ，因为是service所以没有http
	 *@param tableName 表名
	 ***/
	private List<AefsysDataDicVo> getTableColumnsSelect(String tableName){
		List<AefsysDataDicVo> retDataDicVoList=new ArrayList<AefsysDataDicVo>();
		String retColumnJson=generateCodeService.getTableColumns(tableName);
		List<ColumnInfo> retColumnInfoList=JSON.parseArray(retColumnJson, ColumnInfo.class);
		for(ColumnInfo columnInfo:retColumnInfoList) {
			AefsysDataDicVo dataDicVo=new AefsysDataDicVo();
			dataDicVo.setFullName(columnInfo.getColumnName());
			dataDicVo.setCode(columnInfo.getColumnName());
			retDataDicVoList.add(dataDicVo);
		}
		return retDataDicVoList;
	}
	/***
	 *获取字典有哪些字典类型下拉选项，结果转化为字典类型对象 ，因为是service所以没有http
	 ***/
	private List<AefsysDataDicVo> getDicAllType(){
		List<AefsysDataDicVo> retDataDicVoList=new ArrayList<AefsysDataDicVo>();
		String dicAllTypeJsonStr=dataDicService.getAllType();
		List<AefsysDataDicVo> retDicAllTypeList=JSON.parseArray(dicAllTypeJsonStr, AefsysDataDicVo.class);
		for(AefsysDataDicVo typeDic:retDicAllTypeList) {
			AefsysDataDicVo dataDicVo=new AefsysDataDicVo();
			dataDicVo.setFullName(typeDic.getTypeName());
			dataDicVo.setCode(typeDic.getTypeCode());
			retDataDicVoList.add(dataDicVo);
		}
		return retDataDicVoList;
	}
	
	
}
