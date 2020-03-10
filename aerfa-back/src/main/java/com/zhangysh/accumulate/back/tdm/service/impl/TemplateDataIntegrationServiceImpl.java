package com.zhangysh.accumulate.back.tdm.service.impl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

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
			currentExpandRow=expandNewRowsInExcelSheet(sheet,excellStartFillRow-1+expandRow,resultMapList.size());
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
					setTypeStringCellStyle(workbook,sheet,row,cell,fillRule,fillData);
					//字符类型设置值
					setTypeStringValue(workbook,sheet,row,cell,fillRule,fillData);
				}else if (UtilConstant.SHOW_TYPE_RQCODE.equals(fillRule.getShowType())){
					setTypeRqCodeValue(workbook,sheet,row,cell,fillRule,fillData);
				}else if (UtilConstant.SHOW_TYPE_BARCODE.equals(fillRule.getShowType())){
					setTypeBarCodeValue(workbook,sheet,row,cell,fillRule,fillData);
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
	 * 根据sql定义填充word数据
	 ****/
	private void wordFillData(XWPFDocument doc,AeftdmDataSourceSql dataSourceSql,List<Map<String, Object>> resultMapList) throws Exception{

	}

	/***
	 * 根据参数拓展Sheet的数据行
	 */
	private int expandNewRowsInExcelSheet(Sheet sheet,int startRow,int dataSize){
		//数据大于1条才拓展
		if(dataSize>1){
			ExcelUtil.insertRows(sheet,startRow,dataSize-1);
		}
		return dataSize>1?dataSize-1:0;
	}

	/**
	 * 设置填充值为字符串的表格样式
	 */
	private void setTypeStringCellStyle(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData){
		CellStyle cellStyle = cell.getCellStyle();
		//当有字体大小设置的时候需要从新生成新的空的格式，不然excel全部都是这个字体了,默认样式要继承
		Font font2 = sheet.getWorkbook().createFont();
		CellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
		//设置字体样式
		if (StringUtil.isNotEmpty(fillRule.getFontName())) {
			font2.setFontName(fillRule.getFontName());
		}
		//设置加粗
		if (SysDefineConstant.DIC_COMMON_STATUS_YES.equals(fillRule.getIsBlock())) {
			font2.setBold(true);
		} else if (SysDefineConstant.DIC_COMMON_STATUS_NO.equals(fillRule.getIsBlock())) {
			font2.setBold(false);
		}
		//设置字体大小
		if(StringUtil.isNotNull(fillRule.getFontSize())){
			font2.setFontHeightInPoints(Short.valueOf(fillRule.getFontSize().toString()));
			cellStyle2.setFont(font2);
			cellStyle2.setBorderLeft(cellStyle.getBorderLeftEnum());
			cellStyle2.setBorderBottom(cellStyle.getBorderBottomEnum());
			cellStyle2.setBorderRight(cellStyle.getBorderRightEnum());
			cellStyle2.setBorderTop(cellStyle.getBorderTopEnum());
		}
		// 设置居中样式
		if(StringUtil.isNotEmpty(fillRule.getHorizontalAlign())){
			if (UtilConstant.HORIZONTAL_ALIGN_LEFT.equals(fillRule.getHorizontalAlign())) {
				cellStyle2.setAlignment(HorizontalAlignment.LEFT);
			}
			else if (UtilConstant.HORIZONTAL_ALIGN_CENTER.equals(fillRule.getHorizontalAlign())) {
				cellStyle2.setAlignment(HorizontalAlignment.CENTER);
			}
			else if (UtilConstant.HORIZONTAL_ALIGN_RIGHT.equals(fillRule.getHorizontalAlign())) {
				cellStyle2.setAlignment(HorizontalAlignment.RIGHT);
			}
		}
		cell.setCellStyle(cellStyle2);
	}

	/**
	 * 设置填充值为字符串的表格值
	 * **/
	private void setTypeStringValue(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData) throws IOException {
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

	/**
	 * 设置填充值为二维码的表格值
	 * **/
	private void setTypeRqCodeValue(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData) throws WriterException, IOException{
		if(fillData != null && !"".equals(fillData.toString().trim())) {//内容为空，则不生成二维码
			byte[] imageBytes;
			if(StringUtil.isNotEmpty(fillRule.getFormatParam())){
				JSONObject formatParamJson=JSON.parseObject(fillRule.getFormatParam());
				int width=formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_WIDTH);
				int height=formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_HEIGHT);
				imageBytes = QRCodeUtil.createQRCodeByParam(String.valueOf(fillData),width,height,0,"M");
			}else{
				imageBytes = QRCodeUtil.createDefaultQRCode(String.valueOf(fillData));
			}
			addPictureToWorkBook(imageBytes,workbook,sheet,Integer.parseInt(fillRule.getFillRowNumber()+""),Integer.parseInt(fillRule.getFillColNumber()+""));
		}
	}

	/**
	 * 设置填充值为条形码的表格值
	 * **/
	private void setTypeBarCodeValue(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData) throws WriterException, IOException{
		if(fillData != null && !"".equals(fillData.toString().trim())) {//内容为空，则不生成条形码
			byte[] imageBytes;
			if(StringUtil.isNotEmpty(fillRule.getFormatParam())){
				JSONObject formatParamJson=JSON.parseObject(fillRule.getFormatParam());
				int width=formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_WIDTH);
				int height=formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_HEIGHT);
				imageBytes = BarCodeUtil.createBarCodeByParam(String.valueOf(fillData),width,height,"M");
			}else{
				imageBytes = BarCodeUtil.createDefaultBarCode(String.valueOf(fillData));
			}
			addPictureToWorkBook(imageBytes,workbook,sheet,Integer.parseInt(fillRule.getFillRowNumber()+""),Integer.parseInt(fillRule.getFillColNumber()+""));
		}
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
			String picBase64File=HttpClientUtil.getBase64FileByHttpUrl(fullPicUrl,false);
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
		addPictureToWorkBook(imageBytes,workbook,sheet,Integer.parseInt(fillRule.getFillRowNumber()+""),Integer.parseInt(fillRule.getFillColNumber()+""));
	}

	/**
	 * 把图片填充到表单工作区
	 * @param imageBytes 图片内容字节
	 * @param workbook excel文件
	 * @param sheet excel工作区
	 * @param rowNumber 填充的行序号，此方法按exce算内会减一
	 * @param colNumber 填充的列序号，此方法按exce算内会减一
	 ***/
	private void addPictureToWorkBook(byte[] imageBytes,Workbook workbook,Sheet sheet,int rowNumber,int colNumber){
		int pictureIdx = workbook.addPicture(imageBytes, workbook.PICTURE_TYPE_PNG);
		// 创建一个顶级容器
		Drawing drawing = sheet.createDrawingPatriarch();
		CreationHelper helper = workbook.getCreationHelper();
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setRow1(rowNumber-1);
		anchor.setCol1(colNumber-1);
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		// auto-size picture relative to its top-left corner
		pict.resize();// 该方法只支持JPEG 和 PNG后缀文件
	}

}
