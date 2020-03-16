package com.zhangysh.accumulate.common.constant;

import java.nio.charset.Charset;

/*****
 * 工具类通用常量，放到一起定义方便管理和使用
 * 且方便com.zhangysh.accumulate.common.util下工具类使用
 * @author zhangysh
 * @date 2019年5月20日
 *****/
public class UtilConstant {

	/**********字符格式相关常量**********/
	/** ISO-8859-1 */
    public static final String ISO_8859_1 = "ISO-8859-1";
    /** UTF-8 */
    public static final String UTF_8 = "UTF-8";
    /** GBK */
    public static final String GBK = "GBK";
    /** ISO-8859-1 字符集*/
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
    /** UTF-8 字符集*/
    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
    /** GBK 字符集*/
    public static final Charset CHARSET_GBK = Charset.forName(GBK);
    
    /**********格式相关常量**********/
    /** 水平对齐方式:居左 **/
    public static final String HORIZONTAL_ALIGN_LEFT="left";
    /** 水平对齐方式:居中 **/
    public static final String HORIZONTAL_ALIGN_CENTER="center";
    /** 水平对齐方式:居右 **/
    public static final String HORIZONTAL_ALIGN_RIGHT="right";
    /** 数据显示类型:字符 **/
    public static final String SHOW_TYPE_STRING="string";
    /** 数据显示类型:二维码 **/
    public static final String SHOW_TYPE_RQCODE="rqcode";
    /** 数据显示类型:条形码 **/
    public static final String SHOW_TYPE_BARCODE="barcode";
    /** 数据显示类型:url地址指向图片，nginx的图片访问地址拼装 **/
    public static final String SHOW_TYPE_IMAGE_URL_FILE="imageUrlFile";
    /** 数据显示类型:ftp图片 **/
    public static final String SHOW_TYPE_IMAGE_FTP_FILE="imageFtpFile";
    /** 数据显示类型:本地文件dir图片 **/
    public static final String SHOW_TYPE_IMAGE_DIR_FILE="imageDirFile";


    
    /**********文件类型相关常量**********/
    /** excel文件类型xls常量 **/
    /*public static final String FILE_TYPE_EXCEL_XLS="xls";*/
    /** excel文件类型xlsx常量 **/
    public static final String FILE_TYPE_EXCEL_XLSX="xlsx";
    /** word文件类型doc常量 **/
    /*public static final String FILE_TYPE_WORD_DOC="doc";*/
    /** word文件类型docx常量 **/
    public static final String FILE_TYPE_WORD_DOCX="docx";
    /** pdf文件类型常量 **/
    public static final String FILE_TYPE_PDF="pdf";
	/** 图片文件类型png:png是无损压缩 **/
	public static final String FILE_TYPE_PIC_PNG="png";
	/** 图片文件类型png:通用有损非高清 **/
	public static final String FILE_TYPE_PIC_JPG="jpg";
    /** html文件类型常量 **/
    public static final String FILE_TYPE_HTML="html";
    /** html文件类型后缀常量 **/
    public static final String FILE_TYPE_HTML_SUFFIX=".html";
	
    /**********日期相关常量**********/
	/** yyyy-MM-dd 格式普通日期字符串 */
    public static final String NORMAL_MIDDLE_DATE="yyyy-MM-dd";
    /** yyyy-MM-dd HH:mm:ss 格式普通日期字符串 */
    public static final String MOST_MIDDLE_DATE="yyyy-MM-dd HH:mm:ss";
    
	/**********计算相关常量**********/
	/**定义除法默认精确10位**/
	public static final int DEF_DIV_SCALE = 10; //默认精确到10位，为了方便往后多计算几次，最后调用round完成
	
	
	/**********计量大小相关常量**********/
	/**输入输出流的一个缓冲区大小**/
	public static final int BUFFER_SIZE =8192;
}
