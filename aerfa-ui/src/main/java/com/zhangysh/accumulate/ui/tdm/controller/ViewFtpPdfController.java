package com.zhangysh.accumulate.ui.tdm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.util.HttpClientUtil;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsOutFileDto;
import com.zhangysh.accumulate.ui.sys.service.IConfigDataService;
import com.zhangysh.accumulate.ui.ufs.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 直接展示ftp上的pdf文件
 *
 * @author zhangysh
 * @date 2020年05月30日
 */
@Controller
@RequestMapping("/tdm/view_ftp_pdf")
public class ViewFtpPdfController {

    private String templatePrefix="/tdm/template";//返回界面路径即前缀
    private String prefix="/tdm/view_ftp_pdf";

    @Resource
    private IUploadFileService uploadFileService;

    /**
     * 展示ftp上的pdf文件：
     * 可根据aefufs_upload_file的id方式获取文件内容
     *****/
    @RequestMapping(value={"/to_view_ftp_pdf/by_id/{fileId}"})
    public String toViewFtpDataById(HttpServletRequest request, ModelMap modelMap, @PathVariable("fileId") Long fileId) {
        modelMap.addAttribute("prefix",prefix);
        modelMap.addAttribute("tdmPdfDataUrl",prefix+"/data_ftp_pdf_by_id/"+fileId);
        return templatePrefix + "/viewer_data";
    }

    /**
     *根据aefufs_upload_file的id获取pdf展示文件内容
     ***/
    @RequestMapping(value = "/data_ftp_pdf_by_id/{fileId}")
    @ResponseBody
    public void getDataFtpPdfById(HttpServletResponse response, HttpServletRequest request, @PathVariable("fileId") Long fileId) {
        ServletOutputStream out = null;
        try {
            String aerfatoken= HttpStorageUtil.getToken(request);
            String ftpInfoJson=uploadFileService.downloadFile(aerfatoken,fileId);
            JSONObject ftpJson=JSON.parseObject(ftpInfoJson);
            Integer code=ftpJson.getInteger(MarkConstant.MARK_RESULT_VO_CODE);
            if( MarkConstant.MARK_RESULT_VO_SUCESS.equals(code) ) {
                AefufsOutFileDto outFileDto= JSON.parseObject(ftpJson.getString(MarkConstant.MARK_RESULT_VO_DATA), AefufsOutFileDto.class);
                String nginxFilePath=outFileDto.getFileFullLink();
                String fileBase64=HttpClientUtil.getBase64FileByHttpUrl(nginxFilePath,true);
                byte[] bytes = InputStreamUtil.Base64ToByte(fileBase64);
                for (int i = 0; i < bytes.length; ++i) {
                    if (bytes[i] < 0) {// 调整异常数据
                        bytes[i] += 256;
                    }
                }
                response.setContentType("application/pdf");
                out = response.getOutputStream();
                byte[] buffer = bytes;
                out.write(buffer, 0, buffer.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
