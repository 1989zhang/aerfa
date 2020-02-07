package com.zhangysh.accumulate.back.tdm.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.pojo.adi.dataobj.AefadiDataSource;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysConfigData;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceField;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmDataSourceSql;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmFillRule;
import com.zhangysh.accumulate.pojo.tdm.dataobj.AeftdmTemplate;
import com.zhangysh.accumulate.pojo.tdm.transobj.AeftdmTemplateParmDto;
import com.zhangysh.accumulate.back.adi.dydatasource.DynamicSqlRepository;
import com.zhangysh.accumulate.back.sys.service.IConfigDataService;
import com.zhangysh.accumulate.back.tdm.dao.TemplateDao;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceFieldService;
import com.zhangysh.accumulate.back.tdm.service.IDataSourceSqlService;
import com.zhangysh.accumulate.back.tdm.service.IFillRuleService;
import com.zhangysh.accumulate.back.tdm.service.ITemplateDataIntegrationService;
import com.zhangysh.accumulate.back.tdm.service.ITemplateService;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.AsposeExcelUtil;
import com.zhangysh.accumulate.common.util.AsposeWordUtil;
import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.ExcelUtil;
import com.zhangysh.accumulate.common.util.FileUtil;
import com.zhangysh.accumulate.common.util.InputStreamUtil;
import com.zhangysh.accumulate.common.util.NativeSqlUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.common.util.UuidUtil;
import com.zhangysh.accumulate.common.util.WordUtil;
/**
 * 模板定义 服务层实现
 * 
 * @author zhangysh
 * @date 2019年06月19日
 */
@Service
public class TemplateServiceImpl implements ITemplateService {

	@Autowired
	private TemplateDao templateDao;
    @Autowired 
    private IConfigDataService configDataService;
    @Autowired 
    private ITemplateDataIntegrationService templateDataIntegrationService;
    @Autowired 
    private IDataSourceSqlService dataSourceSqlService;
    @Autowired 
    private IFillRuleService fillRuleService;
    @Autowired 
    private IDataSourceFieldService dataSourceFieldService;
    
    @Override
	public AeftdmTemplate getTemplateById(Long id){
	    return templateDao.getTemplateById(id);
	}
	
	@Override
	public BsTableDataInfo listPageTemplate(BsTablePageInfo pageInfo,AeftdmTemplate template){
	    //pagehelper方法调用
		Page<AeftdmTemplate> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		templateDao.listTemplate(template);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AeftdmTemplate> listTemplate(AeftdmTemplate template){
		return templateDao.listTemplate(template);
	}

	@Override
	public int insertTemplate(AeftdmTemplate template){
	    return templateDao.insertTemplate(template);
	}
	
	@Override
	public int updateTemplate(AeftdmTemplate template){
	    return templateDao.updateTemplate(template);
	}
	
	@Override
	public int deleteTemplateById(Long id){
		return templateDao.deleteTemplateById(id);
	}
	
	@Override
	public int deleteTemplateByIds(String ids){
		return templateDao.deleteTemplateByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	public void saveTemplateFile(String fileBase64Str,String saveName) throws IOException{
		//模板内容保存
		AefsysConfigData templateConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_TEMPLATE_FOLDER_PATH);
		String templateFolderPath=templateConfigData.getDataValue();
		if(StringUtil.isEmpty(templateFolderPath)) {//无模板存储路径配置抛异常
			throw new IOException("无模板存储路径配置");
		}
		//保存到磁盘
		FileUtil.saveFiletToDisk(fileBase64Str,templateFolderPath+SysDefineConstant.TEMPLATE_FOLDER_PATH_TEMPLATE_FILE,saveName);
	}
	
	@Override
	public Object getTemplateContent(Long id) throws Exception{
		 AeftdmTemplate template=getTemplateById(id);
		 AefsysConfigData templateConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_TEMPLATE_FOLDER_PATH);
		 String templateFolderPath=templateConfigData.getDataValue();
		 String templateFileFullPath=templateFolderPath+SysDefineConstant.TEMPLATE_FOLDER_PATH_TEMPLATE_FILE+File.separator+template.getSaveName();;
		 if(UtilConstant.FILE_TYPE_EXCEL_XLS.equals(template.getFileType())){
			 return ExcelUtil.readExcelContent(templateFileFullPath);
		 }else if(UtilConstant.FILE_TYPE_WORD_DOC.equals(template.getFileType())) {
			 return WordUtil.readWordContent(templateFileFullPath);
		 }else {
			 throw new RuntimeException("");
		 }
	}
	
	@Override
	public String getTemplateParameter(Long templateId) throws Exception{
		List<String> parameterList=new ArrayList<String>();
		AeftdmDataSourceSql dataSourceSql=new AeftdmDataSourceSql();
		dataSourceSql.setTemplateId(templateId);
		List<AeftdmDataSourceSql> dataSourceSqlList=dataSourceSqlService.listDataSourceSql(dataSourceSql);
	    //查询出所有不重复参数
		for(int i=0;i<dataSourceSqlList.size();i++) {
	    	String sqlText=dataSourceSqlList.get(i).getSqlText();
			List<String> requestStrList=NativeSqlUtil.getRequestStr(sqlText);
			for(String requestStr:requestStrList) {
				String paramName=NativeSqlUtil.getParamName(requestStr);
				if(!parameterList.contains(paramName)) {
					parameterList.add(paramName);
				}
			}
	    }
		//参数组装成字符串
		String retParamNameStr="";
		for(int i=0;i<parameterList.size();i++) {
			retParamNameStr+=parameterList.get(i)+",";
		}
		//返回字符
	    if(StringUtil.isNotEmpty(retParamNameStr)) {
	    	retParamNameStr=retParamNameStr.substring(0, retParamNameStr.length()-1);
	    }
	    return retParamNameStr;
	}
	
	@Override
	public String getViewData(AeftdmTemplateParmDto templateParmDto) throws Exception{
		AefsysConfigData templateConfigData=configDataService.getConfigDataFromRedisByCode(SysDefineConstant.CONFIG_DATA_TEMPLATE_FOLDER_PATH);
		String templateFolderPath=templateConfigData.getDataValue();
		
		AeftdmTemplate template=templateDao.getTemplateById(templateParmDto.getTemplateId());
		//模板文件
		String templateFileFullPath=templateFolderPath+SysDefineConstant.TEMPLATE_FOLDER_PATH_TEMPLATE_FILE+File.separator+template.getSaveName();
		//转换后的pdf文件
		String pdfFileFullPath=templateFolderPath+SysDefineConstant.TEMPLATE_FOLDER_PATH_EXPORT_FILE+File.separator+UuidUtil.getUMID()+"."+UtilConstant.FILE_TYPE_PDF;
		//内容替换后输出文件
		String outFileFullPath;
		if( UtilConstant.FILE_TYPE_EXCEL_XLS.equals(template.getFileType()) ) {
			outFileFullPath=templateFolderPath+SysDefineConstant.TEMPLATE_FOLDER_PATH_EXPORT_FILE+File.separator+UuidUtil.getUMID()+"."+UtilConstant.FILE_TYPE_EXCEL_XLS;
			templateDataIntegrationService.excelDataIntegration(
					templateParmDto.getTemplateId(), templateParmDto.getRequireParm(),templateFileFullPath, outFileFullPath);
		    //excel转pdf
			AsposeExcelUtil.excelToPdf(outFileFullPath, pdfFileFullPath);
			FileInputStream fileis=new FileInputStream(new File(pdfFileFullPath));
			return InputStreamUtil.InputStreamToBase64(fileis);
		}else if( UtilConstant.FILE_TYPE_WORD_DOC.equals(template.getFileType()) ) {
			outFileFullPath=templateFolderPath+SysDefineConstant.TEMPLATE_FOLDER_PATH_EXPORT_FILE+File.separator+UuidUtil.getUMID()+"."+UtilConstant.FILE_TYPE_WORD_DOC;
			templateDataIntegrationService.wordDataIntegration(
					templateParmDto.getTemplateId(), templateParmDto.getRequireParm(),templateFileFullPath, outFileFullPath);
		    //word转pdf
			AsposeWordUtil.wordToPdf(outFileFullPath, pdfFileFullPath);
			FileInputStream fileis=new FileInputStream(new File(pdfFileFullPath));
			return InputStreamUtil.InputStreamToBase64(fileis);
		}
		return "";
	}
	
	@Override
	public String getTemplateExcelFillInfo(Long templateId) throws Exception{
		String retFillInfoStr="";
		AeftdmFillRule fillRule=new AeftdmFillRule();
		fillRule.setTemplateId(templateId);
		List<AeftdmFillRule> fillRuleList=fillRuleService.listFillRule(fillRule);
		for(int i=0;i<fillRuleList.size();i++) {
			Long rowNumber=fillRuleList.get(i).getFillRowNumber();
			Long colNumber=fillRuleList.get(i).getFillColNumber();
			AeftdmDataSourceField dataSourceField=dataSourceFieldService.getDataSourceFieldById(fillRuleList.get(i).getFieldId());
			retFillInfoStr+=rowNumber+MarkConstant.MARK_SPLIT_EN_COLON+colNumber+MarkConstant.MARK_SPLIT_EN_COLON+dataSourceField.getFieldName()+MarkConstant.MARK_SPLIT_EN_COMMA;
		}
		if(StringUtil.isNotEmpty(retFillInfoStr)) {
			retFillInfoStr=retFillInfoStr.substring(0,retFillInfoStr.length()-1);
		}
		return retFillInfoStr;
	}
}
