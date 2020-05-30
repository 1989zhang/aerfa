package com.zhangysh.accumulate.back.ufs.controller;

import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.back.ufs.config.UfsConfig;
import com.zhangysh.accumulate.back.ufs.service.IUploadFileService;
import com.zhangysh.accumulate.common.constant.CodeMsgConstant;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 下载文件主方法
 *
 * @author zhangysh
 * @date 2020年05月30日
 */
@RestController
@RequestMapping("/ufs")
public class DownloadFileController extends BaseController {

    @Resource
    private UfsConfig ufsConfig;

    public IUploadFileService getService(String serviceName) {
        Object obj = getBean(serviceName);
        return (IUploadFileService) obj;
    }

    /***
     * 默认根据配置文件实现方式，下载文件
     * @param  id 要下载的文件id
     * @return 存储相关对象
     *****/
    @RequestMapping(value="/download",method= RequestMethod.POST)
    @ResponseBody
    public String downloadFile(HttpServletRequest request, @RequestBody Long id) {
        try {
            IUploadFileService service =getService(ufsConfig.getUfsImpl());
            return toHandlerResultStr(true,service.downloadFile(id,ufsConfig),null,null);
        } catch (Exception e) {
            e.printStackTrace();
            return toHandlerResultStr(false,null, CodeMsgConstant.SYS_DATA_ACHIEVE_ERROR.fillArgs(e.getMessage()),null);
        }
    }
}
