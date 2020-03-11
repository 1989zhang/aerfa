package com.zhangysh.accumulate.back.tdm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.constant.TdmDefineConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.util.*;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;

/**
 * tdm的excel文档工具类,不具有通用性所以放到此处
 *
 * @author zhangysh
 * @date 2020年03月10日
 */
public class MyTdmExcelUtil {

    /***
     * 根据参数拓展Sheet的数据行
     */
    public static int expandNewRowsInExcelSheet(Sheet sheet, int startRow, int dataSize){
        //数据大于1条才拓展
        if(dataSize>1){
            ExcelUtil.insertRows(sheet,startRow,dataSize-1);
        }
        return dataSize>1?dataSize-1:0;
    }

    /**
     * 设置填充值为字符串的表格样式
     */
    public static void setTypeStringCellStyle(Workbook workbook, Sheet sheet, Row row, Cell cell, AeftdmFillRule fillRule, Object fillData){
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
    public static void setTypeStringValue(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData) throws IOException {
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
    public static void setTypeRqCodeValue(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData) throws WriterException, IOException{
        if(fillData != null && !"".equals(fillData.toString().trim())) {//内容为空，则不生成二维码
            byte[] imageBytes;
            if(StringUtil.isNotEmpty(fillRule.getFormatParam())){
                JSONObject formatParamJson= JSON.parseObject(fillRule.getFormatParam());
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
    public static void setTypeBarCodeValue(Workbook workbook,Sheet sheet,Row row,Cell cell,AeftdmFillRule fillRule,Object fillData) throws WriterException, IOException{
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

    /**
     * 把图片填充到表单工作区
     * @param imageBytes 图片内容字节
     * @param workbook excel文件
     * @param sheet excel工作区
     * @param rowNumber 填充的行序号，此方法按exce算内会减一
     * @param colNumber 填充的列序号，此方法按exce算内会减一
     ***/
    public static void addPictureToWorkBook(byte[] imageBytes,Workbook workbook,Sheet sheet,int rowNumber,int colNumber){
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
