package com.zhangysh.accumulate.back.tdm.util;
import java.io.InputStream;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网上找的扩充document,修复图片插入失败问题问题,貌似有用后面研究
 *
 * @author zhangysh
 * @date 2020年03月10日
 */
public class MyXWPFDocument extends XWPFDocument {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyXWPFDocument.class);

    public MyXWPFDocument() {
        super();
    }

    public MyXWPFDocument(InputStream in) throws Exception {
        super(in);
    }

    public MyXWPFDocument(OPCPackage opcPackage) throws Exception {
        super(opcPackage);
    }

    public void createPicture(int id, String blipId, int width, int height) {
        final int emu = 9525;
        width = emu*width;
        height = emu*height;
        CTInline inline = createParagraph().createRun().getCTR().addNewDrawing().addNewInline();
        String picXml = getFormatPicXml(id,blipId,width, height);
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            LOGGER.error(xe.getMessage(), xe);
        }
        inline.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }

    /**
     * 创建图片
     ***/
    public void createPicture(XWPFRun run, int id, String blipId, int width, int height) {
        final int emu = 9525;
        width = emu*width;
        height = emu*height;

        CTInline inline = run.getCTR().addNewDrawing().addNewInline();
        String picXml = getFormatPicXml(id, blipId, width, height);
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            LOGGER.error(xe.getMessage(), xe);
        }
        inline.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }


    /***
     * 组装图片xml
     **/
    private static String getFormatPicXml(int id, String blipId, int width, int height){
        String  PICXML = ""
                + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
                + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                + "         <pic:nvPicPr>"
                + "            <pic:cNvPr id=\""+id+"\" name=\"Generated\"/>"
                + "            <pic:cNvPicPr/>" + "         </pic:nvPicPr>"
                + "         <pic:blipFill>"
                + "            <a:blip r:embed=\""+blipId+"\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
                + "            <a:stretch>"
                + "               <a:fillRect/>"
                + "            </a:stretch>" + "         </pic:blipFill>"
                + "         <pic:spPr>" + "            <a:xfrm>"
                + "               <a:off x=\"0\" y=\"0\"/>"
                + "               <a:ext cx=\""+width+"\" cy=\""+height+"\"/>"
                + "            </a:xfrm>"
                + "            <a:prstGeom prst=\"rect\">"
                + "               <a:avLst/>" + "            </a:prstGeom>"
                + "         </pic:spPr>" + "      </pic:pic>"
                + "   </a:graphicData>" + "</a:graphic>";
        return PICXML;
    }

}
