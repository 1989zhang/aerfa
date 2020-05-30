package com.zhangysh.accumulate.pojo.ufs.transobj;

import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;

/**
 * 获取文件数据的传输对象
 *
 * @author zhangysh
 * @date 2020年05月30日
 */
public class AefufsOutFileDto extends AefufsUploadFile {

    /** 文件流进行base64编码的字符串 **/
    private String fileBase64Data;
    /** ftp实现时nginx前面路径和文件存储一起拼装的文件访问路径**/
    private String fileFullLink;
    /**ftp上的nginx代理前面路径**/
    private String nginxPrefixPath;

    public String getFileBase64Data() {
        return fileBase64Data;
    }

    public void setFileBase64Data(String fileBase64Data) {
        this.fileBase64Data = fileBase64Data;
    }

    public String getFileFullLink() {
        return fileFullLink;
    }

    public void setFileFullLink(String fileFullLink) {
        this.fileFullLink = fileFullLink;
    }

    public String getNginxPrefixPath() {
        return nginxPrefixPath;
    }

    public void setNginxPrefixPath(String nginxPrefixPath) {
        this.nginxPrefixPath = nginxPrefixPath;
    }
}
