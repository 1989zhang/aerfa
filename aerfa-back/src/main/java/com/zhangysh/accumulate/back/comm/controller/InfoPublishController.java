package com.zhangysh.accumulate.back.comm.controller;

import com.zhangysh.accumulate.back.support.service.IGenerateCodeService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.DataPermission;
import com.zhangysh.accumulate.back.ufs.controller.UploadFileController;
import com.zhangysh.accumulate.common.util.FileUtil;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.pojo.comm.viewobj.AefcommInfoPublishVo;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.back.sys.base.aspect.annotation.Log;
import com.zhangysh.accumulate.back.sys.base.BaseController;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.HttpStorageUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.comm.transobj.AefcommInfoPublishDto;
import com.zhangysh.accumulate.back.comm.service.IInfoPublishService;

import java.io.IOException;

/**
 * 发布调用相关方法
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
@Controller
@RequestMapping("/comm/info_publish")
public class InfoPublishController extends BaseController{

    private static final Logger logger=LoggerFactory.getLogger(InfoPublishController.class);

	@Autowired
	private IInfoPublishService infoPublishService;
	@Autowired
    private IRedisRelatedService redisRelatedService;
	@Autowired
	private IGenerateCodeService generateCodeService;
	@Autowired
	private UploadFileController uploadFileController;
	/****
	 * 获取展示发布信息列表
	 * @param request 请求对象
	 * @param infoPublishDto 分页和查询对象
	 * @return 获取到的发布对象集合JSON
	 ****/
	@DataPermission(tableNameIdentify="aefcomm_info_publish")
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public String getList(HttpServletRequest request,@RequestBody AefcommInfoPublishDto infoPublishDto) {
		logger.info("getList分页信息:当前{}页，每页{}条",infoPublishDto.getPageInfo().getPageNum(),infoPublishDto.getPageInfo().getPageSize());
		BsTableDataInfo tableInfo=infoPublishService.listPageInfoPublish(infoPublishDto.getPageInfo(),infoPublishDto.getInfoPublish());
		return JSON.toJSONStringWithDateFormat(tableInfo,UtilConstant.MOST_MIDDLE_DATE);
	}
	
	/****
	 * 获取展示单个发布,以便修改
	 * @param request 请求对象
     * @param id 发布主键ID
     * @return 发布信息
	 ****/
	@RequestMapping(value="/single",method = RequestMethod.POST)
    @ResponseBody
	public String getSingle(HttpServletRequest request,@RequestBody Long id) {
		logger.info("getSingle发布主键信息:{}",id);
		return JSON.toJSONStringWithDateFormat(infoPublishService.getInfoPublishById(id),UtilConstant.MOST_MIDDLE_DATE);
	}

	/****
	 * 保存新增和修改的发布信息 
	 * @param request 请求对象
	 * @param infoPublishVo 保存的对象包含文件内容
	 ****/
	@RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
	public String saveInfoPublish(HttpServletRequest request,@RequestBody AefcommInfoPublishVo infoPublishVo) {
		String aerfatoken=HttpStorageUtil.getToken(request); 
		AefsysPerson operPerson=redisRelatedService.getRedisSessionPersonByToken(aerfatoken);
		if ( infoPublishVo.getId()!=null ) {//修改方法
			infoPublishVo.setUpdateTime(DateOperate.getCurrentUtilDate());
			infoPublishVo.setUpdateBy(operPerson.getPersonName());
			return toHandlerResultStr(infoPublishService.updateInfoPublish(infoPublishVo));
		} else {//新增方法
			infoPublishVo.setCreateTime(DateOperate.getCurrentUtilDate());
			infoPublishVo.setCreateBy(operPerson.getPersonName());
			int insertRows=infoPublishService.insertInfoPublish(infoPublishVo);
			//生成html文件
			try {
				byte[] retByte=generateCodeService.generatorInfoPublishHtml(infoPublishVo);
				FileUtil.byteToFile(retByte,"D://testaerfa//","11.html");
				AefufsUploadFileDto uploadFileDto=new AefufsUploadFileDto();
				uploadFileDto.setFileBase64Data(InputStreamUtil.ByteToBase64(retByte));
				uploadFileDto.setFileName("11.html");
				uploadFileController.uploadFile(request,uploadFileDto);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return toHandlerResultStr(insertRows);
		}
	}
	
	/****
	 * 删除发布对象，可以删除多个.
	 * @param request 请求对象
	 * @param ids 要删除的发布ids,中间英文,隔开
	 ***/
	@Log(system="后台管理系统",module="系统工具",menu="信息发布",button="删除",saveParam=true)
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public String deleteInfoPublishByIds(HttpServletRequest request,@RequestBody String ids) {
		return toHandlerResultStr(infoPublishService.deleteInfoPublishByIds(ids));
	}
}
