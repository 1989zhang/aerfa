package com.zhangysh.accumulate.back.ufs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *配置文件模型对象
 *@author 创建者：zhangysh
 * @date 2018年9月10日
 */
@Configuration
public class UfsConfig {

	//上传文件实现方式
	@Value("${ufs.impl}")
	private String ufsImpl;
	//图片是否压缩，默认不压缩
	@Value("${ufs.file.compress:false}")
	private Boolean ufsFileCompress;
	//本地文件上传路径
	@Value("${ufs.dir.basedir}")
	private String ufsDirBasedir;
	
	//FtpFile方式上传的相关配置
	@Value("${ufs.ftp.basedir}")
	private String ufsFtpBasedir;
	@Value("${ufs.ftp.ip}")
	private String ufsFtpIp;
	@Value("${ufs.ftp.port}")
	private String ufsFtpPort;
	@Value("${ufs.ftp.user}")
	private String ufsFtpUser;
	@Value("${ufs.ftp.password}")
	private String ufsFtpPassword;
		
	
	public String getUfsImpl() {
		return ufsImpl;
	}
	public void setUfsImpl(String ufsImpl) {
		this.ufsImpl = ufsImpl;
	}
	public Boolean getUfsFileCompress() {
		return ufsFileCompress;
	}
	public void setUfsFileCompress(Boolean ufsFileCompress) {
		this.ufsFileCompress = ufsFileCompress;
	}
	public String getUfsDirBasedir() {
		return ufsDirBasedir;
	}
	public void setUfsDirBasedir(String ufsDirBasedir) {
		this.ufsDirBasedir = ufsDirBasedir;
	}
	public String getUfsFtpBasedir() {
		return ufsFtpBasedir;
	}
	public void setUfsFtpBasedir(String ufsFtpBasedir) {
		this.ufsFtpBasedir = ufsFtpBasedir;
	}
	public String getUfsFtpIp() {
		return ufsFtpIp;
	}
	public void setUfsFtpIp(String ufsFtpIp) {
		this.ufsFtpIp = ufsFtpIp;
	}
	public String getUfsFtpPort() {
		return ufsFtpPort;
	}
	public void setUfsFtpPort(String ufsFtpPort) {
		this.ufsFtpPort = ufsFtpPort;
	}
	public String getUfsFtpUser() {
		return ufsFtpUser;
	}
	public void setUfsFtpUser(String ufsFtpUser) {
		this.ufsFtpUser = ufsFtpUser;
	}
	public String getUfsFtpPassword() {
		return ufsFtpPassword;
	}
	public void setUfsFtpPassword(String ufsFtpPassword) {
		this.ufsFtpPassword = ufsFtpPassword;
	}
	
}
