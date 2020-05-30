package com.zhangysh.accumulate.back.ufs.service;

import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
/*****
 * 保存文件aefufs_upload_file对象的方法 ，并返回带id的对象
 * 此方法和带ID的是两回事，二者无父子关系，FtpFileServiceImpl和DirFileServiceImpl调用此方法
 * @author zhangysh
 * @date 2019年5月14日
 *****/
public interface IUploadFileToDbService {

	/**
	 * 根据ID查询单个文件上传信息
	 *
	 * @param id 主键ID
	 * @return 文件上传信息
	 */
	AefufsUploadFile getUploadFileById(Long id);

	/**
	 * 保存AefufsUploadFile对象到数据库，并返回带id的对象 
	 * @param uploadFile 保存对象 
	 * @return 带id的对象
	 *****/
	 AefufsUploadFile insertUploadFileToDb(AefufsUploadFile uploadFile);

}
