package com.zhangysh.accumulate.ui.ufs;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	public String uploadFile(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken, @RequestBody AefufsUploadFileDto uploadFileDto);

	/****
	 *获取保存的文件对象，后台自己判定返回方式
	 *@param id 文件的id
	 ***/
	@RequestMapping(value = "/ufs/download",method = RequestMethod.POST)
	public String downloadFile(@RequestHeader(CacheConstant.COOKIE_NAME_AERFATOKEN) String aerfatoken,@RequestBody Long id);

}
