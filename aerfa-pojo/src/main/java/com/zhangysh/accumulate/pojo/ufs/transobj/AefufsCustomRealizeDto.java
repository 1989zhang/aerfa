package com.zhangysh.accumulate.pojo.ufs.transobj;

import java.io.Serializable;

/**
 * 用户自定义用来实现文件上传的参数，如果serviceName不为空就转为自定义的配置文件
 *
 * @author zhangysh
 * @date 2020年02月26日
 */
public class AefufsCustomRealizeDto  implements Serializable{

    private static final long serialVersionUID = 1L;

    private String customServiceName;//文件上传service的名称
    private Boolean customFileCompress;//文件是否压缩保存
    private String customDirBasedir;//dir实现的根路径
    private String customFtpBasedir;//ftp实现的根路径
    private String customFtpIp;//ftp实现的IP地址
    private Integer customFtpPort;//ftp实现的端口
    private String customFtpUser;//ftp实现的账号
    private String customFtpPassword;//ftp实现的密码

    public String getCustomServiceName() {
        return customServiceName;
    }

    public void setCustomServiceName(String customServiceName) {
        this.customServiceName = customServiceName;
    }

    public Boolean getCustomFileCompress() {
        return customFileCompress;
    }

    public void setCustomFileCompress(Boolean customFileCompress) {
        this.customFileCompress = customFileCompress;
    }

    public String getCustomDirBasedir() {
        return customDirBasedir;
    }

    public void setCustomDirBasedir(String customDirBasedir) {
        this.customDirBasedir = customDirBasedir;
    }

    public String getCustomFtpBasedir() {
        return customFtpBasedir;
    }

    public void setCustomFtpBasedir(String customFtpBasedir) {
        this.customFtpBasedir = customFtpBasedir;
    }

    public String getCustomFtpIp() {
        return customFtpIp;
    }

    public void setCustomFtpIp(String customFtpIp) {
        this.customFtpIp = customFtpIp;
    }

    public Integer getCustomFtpPort() {
        return customFtpPort;
    }

    public void setCustomFtpPort(Integer customFtpPort) {
        this.customFtpPort = customFtpPort;
    }

    public String getCustomFtpUser() {
        return customFtpUser;
    }

    public void setCustomFtpUser(String customFtpUser) {
        this.customFtpUser = customFtpUser;
    }

    public String getCustomFtpPassword() {
        return customFtpPassword;
    }

    public void setCustomFtpPassword(String customFtpPassword) {
        this.customFtpPassword = customFtpPassword;
    }
}
