package com.zhangysh.accumulate.back.tdm.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhangysh.accumulate.back.adi.dydatasource.DynamicSqlRepository;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceFieldService;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceSqlService;
import com.zhangysh.accumulate.back.tdm.service.IFillRuleService;
import com.zhangysh.accumulate.back.tdm.service.ITemplateDataIntegrationService;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.util.ExcelUtil;
import com.zhangysh.accumulate.common.util.NativeSqlUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;

@Service
public class TemplateDataIntegrationServiceImpl implements ITemplateDataIntegrationService{

	@Autowired
	private IFillRuleService fillRuleService;
    @Autowired
    private IDataSourceSqlService dataSourceSqlService;
	@Autowired
	private DynamicSqlRepository dynamicSqlRepository;
	@Autowired
    private IDataSourceFieldService dataSourceFieldService;
    
	@Override
	public void excelDataIntegration(Long templateId,String requireParm,String templateFileFullPath,String outFileFullPath) throws Exception{
		FileOutputStream fos=new FileOutputStream(outFileFullPath);
		FileInputStream fis=new FileInputStream(new File(templateFileFullPath));
		Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(templateFileFullPath)));
		Sheet sheet = workbook.getSheetAt(0);
        if (sheet != null) {
        	//首先获取模板id下的所有数据源。
    		AeftdmDataSourceSql searchDataSourceSql =new AeftdmDataSourceSql();
    		searchDataSourceSql.setTemplateId(templateId);
    		List<AeftdmDataSourceSql> dataSourceSqlList= dataSourceSqlService.listDataSourceSql(searchDataSourceSql);
    		//格式(参数名1:参数值1,参数名2:参数值2)
    		List<String> paramList=StringUtil.getStrListBySplit(requireParm,MarkConstant.MARK_SPLIT_EN_COMMA);
    		for(AeftdmDataSourceSql dataSourceSql:dataSourceSqlList) {
    			Map<String, Object> params=new HashMap<String, Object>(); 
    			String sqlText=dataSourceSql.getSqlText();
    			List<String> requestStrList=NativeSqlUtil.getRequestStr(sqlText);
    			for(String requestStr:requestStrList) {
    				String paramName=NativeSqlUtil.getParamName(requestStr);
    				for(int i=0;i<paramList.size();i++) {
    					String[] paramStrArr=paramList.get(i).split(MarkConstant.MARK_SPLIT_EN_COLON);
    					if(paramName.equals(paramStrArr[0])) {
    						params.put(paramName, paramStrArr[1]);
    					}
    				}
    				sqlText=sqlText.replaceAll(requestStr, ":"+paramName);
    			}
    			List<Map<String, Object>> resultMapList=dynamicSqlRepository.doSelect(null, sqlText, params);
    			excelFillData(sheet,dataSourceSql,resultMapList);
    		}	 
        }
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();
	}
	
	public void wordDataIntegration(Long templateId,String requireParm,String templateFileFullPath,String outFileFullPath) throws Exception{
		FileOutputStream fos=new FileOutputStream(outFileFullPath);
		FileInputStream fis=new FileInputStream(new File(templateFileFullPath));
		HWPFDocument wordDocument = new HWPFDocument(fis);
		//首先获取模板id下的所有数据源。
		AeftdmDataSourceSql searchDataSourceSql =new AeftdmDataSourceSql();
		searchDataSourceSql.setTemplateId(templateId);
		List<AeftdmDataSourceSql> dataSourceSqlList= dataSourceSqlService.listDataSourceSql(searchDataSourceSql);
		for(AeftdmDataSourceSql dataSourceSql:dataSourceSqlList) {
			Map<String, Object> params=new HashMap<String, Object>(); 
			String sqlText=dataSourceSql.getSqlText();
			List<String> requestStrList=NativeSqlUtil.getRequestStr(sqlText);
			for(String requestStr:requestStrList) {
				String paramName=NativeSqlUtil.getParamName(requestStr);
				params.put(paramName, requireParm);
				sqlText=sqlText.replaceAll(requestStr, ":"+paramName);
			}
			List<Map<String, Object>> resultMapList=dynamicSqlRepository.doSelect(null, sqlText, params);
			wordFillData(wordDocument,dataSourceSql,resultMapList);
		}
		wordDocument.write(fos);
		wordDocument.close();
		fis.close();
		fos.close();
	}

	 
	/***
	 * 根据sql定义填充数据
	 ****/
	private void excelFillData(Sheet sheet,AeftdmDataSourceSql dataSourceSql,List<Map<String, Object>> resultMapList) throws Exception{
		AeftdmDataSourceField searchDataSourceField =new AeftdmDataSourceField();
		searchDataSourceField.setSqlId(dataSourceSql.getId());
		List<AeftdmDataSourceField> dataSourceFieldList=dataSourceFieldService.listDataSourceField(searchDataSourceField);
		int dataSourceFieldSize=dataSourceFieldList.size();
		for(int filedSize=0;filedSize<dataSourceFieldSize;filedSize++) {
			AeftdmDataSourceField dataSourceField=dataSourceFieldList.get(filedSize);
			AeftdmFillRule searchFillRule=new AeftdmFillRule();
			searchFillRule.setFieldId(dataSourceField.getId());
			List<AeftdmFillRule> fillRuleList=fillRuleService.listFillRule(searchFillRule);
			
			AeftdmFillRule fillRule;
			if (fillRuleList == null || fillRuleList.size()==0) {
				continue;//sql查询的多余字段，先不管填充规则
			}else {
				fillRule=fillRuleList.get(0);
			}

			// 数据,后面考虑多条数据填充情况
			String fieldName=dataSourceField.getFieldName();
			Object fillData = resultMapList.size()==0?"":resultMapList.get(0).get(fieldName);
			//excel行数列数从0开始算，所以要减1
			int x = Integer.parseInt(fillRule.getFillRowNumber()+"")-1;
			int y = Integer.parseInt(fillRule.getFillColNumber()+"")-1;
			Row row = sheet.getRow(x) == null ? sheet.createRow(x) : sheet.getRow(x);
			Cell cell = row.getCell(y) == null ? row.createCell(y) : row.getCell(y);
			// 设置居中样式
			CellStyle cellStyle = cell.getCellStyle();
			if(StringUtil.isNotEmpty(fillRule.getHorizontalAlign())){
				if (UtilConstant.HORIZONTAL_ALIGN_LEFT.equals(fillRule.getHorizontalAlign())) {
					cellStyle.setAlignment(HorizontalAlignment.LEFT);
				}
				else if (UtilConstant.HORIZONTAL_ALIGN_CENTER.equals(fillRule.getHorizontalAlign())) {
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
				}
				else if (UtilConstant.HORIZONTAL_ALIGN_RIGHT.equals(fillRule.getHorizontalAlign())) {
					cellStyle.setAlignment(HorizontalAlignment.RIGHT);
				}
			}
			// 按格式填充数据
			if (UtilConstant.SHOW_TYPE_STRING.equals(fillRule.getShowType())) {
				String fillDataStr = String.valueOf(fillData);
				cell.setCellValue(fillDataStr);
				//设置行高,根据内容长度和cell宽度作比较，确定行高
				int mergerCellRegionCol = 0;
				int columnWidth = 0;
				//此处判断cell是否有合并列，如果有返回合并的列数，用于计算合并后的列宽（合并单元格列宽=所合并的那几列的列宽之和）
			    mergerCellRegionCol = ExcelUtil.getMergerCellRegionCol(sheet,cell.getRow().getRowNum(),cell.getColumnIndex());
				
				if(mergerCellRegionCol > 1) {//有合并列
					for(int i=cell.getColumnIndex();i<cell.getColumnIndex()+mergerCellRegionCol;i++) {
						columnWidth += sheet.getColumnWidth(i);
					}
				}else {
				     columnWidth = sheet.getColumnWidth(cell.getColumnIndex()); //单位不是像素，1/256个字符宽度
				}
				int zfs = 0;//字符数
				zfs = fillDataStr.getBytes(UtilConstant.CHARSET_GBK).length;
				double dataLength = (zfs * (8 - 1) + 5) / (8 - 1);//字符宽度
				int bs = 0;
				bs = (int) Math.ceil(dataLength / ((columnWidth / 256 ) == 0 ? dataLength : (columnWidth / 256 )));
				if(bs > 1) {
					row.setHeight((short)(bs * 300));//行高按15*20算
				}
				//处理字符串本身有换行的行高计算(换了几行，就设置几行的高度+行内字符串换行的高度)
				if(fillDataStr != null && (fillDataStr.contains("\r") || fillDataStr.contains("\n"))) {
					int zbs = 0;
					String[] len = null;
					if(fillDataStr.contains("\r")) {
						len = fillDataStr.split("\r");
					}else {
						len = fillDataStr.split("\n");
					}
					for (String string : len) {
						dataLength = (string.getBytes(UtilConstant.CHARSET_GBK).length * (8 - 1) + 5) / (8 - 1);
						bs = (int) Math.ceil(dataLength / ((columnWidth / 256 ) == 0 ? dataLength : (columnWidth / 256 )));
						if(bs >= 2) {//行内字符串过长，换行了，总行高自然增加
							zbs += bs;
						}else {
							zbs += 1;
						}
					}
					//最终的换行数zbs=单行字符串的换行数之和
					row.setHeight((short)(zbs * 220));//行高按11*20算
				}
			}
	    }
	}
	
	/***
	 * 根据sql定义填充word数据
	 ****/
	private void wordFillData(HWPFDocument doc,AeftdmDataSourceSql dataSourceSql,List<Map<String, Object>> resultMapList) throws Exception{
		AeftdmDataSourceField searchDataSourceField =new AeftdmDataSourceField();
		searchDataSourceField.setSqlId(dataSourceSql.getId());
		List<AeftdmDataSourceField> dataSourceFieldList=dataSourceFieldService.listDataSourceField(searchDataSourceField);
		int dataSourceFieldSize=dataSourceFieldList.size();
		for(int filedSize=0;filedSize<dataSourceFieldSize;filedSize++) {
			AeftdmDataSourceField dataSourceField=dataSourceFieldList.get(filedSize);
			AeftdmFillRule searchFillRule=new AeftdmFillRule();
			searchFillRule.setFieldId(dataSourceField.getId());
			List<AeftdmFillRule> fillRuleList=fillRuleService.listFillRule(searchFillRule);

			if (fillRuleList == null || fillRuleList.size()==0) {
				continue;//sql查询的多余字段，先不管填充规则
			}
			
			Map<String, String> replaceMap=new HashMap<String, String>();
			for(int i=0;i<fillRuleList.size();i++) {
				// 数据,后面考虑多条数据填充情况
				String fieldName=dataSourceField.getFieldName();
				AeftdmFillRule fillRule=fillRuleList.get(i);
				String replaceChar=MarkConstant.MARK_SPLIT_EN_DOLLAR+MarkConstant.MARK_SPLIT_EN_BRACE_LEFT+fillRule.getReplaceChar()+MarkConstant.MARK_SPLIT_EN_BRACE_RIGHT;
				Object fillData =resultMapList.get(0).get(fieldName);
				String fillDataStr = String.valueOf(fillData);
				replaceMap.put(replaceChar, fillDataStr);
			}
			
			Range range = doc.getRange(); 
			for (Map.Entry<String, String> entry : replaceMap.entrySet()) {  
                range.replaceText(entry.getKey(), entry.getValue());  
            }  
		}
	}
}
