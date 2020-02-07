package com.zhangysh.accumulate.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * Excel通用工具类
 */
public class ExcelUtil {

	/**
     * 读取Excel文档结构，用于界面显示
     * @param excelFilePath 文件路径 
     * @return 获取到的内容
     */
    public static List<Map<String, Object>> readExcelContent(String excelFilePath) {
    	List<Map<String, Object>> sheetMapList = new ArrayList<Map<String, Object>>();
		try {
			Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(excelFilePath)));
			for(int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++){
	            Sheet sheet = workbook.getSheetAt(sheetIndex);
	            if (sheet != null) {
	            	Map<String, Object> sheetMap = new HashMap<String, Object>();
	            	//工作区sheet
	            	sheetMap.put("sheetName", sheet.getSheetName());
	            	sheetMap.put("sheetIndex",sheetIndex);
	            	int columnNumber = 0;//最大行
	            	List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
	            	//获取行列内容,后面再加5列空的内容
	            	for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum() +5; rowIndex++) {
	            		 Row row = sheet.getRow(rowIndex);
	                     Map<String, Object> rowMap = new HashMap<String, Object>();
	                     rowMap.put("index",rowIndex);
	                     if (row != null) {// 如果行不为空
	                    	 rowMap.put("height", row.getHeight() /10);//?
	                         List<Map<String, Object>> cellList = new ArrayList<Map<String, Object>>();
	                         int oneRowCellNumber = 0;
	                         for (int cellIndex = 0; cellIndex <= row.getLastCellNum(); cellIndex++) { // 循环该行的每一个单元格
	                        	 Cell cell = row.getCell(cellIndex);
	                             Map<String, Object> cellMap = new HashMap<String, Object>();
	                             cellMap.put("index",cellIndex);
	                             int width = sheet.getColumnWidth(cellIndex)/2;//?
	                             cellMap.put("width", width);
	                             boolean isDisplay = true;//是否显示判断标记
	                             int cellRegionCol = getMergerCellRegionCol(sheet, rowIndex, cellIndex); // 合并的列（colspan）
	                             if(cellRegionCol ==-1){
	                                 isDisplay = false;
	                             }else if(cellRegionCol > 0 ){
	                                 cellMap.put("columnSpan",cellRegionCol);
	                             }
	                             int cellRegionRow = getMergerCellRegionRow(sheet, rowIndex, cellIndex);// 合并的行（rowspan）
	                             if(cellRegionRow == -1 ){
	                                 isDisplay = false;
	                             }else if(cellRegionRow > 0){
	                                 cellMap.put("rowSpan",cellRegionRow);
	                             }
	                             cellMap.put("display",isDisplay);
	                             if (cell != null) {
	                            	 if(cell.getCellTypeEnum() != CellType.BLANK && cellRegionCol != -1){
	                                     oneRowCellNumber = Math.max(oneRowCellNumber + cellRegionCol,cellIndex);
	                                 }
	                            	 cell.setCellType(CellType.STRING);
	                                 cellMap.put("value", cell.getStringCellValue());
	                                 CellStyle cellStyle = cell.getCellStyle();
	                                 Font font = workbook.getFontAt(cellStyle.getFontIndex());
	                                 boolean fontBold = font.getBold(); // 字体加粗
	                                 if(fontBold) {
	                                	 cellMap.put("fontWeight","bold"); 
	                                 }else {
	                                	 cellMap.put("fontWeight","normal");
	                                 }
	                                 int fontHeight = font.getFontHeight()/2; // 字体大小
	                                 cellMap.put("fontSize",fontHeight);
	                                 //左右上线居中样式
	                                 String align = "left";
	                                 switch ( cellStyle.getAlignmentEnum()) {
	                                 	 case LEFT:
	                                 		align = "left";
	                                        break;
	                                 	 case CENTER:
	                                         align = "center";
	                                         break;
	                                     case RIGHT:
	                                         align = "right";
	                                         break;
	                                     default:
	                                         break;
	                                 }
	                                 cellMap.put("align",align);
	                                 String vAlign = "middle";
	                                 switch (cellStyle.getVerticalAlignmentEnum()) {
	                                 	 case TOP:
	                                 		 vAlign = "top";
											 break;
	                                 	 case CENTER:
	                                 		 vAlign = "middle";
	                                 		 break;
	                                 	 case BOTTOM:
	                                 		 vAlign = "bottom";
	                                 		 break;
	                                 	 default:
	                                 		 break;
	                                 }
	                                 cellMap.put("valign",vAlign);
	                             }
	                             cellList.add(cellMap);
	                         }
	                         columnNumber = Math.max(oneRowCellNumber, columnNumber);
	                         rowMap.put("cells", cellList);
	                     }else {
	                    	 rowMap.put("cells", null);
	                     }
	                     rowList.add(rowMap);
	            	}
	            	sheetMap.put("rows", rowList);
	            	//补充其他属性
	            	sheetMap.put("columnNumber",columnNumber + 5);
	                List<Integer> columnWidth = new ArrayList<Integer>();
	                for(int x = 0 ; x < columnNumber; x ++){
	                    columnWidth.add(sheet.getColumnWidth(x));
	                }
	                sheetMap.put("columnWidth",columnWidth);
	                sheetMapList.add(sheetMap);
	            }
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return sheetMapList;
    }
    

    /**
     * 判断单元格是否是合并的单格，如果是，获取其合并的列数。
     *
     * @param sheet   工作表
     * @param cellRow 被判断的单元格的行号
     * @param cellCol 被判断的单元格的列号
     * @return
     * @throws IOException
     */
    public static int getMergerCellRegionCol(Sheet sheet, int cellRow, int cellCol) throws IOException {
        int retVal = 0;
        for (CellRangeAddress cra : sheet.getMergedRegions()) {
            int firstRow = cra.getFirstRow();  // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
            if (cellRow >= firstRow && cellRow <= lastRow && cellCol >= firstCol && cellCol <= lastCol) { // 判断该单元格是否是在合并单元格中
               if(cellRow == firstRow && cellCol == firstCol){
                   retVal = lastCol - firstCol + 1; // 得到合并的列数
               }else{
                   retVal = -1;
               }
               break;
            }
        }
        return retVal;
    }

    /**
     * 判断单元格是否是合并的单格，如果是，获取其合并的行数。
     *
     * @param sheet   表单
     * @param cellRow 被判断的单元格的行号
     * @param cellCol 被判断的单元格的列号
     * @return
     * @throws IOException
     */
    private static int getMergerCellRegionRow(Sheet sheet, int cellRow, int cellCol) throws IOException {
        int retVal = 0;
        for (CellRangeAddress cra : sheet.getMergedRegions()) {
            int firstRow = cra.getFirstRow();  // 合并单元格CELL起始行
            int firstCol = cra.getFirstColumn(); // 合并单元格CELL起始列
            int lastRow = cra.getLastRow(); // 合并单元格CELL结束行
            int lastCol = cra.getLastColumn(); // 合并单元格CELL结束列
            if (cellRow >= firstRow && cellRow <= lastRow && cellCol >= firstCol && cellCol <= lastCol) { // 判断该单元格是否是在合并单元格中
                if(cellRow == firstRow && cellCol == firstCol){
                    retVal = lastRow - firstRow + 1; // 得到合并的列数
                }else{
                    retVal = -1;
                }
                break;
            }
        }
        return retVal;
    }
}
