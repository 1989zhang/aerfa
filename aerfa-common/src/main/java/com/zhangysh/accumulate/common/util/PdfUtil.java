package com.zhangysh.accumulate.common.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
/*****
 * pdf文件相关方法
 * @author zhangysh
 * @date 2019年7月6日
 *****/
public class PdfUtil {


	/****
	 * 给pdf添加水印方法
	 * @param inputFileFullPath 要加水印的源文件全路径
	 * @param outputFileFullPath 加水印后的输出文件全路径
	 * @param waterMarkStr 水印字符串
	 * @param opacity 模糊度越小越透明：建议0.2f左右
	 * @throws IOException 
	 * @throws DocumentException 
	 * 
	 ***/
	public static void waterMark(String inputFileFullPath,String outputFileFullPath, String waterMarkStr,float opacity) throws DocumentException, IOException {
		PdfReader reader = new PdfReader(inputFileFullPath);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFileFullPath));
		stamper.setViewerPreferences(PdfWriter.HideToolbar|PdfWriter.HideMenubar);
		BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.EMBEDDED);

        Rectangle pageRect = null;
        PdfGState gs = new PdfGState();
        gs.setFillOpacity(opacity);
        gs.setStrokeOpacity(0.4f);
        int total = reader.getNumberOfPages() + 1;

        JLabel label = new JLabel();
        FontMetrics metrics;
        int textH = 0;
        int textW = 0;
        label.setText(waterMarkStr);
        metrics = label.getFontMetrics(label.getFont());
        textH = metrics.getHeight();//字符串的高,   只和字体有关
        textW = metrics.stringWidth(label.getText());//字符串的宽

        PdfContentByte under;
    	int interval = -5;
        for (int i = 1; i < total; i++) {
            pageRect = reader.getPageSizeWithRotation(i);
            // 计算水印X,Y坐标
            under = stamper.getOverContent(i);
            under.saveState();
            under.setGState(gs);
            under.beginText();
            under.setFontAndSize(base, 15);
            // 水印文字成45度角倾斜
            for (int height = interval + textH; height < pageRect.getHeight();
                 height = height + textH*8) {
                for (int width = interval + textW; width < pageRect.getWidth() + textW;
                     width = width + textW) {
                    under.showTextAligned(Element.ALIGN_LEFT
                            , waterMarkStr, width - textW,
                            height - textH, 45);
                }
            }
            // 添加水印文字
            under.endText();
        }
        stamper.close();
	}
	
	/****
	 * 把多个路径下的pdf文件组合成一个pdf文件
	 * @param inputFileFullPaths 要组合的pdf文件
	 * @param outputFileFullPath 组合后的输出文件
	 * @param createPage 是否创建页码
	 * @throws IOException 
	 * @throws DocumentException 
	 ***/
	public static void concatPdfs(java.util.List<String> inputFileFullPaths,String outputFileFullPath,boolean createPage) throws IOException, DocumentException {
		int totalPages = 0;
		Document document = new Document();
		java.util.List<PdfReader> readers = new ArrayList<>();
       
		java.util.List<InputStream> pdfs=new ArrayList<InputStream>();
		for(String inputPdfFilePath:inputFileFullPaths) {
			pdfs.add(new FileInputStream(inputPdfFilePath));
		}
		
        Iterator<InputStream> iteratorPDFs = pdfs.iterator();
        // Create Readers for the pdfs.
        while (iteratorPDFs.hasNext()) {
            InputStream pdf = iteratorPDFs.next();
            PdfReader pdfReader = new PdfReader(pdf);
            readers.add(pdfReader);
            totalPages += pdfReader.getNumberOfPages();
            pdf.close();
        }
        // Create a writer for the outputstream
    	OutputStream outputStream=new FileOutputStream(new File(outputFileFullPath));
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        PdfContentByte cb = writer.getDirectContent(); // Holds the PDF
        // data
        PdfImportedPage page;
        int currentPageNumber = 0;
        int pageOfCurrentReaderPDF = 0;
        Iterator<PdfReader> iteratorPDFReader = readers.iterator();
        // Loop through the PDF files and add to the output.
        while (iteratorPDFReader.hasNext()) {
            PdfReader pdfReader = iteratorPDFReader.next();
            // Create a new page in the target for each source page.
            while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                document.newPage();
                pageOfCurrentReaderPDF++;
                currentPageNumber++;
                page = writer.getImportedPage(pdfReader,
                        pageOfCurrentReaderPDF);
                cb.addTemplate(page, 0, 0);
                // Code for pagination.
                if (createPage) {
                    cb.beginText();
                    cb.setFontAndSize(bf, 9);
                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, currentPageNumber+"/" + totalPages, 520,5, 0);
                    cb.endText();
                }
            }
            pageOfCurrentReaderPDF = 0;
        }
        outputStream.flush();
        document.close();
        outputStream.close();
	}
	
	/***
	 * 测试主方法
	 **/
    public static void main(String[] args) throws Exception {
    	//waterMark("D://testaerfa//test33.pdf", "D://testaerfa//test44.pdf", "222222", 0.2f);
    	String outPut="D://testaerfa//test55.pdf";
    	String input1="D://testaerfa//test33.pdf";
    	String input2="D://testaerfa//test44.pdf";
    	java.util.List<String> listInput=new ArrayList<String>();
    	listInput.add(input1);listInput.add(input2);
    	concatPdfs(listInput,outPut,true);
    }
}
