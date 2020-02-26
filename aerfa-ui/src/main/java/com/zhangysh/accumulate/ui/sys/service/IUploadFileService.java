package com.zhangysh.accumulate.ui.sys.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;

/*****
 * 文件上传调用后台相关方法
 * @author zhangysh
 * @date 2019年5月13日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IUploadFileService {

	/****
	 *通过传对象的形势上传文件 
	 *@param uploadFileDto 文件相关对象
	 ***/
	@RequestMapping(value = "/ufs/upload",method = RequestMethod.POST)
	public String uploadFile(@RequestBody AefufsUploadFileDto uploadFileDto);

}
