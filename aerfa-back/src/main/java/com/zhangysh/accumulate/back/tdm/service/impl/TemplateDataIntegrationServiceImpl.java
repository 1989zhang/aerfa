package com.zhangysh.accumulate.back.tdm.service.impl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhangysh.accumulate.back.tdm.util.MyTdmExcelUtil;
import com.zhangysh.accumulate.back.tdm.util.MyTdmWordUtil;
import org.apache.poi.ss.usermodel.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.zhangysh.accumulate.back.sys.service.IConfigDataService;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.constant.TdmDefineConstant;
import com.zhangysh.accumulate.common.util.*;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhangysh.accumulate.back.adi.dydatasource.DynamicSqlRepository;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceFieldService;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceSqlService;
import com.zhangysh.accumulate.back.tdm.service.IFillRuleService;
import com.zhangysh.accumulate.back.tdm.service.ITemplateDataIntegrationService;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
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
	@Autowired
	private IConfigDataService configDataService;

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
    		int expandRow=0;
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
				int currentExpandRow=excelFillData(workbook,sheet,dataSourceSql,resultMapList,expandRow);
				expandRow=expandRow+currentExpandRow;
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
		XWPFDocument wordDocument = new XWPFDocument(fis);
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
			wordFillData(wordDocument,dataSourceSql,resultMapList);
		}
		wordDocument.write(fos);
		wordDocument.close();
		fis.close();
		fos.close();
	}

	 
	/***
	 * 根据sql定义填充数据，并返回本次填充数据后的偏移量
	 * @param sheet excel的第一个工作区
	 * @param dataSourceSql 定义的数据源对象
	 * @param resultMapList sql查询结果集合
	 * @param expandRow 行拓展偏移量
	 * @return 本次拓展行偏移量
	 ****/
	private int excelFillData(Workbook workbook,Sheet sheet,AeftdmDataSourceSql dataSourceSql,List<Map<String, Object>> resultMapList,int expandRow) throws Exception{
		//返回的行位移偏移量
		int currentExpandRow=0;
		//查询出列的基本数据
		AeftdmDataSourceField searchDataSourceField =new AeftdmDataSourceField();
		searchDataSourceField.setSqlId(dataSourceSql.getId());
		List<AeftdmDataSourceField> dataSourceFieldList=dataSourceFieldService.listDataSourceField(searchDataSourceField);
		int dataSourceFieldSize=dataSourceFieldList.size();

		//需要进行数据拓展的查询sql
		if(TdmDefineConstant.DATA_FILL_TYPE_EXPAND.equals(dataSourceSql.getFillType())){
			//先拓展excel,先获取起始的行
			AeftdmFillRule startRowFillRule = new AeftdmFillRule();
			Integer excellStartFillRow=0;
			for(int filedSize=0;filedSize<dataSourceFieldSize;filedSize++) {
				AeftdmDataSourceField dataSourceField = dataSourceFieldList.get(filedSize);
				AeftdmFillRule searchFillRule = new AeftdmFillRule();
				searchFillRule.setFieldId(dataSourceField.getId());
				List<AeftdmFillRule> fillRuleList = fillRuleService.listFillRule(searchFillRule);
				if(StringUtil.isNotEmpty(fillRuleList)){
					excellStartFillRow=Integer.parseInt(fillRuleList.get(0).getFillRowNumber()+"");
					break;
				}
			}
			currentExpandRow=MyTdmExcelUtil.expandNewRowsInExcelSheet(sheet,excellStartFillRow-1+expandRow,resultMapList.size());
		}
		//填充数据判断
		List<Map<String, Object>> fillDataMapList=new ArrayList<Map<String, Object>>();
		if(TdmDefineConstant.DATA_FILL_TYPE_FREE.equals(dataSourceSql.getFillType())){
			fillDataMapList.add(resultMapList.get(0));
		}else{
			fillDataMapList=resultMapList;
		}
		//再填充数据
		for(int dataSize=0;dataSize<fillDataMapList.size();dataSize++){
			//填充每个字段
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

				String fieldName=dataSourceField.getFieldName();
				Object fillData = fillDataMapList.get(dataSize).get(fieldName);
				//excel行数列数从0开始算，所以要减1
				int x = Integer.parseInt(fillRule.getFillRowNumber()+"")-1+dataSize+expandRow;
				int y = Integer.parseInt(fillRule.getFillColNumber()+"")-1;
				Row row = sheet.getRow(x) == null ? sheet.createRow(x) : sheet.getRow(x);
				Cell cell = row.getCell(y) == null ? row.createCell(y) : row.getCell(y);

				// 按格式填充数据
				if (UtilConstant.SHOW_TYPE_STRING.equals(fillRule.getShowType())) {
					// 字符类型要设置样式
					MyTdmExcelUtil.setTypeStringCellStyle(workbook,sheet,row,cell,fillRule,fillData);
					//字符类型设置值
					MyTdmExcelUtil.setTypeStringValue(workbook,sheet,row,cell,fillRule,fillData);
				}else if (UtilConstant.SHOW_TYPE_RQCODE.equals(fillRule.getShowType())){
					MyTdmExcelUtil.setTypeRqCodeValue(workbook,sheet,row,cell,fillRule,fillData);
				}else if (UtilConstant.SHOW_TYPE_BARCODE.equals(fillRule.getShowType())){
					MyTdmExcelUtil.setTypeBarCodeValue(workbook,sheet,row,cell,fillRule,fillData);
				}else if (UtilConstant.SHOW_TYPE_IMAGE_URL_FILE.equals(fillRule.getShowType())
						||UtilConstant.SHOW_TYPE_IMAGE_FTP_FILE.equals(fillRule.getShowType())
						||UtilConstant.SHOW_TYPE_IMAGE_DIR_FILE.equals(fillRule.getShowType())){
					setTypeImageValueByUfs(workbook,sheet,row,cell,fillRule,fillData,fillRule.getShowType());
				}

			}
		}
		return currentExpandRow;
	}


	/***
	 * 通过ufs uploadFileSystem不同实现方式获取并生成图片
	 * @param workbook excel文件
	 * @param sheet excel工作区
	 * @param row 要填充数据所在行
	 * @param cell 要填充数据所在单元
	 * @param fillRule 填充规则对象
	 * @param fillData 要填充的数据值
	 * @param showType 数据来源或展示类型
	 **/
	private void setTypeImageValueByUfs(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData,String showType) throws IOException{
		byte[] imageBytes = null;
		//把imageUrlFile从nginx取也归到ufs,毕竟是从ufs上传的
		if(UtilConstant.SHOW_TYPE_IMAGE_URL_FILE.equals(showType)){
			AefsysConfigData picIpAddressConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_SYS_PIC_IP_ADDRESS);
			String fullPicUrl=picIpAddressConfigData.getDataValue()+fillData;
			String picBase64File= HttpClientUtil.getBase64FileByHttpUrl(fullPicUrl,false);
			byte[] picByteImg=InputStreamUtil.Base64ToByte(picBase64File);
			if(StringUtil.isNotEmpty(fillRule.getFormatParam())){
				JSONObject formatParamJson=JSON.parseObject(fillRule.getFormatParam());
				int width=formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_WIDTH);
				int height=formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_HEIGHT);
				BufferedImage picBufferedImg=ImageUtil.byte2BufferedImage(picByteImg);
				imageBytes = ImageUtil.zoomImage(picBufferedImg,width,height);
			}else{
				imageBytes=picByteImg;
			}
		}else{//其他的ufs系统Action里面取

		}
		MyTdmExcelUtil.addPictureToWorkBook(imageBytes,workbook,sheet,Integer.parseInt(fillRule.getFillRowNumber()+""),Integer.parseInt(fillRule.getFillColNumber()+""));
	}

	
	/***
	 * 根据sql定义填充word数据
	 ****/
	private void wordFillData(XWPFDocument doc,AeftdmDataSourceSql dataSourceSql,List<Map<String, Object>> resultMapList) throws Exception{
		//查询出列的基本数据
		AeftdmDataSourceField searchDataSourceField =new AeftdmDataSourceField();
		searchDataSourceField.setSqlId(dataSourceSql.getId());
		List<AeftdmDataSourceField> dataSourceFieldList=dataSourceFieldService.listDataSourceField(searchDataSourceField);
		int dataSourceFieldSize=dataSourceFieldList.size();
		AeftdmFillRule searchFillRule=new AeftdmFillRule();
		searchFillRule.setSqlId(dataSourceSql.getId());
		Map<String, AeftdmFillRule> fillRullMap=fillRuleService.getFillRuleMap(searchFillRule);

		//只需替换单个字符有可能含表格的
		if(TdmDefineConstant.DATA_FILL_TYPE_FREE.equals(dataSourceSql.getFillType())){
			Map<String, Object> resultMap=resultMapList.get(0);
			List<XWPFParagraph> list=doc.getParagraphs();
			// 遍历段落
			for (XWPFParagraph paragraph : list) {
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					if(MyTdmWordUtil.checkRunCanReplace(run)){
						String key=MyTdmWordUtil.getRunReplaceChar(run);
						if(StringUtil.isNotNull(fillRullMap.get(key))&&StringUtil.isNotNull(resultMap.get(key))){
							MyTdmWordUtil.changeValue(run,fillRullMap.get(key),resultMap.get(key));
						}
					}
				}
			}
			// 遍历表
			for (XWPFTable table : doc.getTables()) {
				for (XWPFTableRow row : table.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (XWPFParagraph paragraph : cell.getParagraphs()) {
							List<XWPFRun> runs = paragraph.getRuns();
							for (XWPFRun run : runs) {
								if(MyTdmWordUtil.checkRunCanReplace(run)){
									String key=MyTdmWordUtil.getRunReplaceChar(run);
									if(StringUtil.isNotNull(fillRullMap.get(key))&&StringUtil.isNotNull(resultMap.get(key))) {
										MyTdmWordUtil.changeValue(run, fillRullMap.get(key), resultMap.get(key));
									}
								}
							}
						}
					}
				}
			}
		//要拓展填充表格的
		}else if (TdmDefineConstant.DATA_FILL_TYPE_EXPAND.equals(dataSourceSql.getFillType())){
			//要填充扩展的一定是表格
			// 遍历表
			for (XWPFTable table : doc.getTables()) {
				MyTdmWordUtil.dealWithDocTableData(table,resultMapList,fillRullMap);
			}
		}
	}
}
