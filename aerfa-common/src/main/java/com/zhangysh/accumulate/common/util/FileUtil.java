package com.zhangysh.accumulate.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*****
 * 文件相关工具类
 * @author zhangysh
 * @date 2018年10月27日
 *****/
public class FileUtil {
	
	/**
	 * 根据文件路径获取文件转的byte数组 
	 * @param filePath 文件绝对路径
	 * @return 文件转的byte数组
	 * @throws IOException 
	 */
	public static byte[] fileToByte(String filePath) throws IOException{
		File file=new File(filePath);
		if(!file.exists()){
			throw new FileNotFoundException("file not exists");
		}
		ByteArrayOutputStream bos=new ByteArrayOutputStream((int)file.length());
		BufferedInputStream in=new BufferedInputStream(new FileInputStream(file));
		int buf_size=1024;
		byte[] buffer=new byte[buf_size];
		int len=0;
		while(-1 != (len=in.read(buffer,0,buf_size))){
			bos.write(buffer,0,len);
		}
		if(in!=null){in.close();}
		return bos.toByteArray();
    }
    /**
     * 根据byte数组，生成文件 
     * @param bfile 文件数组
     * @param filePath 文件存放路径
     * @param fileName 文件名称
     * @throws IOException 
     */
	public static void byteToFile(byte[] bfile,String filePath,String fileName) throws IOException{
		File dir=new File(filePath);
		if(!dir.exists()){//判断文件目录是否存在  
			dir.mkdirs();  
        }
		File file=new File(filePath+fileName);
		FileOutputStream fos=new FileOutputStream(file);
		BufferedOutputStream bos=new BufferedOutputStream(fos);
		bos.write(bfile);
		if(bos != null){bos.close(); }
		if(fos != null){fos.close();}
	}

	/**
	 * 创建文件夹
	 * @param dirPath 文件夹路径，以‘/’结尾
	 * @throws IOException
	 **/
	public static void createDirectory(String dirPath) throws IOException {
		File savePath  = new File(dirPath);
		if (!savePath.exists()) {
			if (!savePath.mkdirs()) {
				throw new IOException("创建文件夹异常:" + savePath);
			}
		}
	}

	/***
	 * 保存文件到磁盘
	 * @param fileBase64Str 文件内容64编码
	 * @param fileDir 保存目录
	 * @param saveName 保存的文件名
	 * @throws IOException 
	 ***/
	public static void saveFiletToDisk(String fileBase64Str,String fileDir,String saveName) throws IOException{
		File savePath  = new File(fileDir);
		if (!savePath.exists()) {
			if (!savePath.mkdirs()) {
				throw new IOException("创建文件夹异常:" + savePath);
			}
		}
		File saveFile = new File(fileDir+File.separator+saveName);
		FileOutputStream fos = new FileOutputStream(saveFile);
		InputStream fis=InputStreamUtil.Base64ToInputStream(fileBase64Str);
		byte b[]=new byte[1024];
		int x = fis.read(b);
		while (x != -1) {
			fos.write(b,0,x);
			x = fis.read(b);
		}
		fos.flush();
		if (fis != null) {fis.close();}
		if (fos != null) {fos.close();}
	}
	
	/** 
	 * 删除单个文件 
	 * @param filePath 被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String filePath) {  
		boolean flag = false;  
	    File file = new File(filePath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   dirPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public static boolean deleteDirectory(String dirPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!dirPath.endsWith(File.separator)) {  
	    	dirPath = dirPath + File.separator;  
	    }  
	    File dirFile = new File(dirPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}
	
	/** 
	 * 根据路径删除指定的目录或文件
	 * @param delPath  要删除的目录或文件 
	 * @return 删除成功返回 true，否则返回 false。 
	 */  
	public static boolean deleteFolderFile(String delPath) {
		boolean flag = false;  
	    File file = new File(delPath);  
	    // 判断目录或文件是否存在  
	    if (!file.exists()) {  // 不存在返回 false  
	        return flag;  
	    } else {  
	        // 判断是否为文件  
	        if (file.isFile()) {  // 为文件时调用删除文件方法  
	            return deleteFile(delPath);  
	        } else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(delPath);  
	        }  
	    }  
	}  
}
