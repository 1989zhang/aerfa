package com.zhangysh.accumulate.back.ufs.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhangysh.accumulate.common.util.StringUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.ufs.config.UfsConfig;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileService;
import com.zhangysh.accumulate.common.pojo.ResultVo;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;

/**
 *上传文件主方法
 *@author 创建者：zhangysh
 *@date 2018年9月10日
 */
@RestController
@RequestMapping("/ufs")
public class UploadFileController extends BaseController{
	
	@Resource
	private UfsConfig ufsConfig;
	
	public IUploadFileService getService(String serviceName) {
		Object obj = getBean(serviceName);
		return (IUploadFileService) obj;
	}
	
	/***
	 * 默认根据配置文件实现方式，保存文件包含的内容对象，并返回保存后数据库相关的实际对象
	 * @param  uploadFileDto 保存的对象
	 * @return 存储相关对象
	 *****/
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFile(HttpServletRequest request,@RequestBody AefufsUploadFileDto uploadFileDto) {
		try {
			IUploadFileService service =getService(ufsConfig.getUfsImpl());
			return toHandlerResultStr(true,service.saveFile(uploadFileDto,ufsConfig),null,null);
		} catch (IOException e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage()),null);
		}
	}

	/***
	 * FtpFile实现方式，保存文件包含的内容对象，并返回保存后数据库相关的实际对象
	 * @param  uploadFileDto 保存的对象
	 * @return 存储相关对象
	 *****/
	@RequestMapping(value="/upload_ftp",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFileFtp(HttpServletRequest request,@RequestBody AefufsUploadFileDto uploadFileDto) {
		try {
			IUploadFileService service =getService("FtpFile");
			return toHandlerResultStr(true,service.saveFile(uploadFileDto,ufsConfig),null,null);
		} catch (IOException e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage()),null);
		}
	}

	/***
	 * DirFile实现方式，保存文件包含的内容对象，并返回保存后数据库相关的实际对象
	 * @param  uploadFileDto 保存的对象
	 * @return 存储相关对象
	 *****/
	@RequestMapping(value="/upload_dir",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFileDir(HttpServletRequest request,@RequestBody AefufsUploadFileDto uploadFileDto) {
		try {
			IUploadFileService service =getService("DirFile");
			return toHandlerResultStr(true,service.saveFile(uploadFileDto,ufsConfig),null,null);
		} catch (IOException e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage()),null);
		}
	}

	/***
	 * DbFile实现方式，保存文件包含的内容对象，并返回保存后数据库相关的实际对象
	 * @param  uploadFileDto 保存的对象
	 * @return 存储相关对象
	 *****/
	@RequestMapping(value="/upload_db",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFileDb(HttpServletRequest request,@RequestBody AefufsUploadFileDto uploadFileDto) {
		try {
			IUploadFileService service =getService("DbFile");
			return toHandlerResultStr(true,service.saveFile(uploadFileDto,ufsConfig),null,null);
		} catch (IOException e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage()),null);
		}
	}

	/***
	 * 自定义实现方式，保存文件包含的内容对象，并返回保存后数据库相关的实际对象
	 * @param  uploadFileDto 保存的对象含自定义参数
	 * @return 存储相关对象
	 *****/
	@RequestMapping(value="/upload_custom_realize",method=RequestMethod.POST)
	@ResponseBody
	public String uploadFileCustomRealize(HttpServletRequest request,@RequestBody AefufsUploadFileDto uploadFileDto) {
		try {
			//实现自定义的文件上传
			if(StringUtil.isNotNull(uploadFileDto.getCustomRealize())&&StringUtil.isNotEmpty(uploadFileDto.getCustomRealize().getCustomServiceName())){
				IUploadFileService service=getService(uploadFileDto.getCustomRealize().getCustomServiceName());
				UfsConfig ufsCustomConfig=new UfsConfig();
				ufsCustomConfig.setUfsImpl(uploadFileDto.getCustomRealize().getCustomServiceName());
				ufsCustomConfig.setUfsFileCompress(uploadFileDto.getCustomRealize().getCustomFileCompress());
				ufsCustomConfig.setUfsDirBasedir(uploadFileDto.getCustomRealize().getCustomDirBasedir());
				ufsCustomConfig.setUfsFtpBasedir(uploadFileDto.getCustomRealize().getCustomFtpBasedir());
				ufsCustomConfig.setUfsFtpIp(uploadFileDto.getCustomRealize().getCustomFtpIp());
				ufsCustomConfig.setUfsFtpPort(uploadFileDto.getCustomRealize().getCustomFtpPort());
				ufsCustomConfig.setUfsFtpUser(uploadFileDto.getCustomRealize().getCustomFtpUser());
				ufsCustomConfig.setUfsFtpPassword(uploadFileDto.getCustomRealize().getCustomFtpPassword());
				return toHandlerResultStr(true,service.saveFile(uploadFileDto,ufsCustomConfig),null,null);
			}
			//实现默认的上传
			else{
				IUploadFileService service =getService(ufsConfig.getUfsImpl());
				return toHandlerResultStr(true,service.saveFile(uploadFileDto,ufsConfig),null,null);

			}
		} catch (IOException e) {
			e.printStackTrace();
			return toHandlerResultStr(false,null,CodeMsgConstant.UFS_UPLOAD_FILE_ERROR.fillArgs(e.getMessage()),null);
		}
	}
}
