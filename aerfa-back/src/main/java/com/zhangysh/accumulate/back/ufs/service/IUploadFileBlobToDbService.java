package com.zhangysh.accumulate.back.ufs.service;

import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFileBlob;

/*****
 * 保存文件aefufs_upload_file_blob对象的方法 ，并返回带id的对象，
 * 此方法和不带DB是两回事，二者无父子关系，DbFileServiceImpl调用此方法
 * @author zhangysh
 * @date 2019年5月14日
 *****/
public interface IUploadFileBlobToDbService {

	/**
	 * 根据ID查询单个二进制内容文件上传信息
	 *
	 * @param id 二进制内容文件上传主键ID
	 * @return 二进制内容文件上传信息
	 */
	AefufsUploadFileBlob getUploadFileBlobById(Long id);

	/**
	 * 保存二进制内容对象AefufsUploadFileBlob到数据库，并返回带id的对象 
	 * @param uploadFileBlob 保存对象
	 * @return 返回带id的对象
	 *****/
	AefufsUploadFileBlob insertUploadFileBlobToDb(AefufsUploadFileBlob uploadFileBlob);
}
