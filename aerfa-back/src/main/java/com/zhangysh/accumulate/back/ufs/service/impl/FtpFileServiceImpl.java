package com.zhangysh.accumulate.back.ufs.service.impl;

import java.io.IOException;
import java.util.Date;
import javax.annotation.Resource;

import com.zhangysh.accumulate.back.sys.service.IConfigDataService;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsOutFileDto;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhangysh.accumulate.back.ufs.config.UfsConfig;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileService;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileToDbService;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.common.util.UuidUtil;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;

/*****
 * ftp方式保存文件的具体实现
 * @author zhangysh
 * @date 2019年5月14日
 *****/
@Service(value = "FtpFile")
public class FtpFileServiceImpl implements IUploadFileService{
	
	private static final Logger logger=LoggerFactory.getLogger(FtpFileServiceImpl.class);

	@Resource
	private IUploadFileToDbService uploadFileToDbService;
	@Autowired
	private IConfigDataService configDataService;

	@Override
    public AefufsUploadFile saveFile(AefufsUploadFileDto uploadFileDto,UfsConfig ufsConfig) throws IOException{
    	//以日期作为路径拼装
		String behandPath= DateOperate.getDateYmdFileHolder();
		String fileDir = ufsConfig.getUfsFtpBasedir()+behandPath;

		String fileName=uploadFileDto.getFileName();
		String createBy=uploadFileDto.getCreateBy();
		
		String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
		String uuid =UuidUtil.getUMID();
		String saveName=uuid+"."+suffix;
		
		//保存到Ftp
		saveFiletToFtp(uploadFileDto.getFileBase64Data(),fileDir,saveName,
				ufsConfig.getUfsFtpIp(),ufsConfig.getUfsFtpPort(),ufsConfig.getUfsFtpUser(),ufsConfig.getUfsFtpPassword());
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
	
	/***
	 *因为事务会占用表资源，所以把存文件和存数据库的两个方法分开,Transactional加到保存对象saveUploadFileToDb上面
	 *保存文件到磁盘
	 *@param fileBase64Str 文件内容64编码
	 *@param fileDir 保存目录
	 *@param filename 保存的文件名
	 ***/
	private boolean saveFiletToFtp(String fileBase64Str,String fileDir,String filename,
								   String ftpIp,Integer ftpPort,String ftpUer,String ftpPassword){
		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			ftp.connect(ftpIp, ftpPort);
			// 登录ftp
			logger.info("开始登陆ftp服务器账号:{}密码:{}",ftpUer,ftpPassword);
			ftp.login(ftpUer,ftpPassword);
			//打开被动模式，ftp服务器开端口，接收机器端口固定？要验证
			ftp.enterLocalPassiveMode();
			//打开本地的主动模式，ftp服务器端口固定，接收机器随机开放端口？要验证
			//ftp.enterLocalActiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.setControlEncoding("UTF-8");
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			logger.info("reply code:{}",reply);
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.info("登录失败关闭连接。");
				ftp.disconnect();
				return success;
			}
			// 转到指定上传目录
			logger.info("开始创建目标目录");
			String paths = fileDir;
			String[] split = paths.split("/");
			for (int i = 0; i < split.length; i++) {
				if(!"".equals(split[i])){
					ftp.makeDirectory(split[i]);
					//转到指定目录（下一级）
					ftp.changeWorkingDirectory(split[i]);
				}
			}
			// 将上传文件存储到指定目录
			logger.info("开始上传文件{},开始时间{}",filename,new Date());
			success = ftp.storeFile(filename,InputStreamUtil.Base64ToInputStream(fileBase64Str));
			logger.info("文件上传结束是否成功{},结束时间{}",success,new Date());
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("上传图片到ftp服务器异常{}",e1.getMessage());
		} finally {
			try {
				ftp.logout();//退出ftp
			} catch (Exception e2) {
				e2.printStackTrace();
				logger.error("退出ftp服务器异常{}",e2.getMessage());
			}
			if (ftp.isConnected()) {	
				try {//关闭ftp连接
					ftp.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					logger.error("关闭ftp连接异常{}",ioe.getMessage());
				}
			}
		}
		return success;
	}

	@Override
	public AefufsOutFileDto downloadFile(Long id, UfsConfig ufsConfig) throws IOException{
		AefufsUploadFile ufsUploadFile=uploadFileToDbService.getUploadFileById(id);
		AefufsOutFileDto ufsOutFileDto=new AefufsOutFileDto();
		BeanUtils.copyProperties(ufsUploadFile,ufsOutFileDto);
		AefsysConfigData picIpAddressConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_SYS_PIC_IP_ADDRESS);
		ufsOutFileDto.setNginxPrefixPath(picIpAddressConfigData.getDataValue());
		ufsOutFileDto.setFileFullLink(picIpAddressConfigData.getDataValue()+ufsOutFileDto.getFileLink());
		return ufsOutFileDto;
	}
}
