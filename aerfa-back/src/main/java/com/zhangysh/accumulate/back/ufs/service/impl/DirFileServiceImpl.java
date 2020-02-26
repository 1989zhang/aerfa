package com.zhangysh.accumulate.back.ufs.service.impl;
import java.io.IOException;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zhangysh.accumulate.back.ufs.config.UfsConfig;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileService;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileToDbService;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.FileUtil;
import com.zhangysh.accumulate.common.util.UuidUtil;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;
/**
 * 磁盘方式保存文件的具体实现
 * @author 创建者：zhangysh
 * @date 2018年9月10日
 */
@Service(value = "DirFile")
public class DirFileServiceImpl implements IUploadFileService{

	@Resource
	private IUploadFileToDbService uploadFileToDbService;
	
	@Override
    public AefufsUploadFile saveFile(AefufsUploadFileDto uploadFileDto,UfsConfig ufsConfig) throws IOException{
    	//以日期作为路径拼装
		String behandPath= DateOperate.getDateYmdFileHolder();
		String fileDir = ufsConfig.getUfsDirBasedir()+behandPath;

		String fileName=uploadFileDto.getFileName();
		String createBy=uploadFileDto.getCreateBy();
		
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
		String uuid =UuidUtil.getUMID();
		String saveName=uuid+"."+suffix;
		//保存到磁盘
		FileUtil.saveFiletToDisk(uploadFileDto.getFileBase64Data(),fileDir,saveName);
		//保存数据库对象
		AefufsUploadFile uploadFile=new AefufsUploadFile();
		uploadFile.setFileName(fileName);
		uploadFile.setSaveName(saveName);
		uploadFile.setFileType(suffix);
		uploadFile.setFileLink(behandPath+saveName);
		uploadFile.setImplName(ufsConfig.getUfsImpl());
		uploadFile.setCreateBy(createBy);
		uploadFile.setCreateTime(new Date());
		return uploadFileToDbService.insertUploadFileToDb(uploadFile);
    }
}
