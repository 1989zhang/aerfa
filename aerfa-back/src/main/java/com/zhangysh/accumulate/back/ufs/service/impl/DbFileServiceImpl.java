package com.zhangysh.accumulate.back.ufs.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsOutFileDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.back.ufs.config.UfsConfig;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileBlobToDbService;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileService;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFileBlob;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;

/*****
 * 数据库存储方式保存文件的具体实现，针对比较重要的文件要数据库保存。
 * 如果要实现需建立另一个用户，授权过来保存对象
 * @author zhangysh
 * @date 2019年5月14日
 *****/
@Service(value = "DbFile")
public class DbFileServiceImpl implements IUploadFileService{
	
	@Resource
	private IUploadFileBlobToDbService uploadFileBlobToDbService;
	
	@Override
	public AefufsUploadFile saveFile(AefufsUploadFileDto uploadFileDto,UfsConfig ufsConfig) throws IOException {
		String fileName=uploadFileDto.getFileName();
		String createBy=uploadFileDto.getCreateBy();
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
		
		AefufsUploadFileBlob uploadFileBlob=new AefufsUploadFileBlob();
		uploadFileBlob.setFileName(fileName);
		uploadFileBlob.setFileType(suffix);
		uploadFileBlob.setFileBlob(InputStreamUtil.Base64ToByte(uploadFileDto.getFileBase64Data()));
		uploadFileBlob.setImplName(ufsConfig.getUfsImpl());
		uploadFileBlob.setCreateBy(createBy);
		uploadFileBlob.setCreateTime(new Date());
		uploadFileBlobToDbService.insertUploadFileBlobToDb(uploadFileBlob);
		
		//为了保持一致新建的AefufsUploadFile即可
		AefufsUploadFile retUploadFile=new AefufsUploadFile();
		retUploadFile.setId(uploadFileBlob.getId());
		retUploadFile.setFileName(fileName);
		retUploadFile.setFileType(suffix);
		retUploadFile.setCreateBy(createBy);
		return retUploadFile;
	}

	@Override
	public AefufsOutFileDto downloadFile(Long id, UfsConfig ufsConfig){
		AefufsUploadFileBlob ufsUploadFileBlob = uploadFileBlobToDbService.getUploadFileBlobById(id);
		AefufsOutFileDto ufsOutFileDto=new AefufsOutFileDto();
		BeanUtils.copyProperties(ufsUploadFileBlob,ufsOutFileDto);
		ufsOutFileDto.setFileBase64Data(InputStreamUtil.ByteToBase64(ufsUploadFileBlob.getFileBlob()));
		return ufsOutFileDto;
	}
}
