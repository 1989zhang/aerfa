package com.zhangysh.accumulate.back.ufs.service;
import java.io.IOException;

import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
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
	AefufsUploadFile saveFile(AefufsUploadFileDto uploadFileDto) throws IOException;
}
