package com.zhangysh.accumulate.common.util;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import java.util.Base64;
import com.zhangysh.accumulate.common.constant.UtilConstant;
/**
 * 图片相关工具类
 * @author 创建者：zhangysh
 * @date 2019年6月27日
 */
public class ImageUtil {

	/**
	 * 根据图片路径获取图片内容
     * @param imagePath 图片文件路径
     * @return 读取到的BufferedImage图像
     * @throws IOException 路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String imagePath) throws IOException {
        File f = new File(imagePath);
        return ImageIO.read(f);
    }
    
    /**
     * @param sourceImg 待保存的图像
     * @param saveDir 保存的目录
     * @param fileName 保存的文件名，必须带后缀，比如 "head.jpg"
     * @param format 文件格式：jpg、png或者bmp
     * @return 是否保存正确
     * @throws IOException 
     */
    public static boolean saveBufferedImage(BufferedImage sourceImg, String saveDir, String fileName,String format) throws IOException {
    	// 先检查文件后缀和保存的格式是否一致
    	String postfix = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!postfix.equalsIgnoreCase(format)) {
            System.out.println("待保存文件后缀和保存的格式不一致!");
            return false;
        }
    	String fileUrl = saveDir + fileName;
        File fileOut = new File(fileUrl);
        boolean flag = ImageIO.write(sourceImg, format, fileOut);
        return flag;
    }
    
    /***
     * 合并图片
     * @param isHorizontal true是否水平合并，否为纵向合并
     * @param imgs 要合并的图像集合
     ***/
    public static BufferedImage mergeImage(boolean isHorizontal,List<BufferedImage> imgs) {
    	// 生成新图片
        BufferedImage retImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        for (BufferedImage img : imgs) {
            allw += img.getWidth();
            allh += img.getHeight();
            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }
        // 创建新图片
        if (isHorizontal) {
            retImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
        	retImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }
        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (BufferedImage img : imgs) {
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
            if (isHorizontal) { // 水平方向合并
            	retImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            } else { // 垂直方向合并
            	retImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            }
            wx += w1;
            wy += h1;
        }
        return retImage;
    }
    
    /***
     * 把无背景透明的png图片转化为白底的jpg图片 
     * @param sourceImg png转来的资源文件
     * @throws IOException 
     ***/
    public static byte[] changePng2WhiteJpg(BufferedImage sourceImg) throws IOException {
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	BufferedImage result =new BufferedImage(sourceImg.getWidth(),sourceImg.getHeight(),BufferedImage.TYPE_INT_RGB);  
		result.createGraphics().drawImage(sourceImg,0,0,Color.WHITE, null);  
		ImageIO.write(result, UtilConstant.FILE_PIC_TYPE_JPG, outputStream);
		return outputStream.toByteArray();
    }
    
	/***
     * 把无背景透明的png图片转化为白底的jpg图片 
     * @param base64Code 源图片64文件
     * @return 转换后的base64文件
     ***/
    public static String changePng2WhiteJpg(String base64Code) throws IOException {
    	byte[] byteImg = Base64.getDecoder().decode(base64Code);
    	ByteArrayInputStream in = new ByteArrayInputStream(byteImg);//将b作为输入流；
    	BufferedImage sourceImg = ImageIO.read(in);//将in作为输入流，读取图片存入image中;
		return Base64.getEncoder().encodeToString(changePng2WhiteJpg(sourceImg));
    }
    
    /***
     * 把jpg图片按照参数长宽比例压缩重新生成，为保持原图 尽量比例一致
     * @param sourceImg 资源图片
     * @param width 宽度像素
     * @param height 高度像素
     * @return 压缩后的jpg图片byte
     * @throws IOException 
     ***/
    public static byte[] zoomImage(BufferedImage sourceImg,int width, int height) throws IOException {  
		ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
		// 新生成结果图片
		BufferedImage result =  new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
		result.getGraphics().drawImage(
				sourceImg.getScaledInstance(width, height,java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
		ImageIO.write(result, UtilConstant.FILE_PIC_TYPE_JPG, outputStream);
		return outputStream.toByteArray();
	} 
    
    /***
     * 把图片按照参数旋转
     * @param sourceImg 资源图片
     * @param angel 顺时针旋转角度
     * @return 旋转后的jpg图片byte
     * @throws IOException 
     ***/
    public static byte[] rotateImage(BufferedImage sourceImg,int angel) throws IOException {
    	ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
    	int src_width = sourceImg.getWidth(null);
        int src_height = sourceImg.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = CalcRotatedSize(
        		new Rectangle(new Dimension(src_width, src_height)), angel);
        BufferedImage retImage = null;
        retImage = new BufferedImage(
        		rect_des.width, rect_des.height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = retImage.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width)/2,(rect_des.height - src_height)/2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        g2.drawImage(sourceImg, null, null);
        ImageIO.write(retImage, UtilConstant.FILE_PIC_TYPE_JPG, outputStream);
        return outputStream.toByteArray();
    }
    
    /**
     * 直接图片byte对象转为BufferedImage对象
     * @param byteImg byte资源
     * @throws IOException 
     ****/
    public static BufferedImage byte2BufferedImage(byte[] byteImg) throws IOException {
    	ByteArrayInputStream in = new ByteArrayInputStream(byteImg);//将b作为输入流；
    	BufferedImage image = ImageIO.read(in);//将in作为输入流，读取图片存入image中;
    	return image;
    }
    
    /***
     * 直接BufferedImage对象转为byte
     * @param sourceImg BufferedImage资源
     * @throws IOException 
     * **/
    public static byte[] bufferedImage2Byte(BufferedImage bufferedImg) throws IOException {
    	ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
    	ImageIO.write(bufferedImg,UtilConstant.FILE_PIC_TYPE_JPG,outputStream);
    	return outputStream.toByteArray();
    }
    
    
    /**
     * 计算旋转后的图片尺寸
     * @param src 被旋转的图片尺寸
     * @param angel 顺时针旋转角度
     * @return 旋转后的图片尺寸
     */
    private static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }
 
        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);
 
        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }
    

    /**
     * 测试主方法
     ***/
    public static void main(String[] args) {
    	try {
    		BufferedImage imagesrc=getBufferedImage("d://11.jpg");
    		byte[] byteSrc=rotateImage(imagesrc,90);
    		saveBufferedImage(byte2BufferedImage(byteSrc),"d://","22.jpg","jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
