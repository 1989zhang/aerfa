package com.zhangysh.accumulate.back.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysDataDic;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.transobj.AefsysDataDicDto;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysDataDicVo;
import com.zhangysh.accumulate.back.sys.service.IDataDicService;

/**
 * 数据字典调用相关方法
 * 
 * @author zhangysh
 * @date 2019年04月15日
 */
@Controller
@RequestMapping("/sys/data_dic")
public class DataDicController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(DataDicController.class);

	@Autowired
	private IDataDicService dataDicService;
    @Autowired
    private IRedisRelatedService redisRelatedService;
    
	/****
	 * 获取展示数据字典信息列表
	 * @param request 请求对象
	 * @param AefsysDataDicDto 分页和查询对象
	 * @return 获取到的数据字典对象集合JSON
	 ****/
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefsysDataDicDto dataDicDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",dataDicDto.getPageInfo().getPageNum(),dataDicDto.getPageInfo().getPageSize());
		//分页查询的情况
		BsTableDataInfo tableInfo=dataDicService.listPageDataDic(dataDicDto.getPageInfo(),dataDicDto.getDataDic());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 获取所有数据字典类型，不分页，所以不能用list，下拉框要用，因为是service要用所以没有http
	 * @param request 请求对象
	 * @return 获取到的所有数据字典类型对象集合JSON
	 ****/
	@RequestMapping(value="/type",method = RequestMethod.POST)
	@ResponseBody
	public String getAllType() {
		AefsysDataDic searchDataDic=new AefsysDataDic();
		searchDataDic.setIsType(1L);//不是字典类型
		List<AefsysDataDicVo> dataDicVoList=dataDicService.listDataDic(searchDataDic);
		return JSON.toJSONStringWithDateFormat(dataDicVoList,UtilConstant.NORMAL_MIDDLE_DATE);
	}
	
	/****
	 * 根据字典类型，不分页获取数据字典信息列表。因为要存redis所以单独列出来
	 * 由于存redis要加key管理起来复杂，所以清理缓存来管理
	 * @param request 请求对象
	 * @param type 字典类型
	 * @return 获取到的数据字典对象集合JSON
	 ****/
	@RequestMapping(value="/data",method = RequestMethod.POST)
	@ResponseBody
	public String getDataByType(@RequestBody String type) {
		return dataDicService.getDataDicByTypeCodeFromRedis(type);
	}
	
	/****
	 * 获取展示单个数据字典,以便修改
	 * @param request 请求对象
     * @param id 数据字典主键ID
     * @return 数据字典信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle数据字典主键信息:{}",id);
		return JSON.toJSONString(dataDicService.getDataDicById(id));
	}
	
	/****
	 *保存新增和修改的数据字典信息 
	 *@param request 请求对象
	 *@param dataDic 保存的对象
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveDataDic(HttpServletRequest request,@RequestBody AefsysDataDic dataDic) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( dataDic.getId()!=null ) {//修改方法
			dataDic.setUpdateTime(DateOperate.getCurrentUtilDate());
			dataDic.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(dataDicService.updateDataDic(dataDic));
		} else {//新增方法
			//当不是字典类型的时候，要先查出字典类型名称
			if(dataDic.getIsType()==0) {
				AefsysDataDicVo dicType=dataDicService.getDicTypeByTypeCode(dataDic.getTypeCode());
				dataDic.setTypeName(dicType==null?"":dicType.getTypeName());
			}
			dataDic.setCreateTime(DateOperate.getCurrentUtilDate());
			dataDic.setCreateBy(operPerson.getPersonName());
			return toHandlerResultStr(dataDicService.insertDataDic(dataDic));
		}
	}
	/****
	 *检查字典类型编码的唯一性
	 *@param request 请求对象
	 *@param DataDic 要检查的数据字典包括：typeCode和id,id为了排除自己
	 ***/
	@RequestMapping(value = "/check_data_dic_unique",method = RequestMethod.POST)
	@ResponseBody
	public String checkDataDicUnique(HttpServletRequest request,@RequestBody AefsysDataDic dataDic) {
		return toUniqueResultStr(dataDicService.checkDataDicUnique(dataDic).size());
	}
	/****
	 *删除数据字典对象，可以删除多个.
	 *@param request 请求对象
	 *@param ids 要删除的数据字典ids,中间英文,隔开
	 ***/
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteDataDicByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(dataDicService.deleteDataDicByIds(ids));
	}
}
