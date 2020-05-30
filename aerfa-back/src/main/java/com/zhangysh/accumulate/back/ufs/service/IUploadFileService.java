package com.zhangysh.accumulate.back.ufs.service;
import java.io.IOException;

import com.zhangysh.accumulate.back.ufs.config.UfsConfig;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsOutFileDto;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;

/**
 *保存文件的方法接口
 *@author 创建者：zhangysh
 *@date 2018年9月10日
 */
public interface IUploadFileService {

	/**
	 * 保存文件返回存储相关对象
	 * @param uploadFileDto 保存的对象
	 * @return 文件存储对象
	 */
	AefufsUploadFile saveFile(AefufsUploadFileDto uploadFileDto,UfsConfig ufsConfig) throws IOException;

	/***
	 * 下载保存的文件对象，ftp就不下载base64了，直接返回nginx路径
	 * @param id 文件id
	 * @param ufsConfig 配置文件
	 */
	AefufsOutFileDto downloadFile(Long id, UfsConfig ufsConfig) throws IOException;


}
