package com.zhangysh.accumulate.back.ufs.service;

import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFileBlob;

/*****
 * 保存文件aefufs_upload_file_blob对象的方法 ，并返回带id的对象，不同表不同方法分开
 * @author zhangysh
 * @date 2019年5月14日
 *****/
public interface IUploadFileBlobToDbService {
	
	/**
	 * 保存二进制内容对象AefufsUploadFileBlob到数据库，并返回带id的对象 
	 * @param uploadFile 保存对象 
	 * @return 返回带id的对象
	 *****/
	AefufsUploadFileBlob insertUploadFileBlobToDb(AefufsUploadFileBlob uploadFileBlob);
}
