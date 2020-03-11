package com.zhangysh.accumulate.back.tdm.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.TdmDefineConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.util.QRCodeUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import org.apache.poi.xwpf.usermodel.*;
import java.util.List;
import java.util.Map;


/**
 * tdm的word文档工具类,不具有通用性所以放到此处
 *
 * @author zhangysh
 * @date 2020年03月10日
 * */
public class MyTdmWordUtil {

    /**
     * 替换文本的当前值
     * @param currentRun 最小的操作单位
     * @param fillRule 填充规则
     * @param fillData 填充值
     */
    public static void changeValue(XWPFRun currentRun,AeftdmFillRule fillRule, Object fillData){
        try {
            if (UtilConstant.SHOW_TYPE_STRING.equals(fillRule.getShowType())) {
                if(fillData != null && !"".equals(fillData.toString().trim())) {//内容为空
                    currentRun.setFontSize(Integer.valueOf(fillRule.getFontSize().toString()));
                    currentRun.setFontFamily(fillRule.getFontName());//字体
                    currentRun.setText("",0);//原替换字符清空
                    currentRun.setText(String.valueOf(fillData),0);
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
                    currentRun.setText("",0);//原替换字符清空
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *填充整个表格对应的数据
     * @param table 整个表格对象
     * @param resultMapList Sql查询结果集合
     * @param fillRullMap 填充规则Map,key为替换文字
     ***/
    public static void dealWithDocTableData(XWPFTable table,List<Map<String,Object>> resultMapList,Map<String, AeftdmFillRule> fillRullMap){
        int startIndex = getFirstRowIndex(table);
        if (resultMapList.size() < 1 || startIndex<0) {
            return;
        } else if(resultMapList.size()==1) {
            fillWordTableFirstRow(table, startIndex, resultMapList.get(0), fillRullMap);
        }else if(resultMapList.size()>1){
            //先拷贝赋值,再处理第一条,不然无法根据key去get到map的value
            fillWordTableOtherRow(table, startIndex, resultMapList, fillRullMap);
            fillWordTableFirstRow(table, startIndex, resultMapList.get(0), fillRullMap);
        }
    }

    /**
     * 判断最小的操作单位是否需要替换
     * @param run 最小的操作单位
     **/
    public static boolean checkRunCanReplace(XWPFRun run){
        String text = run.getText(0);
        if(     StringUtil.isNotEmpty(text)
                && text.startsWith(MarkConstant.MARK_SPLIT_EN_DOLLAR+MarkConstant.MARK_SPLIT_EN_BRACE_LEFT)
                && text.endsWith(MarkConstant.MARK_SPLIT_EN_BRACE_RIGHT)){
            return true;
        }
        return false;
    }

    /**
     * 获取最小单元的替换字符
     * @param run 最小的操作单位
     **/
    public static String getRunReplaceChar(XWPFRun run){
        String text = run.getText(0);
        String key=text.replaceAll(MarkConstant.MARK_SPLIT_EN_DOLLAR_TRANS+MarkConstant.MARK_SPLIT_EN_BRACE_LEFT_TRANS,"").replaceAll(MarkConstant.MARK_SPLIT_EN_BRACE_RIGHT_TRANS,"");
        return key;
    }


    /**
     * XWPFDocument 里面图片格式和数字转换
     * @param type 字符图片格式
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

    /***
     * 返回表格需要替换的起始值,第一个cell匹配到${}即为开始,未匹配到返回-1
     * @param table 整个表格对象
     **/
    private static int getFirstRowIndex(XWPFTable table){
        List<XWPFTableRow> listTableRows= table.getRows();
        for(int i=0;i<listTableRows.size();i++){
            XWPFTableRow tableRow=listTableRows.get(i);
            for(int j=0;j<tableRow.getTableCells().size();j++){
                XWPFTableCell tableCell = tableRow.getCell(j);
                if(tableCell!=null){
                    for (XWPFParagraph paragraph : tableCell.getParagraphs()) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            if(checkRunCanReplace(run)){
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 处理word需拓展的表格的第一行需要插入的值,填入规则和rows的cell一一对应
     * @param table 整个表格对象
     * @param startIndex 第一行的索引
     * @param firstRowValueMap 第一行的值
     * @param fillRullMap 填充规则Map,key为替换文字
     **/
    private static void fillWordTableFirstRow(XWPFTable table,int startIndex,Map<String, Object> firstRowValueMap,Map<String, AeftdmFillRule> fillRullMap){
        XWPFTableRow tableRow=table.getRow(startIndex);
        if(StringUtil.isNotNull(tableRow)){
            for(int i=0;i<tableRow.getTableCells().size();i++){
                XWPFTableCell tableCell = tableRow.getCell(i);
                if(StringUtil.isNotNull(tableCell)){
                    for (XWPFParagraph paragraph : tableCell.getParagraphs()) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            if(checkRunCanReplace(run)){
                                String key=getRunReplaceChar(run);
                                changeValue(run,fillRullMap.get(key),firstRowValueMap.get(key));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 处理表格的其他列,默认第一列是不需拓展的,所以和firstRow单独列开来
     * @param table 整个表格对象
     * @param startIndex 第一行的索引用做复制源
     * @param resultMapList Sql查询结果集合
     * @param fillRullMap 填充规则Map,key为替换文字
     ***/
    private static void fillWordTableOtherRow(XWPFTable table,int startIndex,List<Map<String,Object>> resultMapList,Map<String, AeftdmFillRule> fillRullMap){
        int insertNum=resultMapList.size()-1;
        for (int x = 0; x < insertNum; x++) {
            table.createRow();
        }
        List<XWPFTableRow> rows = table.getRows();
        for(int x=startIndex+1; x<insertNum+startIndex+1 ;x++){
            copyRow(rows.get(startIndex),rows.get(x),resultMapList.get(x-1),fillRullMap);
        }
    }

    /**
     * 拷贝word里面表格的行
     * @param sourceRow 源行
     * @param targetRow 目标行
     * @param valueMap 目标行的值
     */
    private static void copyRow(XWPFTableRow sourceRow,XWPFTableRow targetRow,Map<String,Object> valueMap,Map<String, AeftdmFillRule> fillRullMap) {
        targetRow.setHeight(sourceRow.getHeight());
        for (int i = 0; i <= sourceRow.getTableCells().size(); i++) {
            XWPFTableCell sourceCell = sourceRow.getCell(i);
            XWPFTableCell targetCell = targetRow.getCell(i);
            if(sourceCell!=null){
                //拷贝单元格，包括内容和样式
                copyCell(sourceCell,targetCell,valueMap,fillRullMap);
            }
        }
    }

    /**
     * 拷贝word里面表格的单元
     * @param sourceCell 源单元格
     * @param targetCell 拷贝目标单元格
     * @param valueMap 目标行的值集合
     */
    private static void copyCell(XWPFTableCell sourceCell,XWPFTableCell targetCell,Map<String,Object> valueMap,Map<String, AeftdmFillRule> fillRullMap) {
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
                 String key=getRunReplaceChar(run);
                 changeValue(newRun,fillRullMap.get(key),valueMap.get(key));
            }
        }
    }

}
