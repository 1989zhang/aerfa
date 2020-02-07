package com.zhangysh.accumulate.back.ufs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhangysh.accumulate.back.ufs.dao.UploadFileDao;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileToDbService;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;

/*****
 * 保存文件aefufs_upload_file对象的方法
 * @author zhangysh
 * @date 2019年5月14日
 *****/
@Service
public class UploadFileToDbServiceImpl implements IUploadFileToDbService{

	@Resource
	private UploadFileDao uploadFileDao;
	
	@Override
	@Transactional
	public AefufsUploadFile insertUploadFileToDb(AefufsUploadFile uploadFile) {
		uploadFileDao.insertUploadFile(uploadFile);
		return uploadFile;
	}
}
