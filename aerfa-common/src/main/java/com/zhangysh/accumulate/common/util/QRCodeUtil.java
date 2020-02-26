package com.zhangysh.accumulate.common.util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.Result;
import com.zhangysh.accumulate.common.constant.UtilConstant;
/**
 *生成二维码方法 Quick Response Code,用com.google.zxing一维生成
 */
public class QRCodeUtil {

	/**
	 * 创建默认的二维码：高度100,纠错等级M
	 * @param content 二维码内容如果是http则扫描会跳转
	 * @throws IOException 
	 * @throws WriterException 
	 ***/
	public static byte[] createDefaultQRCode(String content) throws WriterException, IOException{
		return createQRCodeByParam(content,100,100,0,"M");
	}
	
	/**
	 * 根据参数生成二维码
	 * @param content 二维码内容
	 * @param width 二维码宽度
	 * @param height 二维码高度
	 * @param margin 外边距
	 * @param level 纠错等级:H L M Q,一般用M
	 * @throws WriterException 
	 * @throws IOException  
	 ***/
	public static byte[] createQRCodeByParam(String content,int width,int height,int margin,String level) throws WriterException, IOException{
		//定义二维码的参数
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, UtilConstant.UTF_8);//字符集
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);//纠错等级默认用M
		if("H".equals(level)) {
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//纠错等级  H
		}else if("L".equals(level)) {
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//纠错等级  L
		}else if("Q".equals(level)) {
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);//纠错等级  Q
		}
		hints.put(EncodeHintType.MARGIN, margin);//边距
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
	    MatrixToImageWriter.writeToStream(bitMatrix,UtilConstant.FILE_TYPE_PIC_PNG, bos);// 输出图像
        return bos.toByteArray();	
	}
	
	/****
	 * 读取二维码文件内容
	 * @throws NotFoundException 
	 * @throws IOException 
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String ReadQrCode(BufferedImage image) throws NotFoundException,IOException {
		MultiFormatReader formatReader = new MultiFormatReader();
		BinaryBitmap binaryBitmap = new BinaryBitmap(
				new HybridBinarizer(
						new BufferedImageLuminanceSource(image))); 
		//定义二维码的参数
		HashMap hints = new HashMap();
		//字符集
		hints.put(EncodeHintType.CHARACTER_SET, UtilConstant.UTF_8);
		Result result = formatReader.decode(binaryBitmap, hints);
		return result.getText();
	}
    
	/***
	 * 生成带logo的二维码400x400图片,因为涉及提示文字，所以大小固定
	 * @param logoFile 放到中间的logo图片
	 * @param content 二维码内容
	 * @param note 图片下面的提示信息可传空
	 * @throws WriterException 
	 * @throws IOException 
	 **/ 
    public static byte[] drawLogoQRCode(BufferedImage logoFile,String content, String note) throws WriterException, IOException {
    	int QRCOLOR = 0xFF000000; // 默认是黑色
        int BGWHITE = 0xFFFFFFFF; // 背景颜色
        int WIDTH = 400; // 二维码宽
        int HEIGHT = 400; // 二维码高
        //用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, UtilConstant.UTF_8);//字符集
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.MARGIN, 0);
		 
        ByteArrayOutputStream bos= new ByteArrayOutputStream();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        BitMatrix bm = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
            }
        }
        int width = image.getWidth();
        int height = image.getHeight();
        if (StringUtil.isNotNull(logoFile)) {
            // 构建绘图对象
            Graphics2D g = image.createGraphics();
            // 读取Logo图片
            BufferedImage logo = logoFile;
            // 开始绘制logo图片
            g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
            g.dispose();
            logo.flush();
        }
        // 自定义文本描述
        if (StringUtil.isNotEmpty(note)) {
            // 新的图片，把带logo的二维码下面加上文字
            BufferedImage outImage = new BufferedImage(400, 445, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
            // 画二维码到新的面板
            outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            // 画文字到新的面板
            outg.setColor(Color.BLACK);
            outg.setFont(new Font("楷体", Font.BOLD, 30)); // 字体、字型、字号
            int strWidth = outg.getFontMetrics().stringWidth(note);
            if (strWidth > 399) {
                // //长度过长就截取前面部分
                // 长度过长就换行
                String note1 = note.substring(0, note.length() / 2);
                String note2 = note.substring(note.length() / 2, note.length());
                int strWidth1 = outg.getFontMetrics().stringWidth(note1);
                int strWidth2 = outg.getFontMetrics().stringWidth(note2);
                outg.drawString(note1, 200 - strWidth1 / 2, height + (outImage.getHeight() - height) / 2 + 12);
                BufferedImage outImage2 = new BufferedImage(400, 485, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg2 = outImage2.createGraphics();
                outg2.drawImage(outImage, 0, 0, outImage.getWidth(), outImage.getHeight(), null);
                outg2.setColor(Color.BLACK);
                outg2.setFont(new Font("宋体", Font.BOLD, 30)); // 字体、字型、字号
                outg2.drawString(note2, 200 - strWidth2 / 2,outImage.getHeight() + (outImage2.getHeight() - outImage.getHeight()) / 2 + 5);
                outg2.dispose();
                outImage2.flush();
                outImage = outImage2;
            } else {
                outg.drawString(note, 200 - strWidth / 2, height + (outImage.getHeight() - height) / 2 + 12); // 画文字
            }
            outg.dispose();
            outImage.flush();
            image = outImage;
        }

        image.flush();
        ImageIO.write(image, UtilConstant.FILE_TYPE_PIC_PNG, bos);
        return bos.toByteArray();
    }
    
	/******
	 *测试主方法 
	 ***/
	public static void main(String args[]) {
		try {
			/*String ret = ReadQrCode(ImageUtil.byte2BufferedImage(createDefaultQRCode("zhangyong张三")));
			System.out.println("二维码文本内容：" + ret);*/
			/*byte[] byteSource=createDefaultQRCode("zhangyong张三");
			BufferedImage imgSource=ImageUtil.byte2BufferedImage(byteSource);
			ImageUtil.saveBufferedImage(imgSource, "D://", "11.jpg", "jpg");*/
			/*BufferedImage logoFile=ImageUtil.getBufferedImage("D://22.jpg");
			String url = "roefooooohriiiiiiiiii";
		    String note = "这是一个很hriii房呢";
		    byte[]  byteret=drawLogoQRCode(logoFile, url, note);
		    FileUtil.byteToFile(byteret, "d://", "33.jpg");*/
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
