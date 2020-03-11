package com.zhangysh.accumulate.back.tdm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.TdmDefineConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.util.QRCodeUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.util.List;
import java.util.Map;


/**
 * tdm的word文档工具类,不具有通用性
 *
 * @author zhangysh
 * @date 2020年03月10日
 * */
public class MyWordUtil {
    /**
     * 根据条件替换值
     */
    public static void changeValue(XWPFParagraph paragraph, XWPFRun currentRun, String replaceAllChar, AeftdmFillRule fillRule, Object fillData){
        try {
            if (UtilConstant.SHOW_TYPE_STRING.equals(fillRule.getShowType())) {
                if(fillData != null && !"".equals(fillData.toString().trim())) {//内容为空
                    currentRun.setText("",0);//替换字符清空
                    currentRun.setText(fillData.toString(),0);
                }
            }else if (UtilConstant.SHOW_TYPE_RQCODE.equals(fillRule.getShowType())){
                if(fillData != null && !"".equals(fillData.toString().trim())) {//内容为空，则不生成二维码
                    byte[] imageBytes;int width=100;int height=100;
                    if (StringUtil.isNotEmpty(fillRule.getFormatParam())) {
                        JSONObject formatParamJson = JSON.parseObject(fillRule.getFormatParam());
                        width= formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_WIDTH);
                        height = formatParamJson.getInteger(TdmDefineConstant.FORMAT_PARAM_HEIGHT);
                        imageBytes = QRCodeUtil.createQRCodeByParam(String.valueOf(fillData), width, height, 0, "M");
                    } else {
                        imageBytes = QRCodeUtil.createDefaultQRCode(String.valueOf(fillData));
                    }
                    String   picId;
                    picId = currentRun.getDocument().addPictureData(imageBytes,getXwpfImageType(UtilConstant.FILE_TYPE_PIC_PNG));
                    new MyXWPFDocument().createPicture(currentRun,  currentRun.getDocument().getNextPicNameNumber(getXwpfImageType(UtilConstant.FILE_TYPE_PIC_PNG)),picId, width, height);
                    currentRun.setText("",0);//替换字符清空
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * XWPFDocument 里面图片格式和数字转换
     **/
    private static Integer getXwpfImageType(String type) {
        if ("JPG".equalsIgnoreCase(type) || "JPEG".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_JPEG;
        }
        if ("GIF".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_GIF;
        }
        if ("BMP".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_GIF;
        }
        if ("PNG".equalsIgnoreCase(type)) {
            return XWPFDocument.PICTURE_TYPE_PNG;
        }
        return XWPFDocument.PICTURE_TYPE_JPEG;
    }


    public static void dealWithFirstRow(XWPFTable table, int sourceRowIndex,Map<String, Object> valueMap){
        XWPFTableRow sourceRow= table.getRow(sourceRowIndex);
        for (int i = 0; i <= sourceRow.getTableCells().size(); i++) {
            XWPFTableCell sourceCell = sourceRow.getCell(i);
            if(sourceCell!=null){
                for (XWPFParagraph paragraph : sourceCell.getParagraphs()) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        String key=text.substring(2,text.length()-1);
                        String valueCell=valueMap.get(key)+"";
                        run.setText("",0);//替换字符清空
                        run.setText(valueCell.toString(),0);
                    }
                }
            }
        }
    }
    /**
     * 插入行
     *
     * @param table
     * @param sourceRowIndex 源行
     * @param resultMapList 插入的結果
     */
    public static void insertRows(XWPFTable table, int sourceRowIndex,List<Map<String, Object>> resultMapList) {

        int insertNum=resultMapList.size()-1;
        if (insertNum < 1) {
            return;
        }
        for (int x = 0; x < insertNum; x++) {
            table.createRow();
        }
        List<XWPFTableRow> rows = table.getRows();
        for(int x=sourceRowIndex+1; x<insertNum+sourceRowIndex+1 ;x++){
            copyRow(rows.get(sourceRowIndex),rows.get(x),resultMapList.get(x-1));
        }
        dealWithFirstRow(table,sourceRowIndex,resultMapList.get(0));
    }


    /**
     * 拷贝word里面表格的行
     * @param sourceRow 源行
     * @param targetRow 目标行
     */
    private static void copyRow(XWPFTableRow sourceRow,XWPFTableRow targetRow,Map<String,Object> valueMap) {
        targetRow.setHeight(sourceRow.getHeight());
        for (int i = 0; i <= sourceRow.getTableCells().size(); i++) {
            XWPFTableCell sourceCell = sourceRow.getCell(i);
            XWPFTableCell targetCell = targetRow.getCell(i);
            if(sourceCell!=null){
                String text=sourceCell.getText();
                String key=text.substring(2,text.length()-1);
                String valueCell=valueMap.get(key)+"";
                //拷贝单元格，包括内容和样式
                copyCell(sourceCell,targetCell,valueCell);
            }

        }
    }

    /**
     * 拷贝word里面表格的单元
     * @param sourceCell 源单元格
     * @param targetCell 拷贝目标单元格
     */
    private static void copyCell(XWPFTableCell sourceCell,XWPFTableCell targetCell,String cellValue) {
        List<XWPFParagraph> targetParagraphList = targetCell.getParagraphs();
        for (int x = targetParagraphList.size() - 1; x >= 0; x--) {
            targetCell.removeParagraph(x);
        }

        for (XWPFParagraph paragraph : sourceCell.getParagraphs()) {
            XWPFParagraph newParagraph = targetCell.addParagraph();
            newParagraph.setAlignment(paragraph.getAlignment());
            newParagraph.setFontAlignment(paragraph.getFontAlignment());
            newParagraph.setFirstLineIndent(paragraph.getFirstLineIndent());
            newParagraph.setVerticalAlignment(paragraph.getVerticalAlignment());

            for (XWPFRun run : paragraph.getRuns()) {
                XWPFRun newRun = newParagraph.createRun();
                CTText ctTexts = run.getCTR().getTArray(0);
                newRun.setFontSize(10);
                newRun.setText(cellValue, 0);
            }
        }
    }
}
