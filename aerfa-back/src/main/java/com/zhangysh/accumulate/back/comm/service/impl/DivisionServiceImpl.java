package com.zhangysh.accumulate.back.comm.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommDivision;
import com.zhangysh.accumulate.pojo.comm.viewobj.CityPickerRangeVo;
import com.zhangysh.accumulate.back.comm.dao.DivisionDao;
import com.zhangysh.accumulate.back.comm.service.IDivisionService;
import com.zhangysh.accumulate.back.manage.redis.IRedisService;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
/**
 * 行政区划 服务层实现
 * 
 * @author zhangysh
 * @date 2019年05月25日
 */
@Service
public class DivisionServiceImpl implements IDivisionService {

	@Autowired
	private IRedisService redisService;
	
	@Autowired
	private DivisionDao divisionDao;

    @Override
	public AefcommDivision getDivisionById(Long id){
	    return divisionDao.getDivisionById(id);
	}
	
	@Override
	public BsTableDataInfo listPageDivision(BsTablePageInfo pageInfo,AefcommDivision division){
	    //pagehelper方法调用
		Page<AefcommDivision> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		divisionDao.listDivision(division);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefcommDivision> listDivision(AefcommDivision division){
		return divisionDao.listDivision(division);
	}

	@Override
	public List<AefcommDivision> checkCodeUnique(AefcommDivision division){
		return divisionDao.checkCodeUnique(division);
	}
	@Override
	public int insertDivision(AefcommDivision division){
	    return divisionDao.insertDivision(division);
	}
	
	@Override
	public int updateDivision(AefcommDivision division){
	    return divisionDao.updateDivision(division);
	}
	
	@Override
	public int deleteDivisionById(Long id){
		return divisionDao.deleteDivisionById(id);
	}
	
	@Override
	public int deleteDivisionByIds(String ids){
		return divisionDao.deleteDivisionByIds(ConvertUtil.toStrArray(ids));
	}
	
	
	@Override
	public List<BsTreeNode> listDivisionWithTreeStructure(Long parentId){
		List<BsTreeNode> retList=new ArrayList<BsTreeNode>();
		AefcommDivision searchParamDivision=new AefcommDivision();
		//顶级区划父级序号为0
		if(parentId==null||parentId<SysDefineConstant.DB_DIVISION_TOP_PARENT_ID) {
			searchParamDivision.setParentId(SysDefineConstant.DB_DIVISION_TOP_PARENT_ID);
		}
		searchParamDivision.setParentId(parentId);
		List<AefcommDivision> divisionList=new ArrayList<AefcommDivision>();
		divisionList=divisionDao.listDivision(searchParamDivision);
		for(AefcommDivision division:divisionList) {
			BsTreeNode vo=new BsTreeNode();
			vo.setId(division.getId()+"");
			vo.setText(division.getName());
			vo.setLazyLoad(true);
			retList.add(vo);
		}
		return retList;
	}
	
	@Override
	public String getCityPickerDivisionDataAloneFromRedis() {
		String divisionCityPickerJsonStr=redisService.getRedis(CacheConstant.REDIS_DIVISION_CITY_PICKER_PREFIX, "", String.class);
		//从数据查询并设置到redis
		if(StringUtil.isEmpty(divisionCityPickerJsonStr)) {
			//首先找出中国行政区划所下的省
			AefcommDivision searchProvinceDivision=new AefcommDivision();
			searchProvinceDivision.setParentId(SysDefineConstant.DB_DIVISION_CHINA_ID);
			List<AefcommDivision> provinceList=divisionDao.listDivision(searchProvinceDivision);
			
			Map<Object,Object> retNationMap=new HashMap<Object,Object>();
			CityPickerRangeVo cityPickerRangeVo=getCityPickerRange();
			retNationMap.put(86, cityPickerRangeVo);		
			for(AefcommDivision province:provinceList) {
				Map<Object,Object> cityMap=new HashMap<Object,Object>();
				//四个直辖市特殊处理
				if("110000".equals(province.getCode())) {
					cityMap.put(110100, "北京市");
					Map<Integer,String> districtMap=getChildDivisionMapByParentId(province.getId());
					retNationMap.put(110100, districtMap);
				}else if("120000".equals(province.getCode())) {
					cityMap.put(120100, "天津市");
					Map<Integer,String> districtMap=getChildDivisionMapByParentId(province.getId());
					retNationMap.put(120100, districtMap);
				}else if("500000".equals(province.getCode())) {
					cityMap.put(500100, "重庆市");
					Map<Integer,String> districtMap=getChildDivisionMapByParentId(province.getId());
					retNationMap.put(500100, districtMap);
				}else if("310000".equals(province.getCode())) {
					cityMap.put(310100, "上海市");
					Map<Integer,String> districtMap=getChildDivisionMapByParentId(province.getId());
					retNationMap.put(310100, districtMap);
				}else {
					//查询省下面的市
					AefcommDivision searchCityDivision=new AefcommDivision();
					searchCityDivision.setParentId(province.getId());
					List<AefcommDivision> cityList=divisionDao.listDivision(searchCityDivision);
					for(AefcommDivision city:cityList) {
						cityMap.put(Integer.parseInt(city.getCode()), city.getName());	
						Map<Integer,String> districtMap=getChildDivisionMapByParentId(city.getId());
						retNationMap.put(Integer.parseInt(city.getCode()), districtMap);
					}
				}
				retNationMap.put(Integer.parseInt(province.getCode()), cityMap);
			}
			divisionCityPickerJsonStr=JSON.toJSONString(retNationMap);
			divisionCityPickerJsonStr=divisionCityPickerJsonStr.replace("rangeAtoG", "A-G");
			divisionCityPickerJsonStr=divisionCityPickerJsonStr.replace("rangeHtoK", "H-K");
			divisionCityPickerJsonStr=divisionCityPickerJsonStr.replace("rangeLtoS", "L-S");
			divisionCityPickerJsonStr=divisionCityPickerJsonStr.replace("rangeTtoZ", "T-Z");
			//设置到redis
			redisService.setRedis(CacheConstant.REDIS_DIVISION_CITY_PICKER_PREFIX,"",divisionCityPickerJsonStr,CacheConstant.REDIS_EXPIRES_ONE_DAY);
		}
		return divisionCityPickerJsonStr;
	}

	/**
	 * 城市选择器CityPicker专用：把省列表对象转化为CityPickerRangeVo，省级单位一般不会变得
	 * @return 转化得范围对象
	 ****
	 */
	private CityPickerRangeVo getCityPickerRange() {
		/* A-G区划序号 */
		List<Map<String,String>> AtoGList=new ArrayList<Map<String,String>>();
		//安徽省
		Map<String,String> ahProvinceMap=new HashMap<String,String>(4);
		ahProvinceMap.put("code", "340000");ahProvinceMap.put("address", "安徽省");
		AtoGList.add(ahProvinceMap);
		//北京市
		Map<String,String> bjProvinceMap=new HashMap<String,String>(4);
		bjProvinceMap.put("code", "110000");bjProvinceMap.put("address", "北京市");
		AtoGList.add(bjProvinceMap);
		//重庆市
		Map<String,String> cqProvinceMap=new HashMap<String,String>(4);
		cqProvinceMap.put("code", "500000");cqProvinceMap.put("address", "重庆市");
		AtoGList.add(cqProvinceMap);
		//福建省
		Map<String,String> fjProvinceMap=new HashMap<String,String>(4);
		fjProvinceMap.put("code", "350000");fjProvinceMap.put("address", "福建省");
		AtoGList.add(fjProvinceMap);
		//甘肃省	
		Map<String,String> gsProvinceMap=new HashMap<String,String>(4);
		gsProvinceMap.put("code", "620000");gsProvinceMap.put("address", "甘肃省");
		AtoGList.add(gsProvinceMap);
		//广东省
		Map<String,String> gdProvinceMap=new HashMap<String,String>(4);
		gdProvinceMap.put("code", "440000");gdProvinceMap.put("address", "广东省");
		AtoGList.add(gdProvinceMap);
		//广西壮族自治区
		Map<String,String> gxProvinceMap=new HashMap<String,String>(4);
		gxProvinceMap.put("code", "450000");gxProvinceMap.put("address", "广西壮族自治区");
		AtoGList.add(gxProvinceMap);
		//贵州省
		Map<String,String> gzProvinceMap=new HashMap<String,String>(4);
		gzProvinceMap.put("code", "520000");gzProvinceMap.put("address", "贵州省");
		AtoGList.add(gzProvinceMap);

		/* H-K区划序号 */
		List<Map<String,String>> HtoKList=new ArrayList<Map<String,String>>();
		//海南省
		Map<String,String> hnProvinceMap=new HashMap<String,String>(4);
		hnProvinceMap.put("code", "460000");hnProvinceMap.put("address", "海南省");
		HtoKList.add(hnProvinceMap);
		//河北省
		Map<String,String> hbProvinceMap=new HashMap<String,String>(4);
		hbProvinceMap.put("code", "130000");hbProvinceMap.put("address", "河北省");
		HtoKList.add(hbProvinceMap);
		//黑龙江省
		Map<String,String> hljProvinceMap=new HashMap<String,String>(4);
		hljProvinceMap.put("code", "230000");hljProvinceMap.put("address", "黑龙江省");
		HtoKList.add(hljProvinceMap);
		//河南省
		Map<String,String> hn2ProvinceMap=new HashMap<String,String>(4);
		hn2ProvinceMap.put("code", "410000");hn2ProvinceMap.put("address", "河南省");
		HtoKList.add(hn2ProvinceMap);
		//湖北省
		Map<String,String> hb2ProvinceMap=new HashMap<String,String>(4);
		hb2ProvinceMap.put("code", "420000");hb2ProvinceMap.put("address", "湖北省");
		HtoKList.add(hb2ProvinceMap);
		//湖南省
		Map<String,String> hn3ProvinceMap=new HashMap<String,String>(4);
		hn3ProvinceMap.put("code", "430000");hn3ProvinceMap.put("address", "湖南省");
		HtoKList.add(hn3ProvinceMap);
		//江苏省
		Map<String,String> jsProvinceMap=new HashMap<String,String>(4);
		jsProvinceMap.put("code", "320000");jsProvinceMap.put("address", "江苏省");
		HtoKList.add(jsProvinceMap);
		//江西省
		Map<String,String> jxProvinceMap=new HashMap<String,String>(4);
		jxProvinceMap.put("code", "360000");jxProvinceMap.put("address", "江西省");
		HtoKList.add(jxProvinceMap);
		//吉林省
		Map<String,String> jlProvinceMap=new HashMap<String,String>(4);
		jlProvinceMap.put("code", "220000");jlProvinceMap.put("address", "吉林省");
		HtoKList.add(jlProvinceMap);

		/* L-S区划序号 */
		List<Map<String,String>> LtoSList=new ArrayList<Map<String,String>>();
		//辽宁省
		Map<String,String> lnProvinceMap=new HashMap<String,String>(4);
		lnProvinceMap.put("code", "210000");lnProvinceMap.put("address", "辽宁省");
		LtoSList.add(lnProvinceMap);
		//内蒙古自治区
		Map<String,String> nmgProvinceMap=new HashMap<String,String>(4);
		nmgProvinceMap.put("code", "150000");nmgProvinceMap.put("address", "内蒙古自治区");
		LtoSList.add(nmgProvinceMap);
		//宁夏回族自治区
		Map<String,String> nxProvinceMap=new HashMap<String,String>(4);
		nxProvinceMap.put("code", "640000");nxProvinceMap.put("address", "宁夏回族自治区");
		LtoSList.add(nxProvinceMap);
		//青海省
		Map<String,String> qhProvinceMap=new HashMap<String,String>(4);
		qhProvinceMap.put("code", "630000");qhProvinceMap.put("address", "青海省");
		LtoSList.add(qhProvinceMap);
		//山东省
		Map<String,String> sdProvinceMap=new HashMap<String,String>(4);
		sdProvinceMap.put("code", "370000");sdProvinceMap.put("address", "山东省");
		LtoSList.add(sdProvinceMap);
		//上海市
		Map<String,String> shProvinceMap=new HashMap<String,String>(4);
		shProvinceMap.put("code", "310000");shProvinceMap.put("address", "上海市");
		LtoSList.add(shProvinceMap);
		//山西省
		Map<String,String> sxProvinceMap=new HashMap<String,String>(4);
		sxProvinceMap.put("code", "140000");sxProvinceMap.put("address", "山西省");
		LtoSList.add(sxProvinceMap);
		//陕西省
		Map<String,String> sx1ProvinceMap=new HashMap<String,String>(4);
		sx1ProvinceMap.put("code", "610000");sx1ProvinceMap.put("address", "陕西省");
		LtoSList.add(sx1ProvinceMap);
		//四川省
		Map<String,String> scProvinceMap=new HashMap<String,String>(4);
		scProvinceMap.put("code", "510000");scProvinceMap.put("address", "四川省");
		LtoSList.add(scProvinceMap);

		/* T-Z区划序号 */
		List<Map<String,String>> TtoZList=new ArrayList<Map<String,String>>();
		//天津市
		Map<String,String> tjProvinceMap=new HashMap<String,String>(4);
		tjProvinceMap.put("code", "120000");tjProvinceMap.put("address", "天津市");
		TtoZList.add(tjProvinceMap);
		//新疆维吾尔自治区
		Map<String,String> xjProvinceMap=new HashMap<String,String>(4);
		xjProvinceMap.put("code", "650000");xjProvinceMap.put("address", "新疆维吾尔自治区");
		TtoZList.add(xjProvinceMap);
		//西藏自治区
		Map<String,String> xzProvinceMap=new HashMap<String,String>(4);
		xzProvinceMap.put("code", "540000");xzProvinceMap.put("address", "西藏自治区");
		TtoZList.add(xzProvinceMap);
		//云南省
		Map<String,String> ynProvinceMap=new HashMap<String,String>(4);
		ynProvinceMap.put("code", "530000");ynProvinceMap.put("address", "云南省");
		TtoZList.add(ynProvinceMap);
		//浙江省
		Map<String,String> zjProvinceMap=new HashMap<String,String>(4);
		zjProvinceMap.put("code", "330000");zjProvinceMap.put("address", "浙江省");
		TtoZList.add(zjProvinceMap);

		CityPickerRangeVo cityPickerRangeVo=new CityPickerRangeVo();
		cityPickerRangeVo.setRangeAtoG(AtoGList);
		cityPickerRangeVo.setRangeHtoK(HtoKList);
		cityPickerRangeVo.setRangeLtoS(LtoSList);
		cityPickerRangeVo.setRangeTtoZ(TtoZList);
		return cityPickerRangeVo;
	}
	/***
	 * 城市选择器CityPicker专用：根据父区划id查询出子区划的map<区划代码,名称>集合
	 * @param parentId 父区划id
	 ***/
	private Map<Integer,String> getChildDivisionMapByParentId(Long parentId) {
		Map<Integer,String> retDivisionMap=new HashMap<Integer,String>();
		AefcommDivision searchDivision=new AefcommDivision();
		searchDivision.setParentId(parentId);
		List<AefcommDivision> divisionList=divisionDao.listDivision(searchDivision);
		for(AefcommDivision division:divisionList) {
			retDivisionMap.put(Integer.parseInt(division.getCode()), division.getName());
		}
		return retDivisionMap;
	}
}
