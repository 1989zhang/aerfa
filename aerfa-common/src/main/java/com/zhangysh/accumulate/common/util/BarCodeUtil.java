package com.zhangysh.accumulate.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zhangysh.accumulate.common.constant.UtilConstant;

/**
 *生成条形方法:用com.google.zxing条形码生成
 */
public class BarCodeUtil {

	/**
	 * 创建默认的条形码：长度200,高度50,纠错等级M
	 * @param content 二维码内容如果是http则扫描会跳转
	 ***/
	public static byte[] createDefaultBarCode(String content){
		return createBarCodeByParam(content,200,50,"M");
	}
	
	/***
	 *根据参数生成条形码 
	 * @param content 条形码内容
	 * @param width 条形码宽度
	 * @param height 条形码高度
	 * @param level 纠错等级:H L M Q,一般用M
	 *****/
	public static byte[] createBarCodeByParam(String content,int width,int height,String level) {
		//定义条形码的参数
		byte[] bts=null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try{
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");//字符集
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);//纠错等级默认用M
			if("H".equals(level)) {
				hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);//纠错等级  H
			}else if("L".equals(level)) {
				hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//纠错等级  L
			}else if("Q".equals(level)) {
				hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);//纠错等级  Q
			}
			BitMatrix bitMatrix = new Code128Writer().encode(content, BarcodeFormat.CODE_128, width, height, hints);
			MatrixToImageWriter.writeToStream(bitMatrix,UtilConstant.FILE_PIC_TYPE_PNG, bos);// 输出图像
		    bts = bos.toByteArray();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bos != null){
	            try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
		return bts;
	}
}
