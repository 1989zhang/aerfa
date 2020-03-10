package com.zhangysh.accumulate.back.tdm.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.back.tdm.dao.FillRuleDao;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceFieldService;
import com.zhangysh.accumulate.back.tdm.service.IFillRuleService;
import com.zhangysh.accumulate.back.tdm.service.ITemplateService;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmTemplate;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmFillRuleDataDto;
import com.zhangysh.accumulate.pojo.tdm.viewobj.AeftdmFillRuleVo;
/**
 * 模板填充规则 服务层实现
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Service
public class FillRuleServiceImpl implements IFillRuleService {
	
	@Autowired
	private IDataSourceFieldService dataSourceFieldService;
	@Autowired
	private ITemplateService templateService;
	@Autowired
	private FillRuleDao fillRuleDao;
	
	@Override
	public BsTableDataInfo getSettingFieldList(String fieldIds) {
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		List<AeftdmFillRuleVo> retFillRuleList=new ArrayList<AeftdmFillRuleVo>();
		List<AeftdmDataSourceField> dataSourceFieldList=dataSourceFieldService.listBypksDataSourceField(fieldIds);
		for(AeftdmDataSourceField dataSourceFile:dataSourceFieldList) {
			AeftdmFillRuleVo fillRuleVo=new AeftdmFillRuleVo();
			fillRuleVo.setFieldId(dataSourceFile.getId());
			fillRuleVo.setFieldName(dataSourceFile.getFieldName());
			fillRuleVo.setShowType("string");//默认数据类型为string
			fillRuleVo.setHorizontalAlign("left");//默认居左left
			fillRuleVo.setFontName("SimSun");//默认字体宋体SimSun
			fillRuleVo.setFontSize(12L);//默认字体大小12
			fillRuleVo.setIsBlock(0L);//默认不加粗
			fillRuleVo.setFormatParam("");//不然无此属性
			retFillRuleList.add(fillRuleVo);
		}
		tableInfo.setRows(retFillRuleList);
		tableInfo.setTotal(retFillRuleList.size());
		return tableInfo;
	}
	
	@Override
	public boolean saveTableData(AeftdmFillRuleDataDto tableDataDto) {
		List<AeftdmFillRuleVo> fillRuleVoList=JSON.parseArray(tableDataDto.getTableData(), AeftdmFillRuleVo.class);
		String locationMark=tableDataDto.getLocationMark();
		AeftdmFillRuleVo firstFillRuleVo=fillRuleVoList.get(0);
		AeftdmDataSourceField dataSourceField=dataSourceFieldService.getDataSourceFieldById(firstFillRuleVo.getFieldId());
		AeftdmTemplate template=templateService.getTemplateById(dataSourceField.getTemplateId());
		//excel模板填充规则处理
		if(UtilConstant.FILE_TYPE_EXCEL_XLS.equals(template.getFileType())) {
			String[] locationMarkArr=locationMark.split(MarkConstant.MARK_SPLIT_EN_COMMA);
			Long rowNumber=Long.parseLong(locationMarkArr[0]);
			Long colNumber=Long.parseLong(locationMarkArr[1]);
			for(AeftdmFillRuleVo fillRuleVo:fillRuleVoList) {
				AeftdmFillRule fillRule=new AeftdmFillRule();
				BeanUtils.copyProperties(fillRuleVo, fillRule);
				fillRule.setFillRowNumber(rowNumber);
				fillRule.setFillColNumber(colNumber);
				fillRule.setTemplateId(dataSourceField.getTemplateId());
				fillRule.setSqlId(dataSourceField.getSqlId());
				fillRuleDao.insertFillRule(fillRule);
			}
		}//word内容替换规则处理
		else if(UtilConstant.FILE_TYPE_WORD_DOCX.equals(template.getFileType())) {
			for(AeftdmFillRuleVo fillRuleVo:fillRuleVoList) {
				AeftdmFillRule fillRule=new AeftdmFillRule();
				BeanUtils.copyProperties(fillRuleVo, fillRule);
				fillRule.setReplaceChar(locationMark);
				fillRule.setTemplateId(dataSourceField.getTemplateId());
				fillRule.setSqlId(dataSourceField.getSqlId());
				fillRuleDao.insertFillRule(fillRule);
			}
		}
		
		return true;
	}
	
	@Override
	public List<AeftdmFillRule> listFillRule(AeftdmFillRule fillRule){
		return fillRuleDao.listFillRule(fillRule);
	}
	
	@Override
	public int deleteFillRuleByMark(AeftdmFillRuleDataDto tableDataDto) {
		String[] locationMarkArr=tableDataDto.getLocationMark().split(MarkConstant.MARK_SPLIT_EN_COMMA);
		Long rowNumber=Long.parseLong(locationMarkArr[0]);
		Long colNumber=Long.parseLong(locationMarkArr[1]);
		AeftdmFillRule fillRule=new AeftdmFillRule();
		fillRule.setTemplateId(tableDataDto.getTemplateId());
		fillRule.setFillRowNumber(rowNumber);
		fillRule.setFillColNumber(colNumber);
		List<AeftdmFillRule> fillRuleList=fillRuleDao.listFillRule(fillRule);
		for(int i=0;i<fillRuleList.size();i++) {
			fillRuleDao.deleteFillRuleById(fillRuleList.get(i).getId());
		}
		return fillRuleList.size();
	}
}
