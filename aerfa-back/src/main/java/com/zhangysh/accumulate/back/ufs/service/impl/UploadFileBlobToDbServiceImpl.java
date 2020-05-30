package com.zhangysh.accumulate.back.ufs.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhangysh.accumulate.back.ufs.dao.UploadFileBlobDao;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileBlobToDbService;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFileBlob;

/*****
 * 保存文件aefufs_upload_file对象的方法
 * @author zhangysh
 * @date 2019年5月14日
 *****/
@Service
public class UploadFileBlobToDbServiceImpl implements IUploadFileBlobToDbService{

	@Resource
	private UploadFileBlobDao uploadFileBlobDao;

	@Override
	public AefufsUploadFileBlob getUploadFileBlobById(Long id){
		return uploadFileBlobDao.getUploadFileBlobById(id);
	}

	@Override
	@Transactional
	public AefufsUploadFileBlob insertUploadFileBlobToDb(AefufsUploadFileBlob uploadFileBlob) {
		uploadFileBlobDao.insertUploadFileBlob(uploadFileBlob);
		return uploadFileBlob;
	}
}
