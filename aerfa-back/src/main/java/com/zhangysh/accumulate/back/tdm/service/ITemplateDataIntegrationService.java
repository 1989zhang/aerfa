package com.zhangysh.accumulate.back.tdm.service;

/*****
 * 把数据整合填充到模板方法
 * @author zhangysh
 * @date 2019年7月28日
 *****/
public interface ITemplateDataIntegrationService {

	/*****
	 * excel模板数据填充
	 * @param templateId 模板id
	 * @param requireParm 请求参数
	 * @param templateFileFullPath excel模板文件全路径
	 * @param outFileFullPath 填充完数据的excel文件路径
	 ***/
	 void excelDataIntegration(Long templateId,String requireParm,String templateFileFullPath,String outFileFullPath) throws Exception;

	/*****
	 * word模板替换数据
	 * @param templateId 模板id
	 * @param requireParm 请求参数
	 * @param templateFileFullPath word模板文件全路径
	 * @param outFileFullPath 填充完数据的word文件路径
	 ***/
	 void wordDataIntegration(Long templateId,String requireParm,String templateFileFullPath,String outFileFullPath) throws Exception;

}
