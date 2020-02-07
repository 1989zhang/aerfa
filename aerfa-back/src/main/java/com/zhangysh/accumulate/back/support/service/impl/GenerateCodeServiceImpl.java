package com.zhangysh.accumulate.back.support.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.back.support.dao.GenerateCodeDao;
import com.zhangysh.accumulate.back.support.service.IGenerateCodeService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.CalculateUtil;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.support.dataobj.ColumnInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.TableInfo;
import com.zhangysh.accumulate.pojo.support.transobj.GenerateCodeParamDto;

/**
 *@author 创建者：zhangysh
 */
@Service
public class GenerateCodeServiceImpl implements IGenerateCodeService{

	private static Logger logger = LoggerFactory.getLogger(GenerateCodeServiceImpl.class);

	@Autowired
	private GenerateCodeDao generateCodeDao;
	
	@Override
	public BsTableDataInfo listPageTables(BsTablePageInfo pageInfo,TableInfo tableInfo) {
		Page<TableInfo> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		generateCodeDao.listTables(tableInfo);
		BsTableDataInfo bsTableInfo=new BsTableDataInfo();
		bsTableInfo.setTotal(page.getTotal());
		bsTableInfo.setRows(page.getResult());
		return bsTableInfo;
	}

	@Override
	public TableInfo getTableByName(String tableName) {
		return generateCodeDao.getTableByName(tableName);
	}

	@Override
	public List<ColumnInfo> getTableColumnsByName(String tableName) {
		return generateCodeDao.getTableColumnsByTableName(tableName);
	}
	
	@Override
	public byte[] generatorCode(GenerateCodeParamDto codeParamDto) {
		//表名基本信息
		String tableName=codeParamDto.getTableName();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 查询表信息
        TableInfo table =getTableByName(tableName);
        // 查询列信息
        List<ColumnInfo> columns=getTableColumnsByName(tableName);
        // 生成代码
        produceCode(table, columns, zip, codeParamDto);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
	}
	
	/***
	 *开始生成代码
	 *@param table 表信息对象
	 *@param columns 表所属列
	 *@param zip 输出zip对象
	 ***/
	private void produceCode(TableInfo table,List<ColumnInfo> columns,ZipOutputStream zip,GenerateCodeParamDto codeParamDto){
		/*****首先设置获取需要的基本信息*****/
		// 表名转换成Java属性名
		String tableName=table.getTableName();
		String doclassName=StringUtil.convertToCamelCase(tableName);
		String doclassname=StringUtil.convertToCamelCase(doclassName);
		
		/*****表名去掉前缀*****/
		String autoReomvePre=codeParamDto.getReomveTablePrefix()==null?"":codeParamDto.getReomveTablePrefix();
        if(StringUtil.isNotEmpty(autoReomvePre)&&tableName.startsWith(autoReomvePre)) {
        	tableName = tableName.substring(autoReomvePre.length());
        }
        String className=StringUtil.convertToCamelCase(tableName);
        String classname=StringUtil.convertToFirstLowerCase(className);
	    
        /*****列转换,且把对应信息设置到列里面去******/
        List<ColumnInfo> tableColumns = transColums(columns,codeParamDto);
        
        /*****设置值到table对象里面*****/
        table.setColumns(tableColumns);// 列信息
        table.setPrimaryKey(columns.get(0));//列第一个为主键
        table.setClassName(className);
        table.setClassname(classname);
        table.setDoclassName(doclassName);
        table.setDoclassname(doclassname);
        table.setRemovePrefixTableName(tableName);
        
        initVelocity();
        //设置模板对象参数
        VelocityContext context =getVelocityContext(table,codeParamDto);
        // 获取模板列表
        List<String> templates =getTemplates();
        for (String template : templates){
        	// 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template,"UTF-8");
            tpl.merge(context, sw);
            try{
                // 添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template,table)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
                
            }catch (IOException e){
            	e.printStackTrace();
            	logger.error("渲染模板{}失败,详细信息{}",table.getTableName(),e.getMessage());
            }
        }
        
	}
    /****
     *根据模板文件创建代码文件 
     *@param template 模板文件
     *@param table 表信息
     ***/
    private String getFileName(String template,TableInfo table) {
    	
    	String removePrefixTableName = table.getRemovePrefixTableName();
        String className = table.getClassName();
        String doclassName = table.getDoclassName();
        String javaPath = "";
        if (StringUtil.isNotEmpty(className)){
            javaPath += className.toLowerCase() + "/";
        }	
        //为了区分模板名称Controller.java.vm和UiController.java.vm，所以先处理下
        template=template.substring(template.lastIndexOf("/")+1);
        if(template.equals("DataObj.java.vm")) {
        	return javaPath + "pojo" +"/dataobj" +"/" + doclassName + ".java";
        }else if(template.equals("DataTransObj.java.vm")) {
        	return javaPath + "pojo" +"/transobj" +"/" + doclassName + "Dto.java";
        }else if(template.equals("DataViewObj.java.vm")) {
        	return javaPath + "pojo" +"/viewobj" +"/" + doclassName + "Vo.java";
        }else if(template.equals("Controller.java.vm")) {
        	return javaPath + "controller" +"/" + className + "Controller.java";
        }else if(template.equals("Service.java.vm")) {
        	return javaPath + "service" + "/I" + className + "Service.java";
        }else if(template.equals("ServiceImpl.java.vm")) {
        	return javaPath + "service" + "/impl" +"/" + className + "ServiceImpl.java";
        }else if(template.equals("Dao.java.vm")) {
        	return javaPath + "dao" + "/" + className + "Dao.java";
        }else if (template.equals("Dao.xml.vm")){
        	 return javaPath + "dao" + "/" + className + "Dao.xml";
        }else if(template.equals("list.html.vm")) {
        	 return javaPath + "html" + "/" + removePrefixTableName + ".html";
        }else if(template.equals("add.html.vm")) {
       	     return javaPath + "html" + "/" + "add" + ".html";
        }else if(template.equals("edit.html.vm")) {
      	     return javaPath + "html" + "/" + "edit" + ".html";
        }else if(template.equals("UiController.java.vm")) {
     	     return javaPath + "ui" + "/" + className + "Controller.java";
        }else if(template.equals("UiService.java.vm")) {
    	     return javaPath + "ui" + "/I" + className + "Service.java";
        }
        return null;
    }
	
    
    /****
     *把查询到的 ColumnInfo补充其余属性以及参数设置的下拉属性
     *@param dbColumnList 查询到的ColumnInfo的list集合
     *@return 转化的list集合
     **/
    private List<ColumnInfo> transColums(List<ColumnInfo> dbColumnList,GenerateCodeParamDto codeParamDto) {
    	List<ColumnInfo> retColumnList=new ArrayList<ColumnInfo>();
    	
    	List<String> searchColumns=StringUtil.getStrListBySplit(codeParamDto.getSearchColumns(),",");
    	List<String> onlyCheckColumns=StringUtil.getStrListBySplit(codeParamDto.getOnlyCheckColumns(),",");
    	List<String> tableShowColumns=StringUtil.getStrListBySplit(codeParamDto.getTableShowColumns(),",");
    	List<String> pullDicColumns=StringUtil.getStrListBySplit(codeParamDto.getPullDicColumns(),",");
    	List<String> pullDicTypeColumns=StringUtil.getStrListBySplit(codeParamDto.getPullDicTypeColumns(),",");
    	
    	for(ColumnInfo dbColumn:dbColumnList) {
    		  String attrName=StringUtil.convertToCamelCase(dbColumn.getColumnName());
    	      String attrname=StringUtil.convertToFirstLowerCase(attrName);
    	      String attrType = getJavaTypeByDataType(dbColumn.getDataType());
    	      dbColumn.setAttrName(attrName);
    	      dbColumn.setAttrname(attrname);
    	      dbColumn.setAttrType(attrType);
    	      //为方便理解和以后拓展，用循环方式加属性
    	      //判断是否为查询列
    	      for(String searchColumn:searchColumns) {
    	    	  if(dbColumn.getColumnName().equals(searchColumn)) {
    	    		  dbColumn.setSearchColumn(true);
    	    	  }
    	      }
    	      //判断唯一验证列
    	      for(String onlyCheckColumn:onlyCheckColumns) {
    	    	  if(dbColumn.getColumnName().equals(onlyCheckColumn)) {
    	    		  dbColumn.setOnlyCheckColumn(true);
    	    	  }
    	      }
    	      //判断列表展示列
    	      double tableShowColumnWidth= CalculateUtil.div(100, tableShowColumns.size()+1, 1);//有操作列所以加1
    	      for(String tableShowColumn:tableShowColumns) {
    	    	  if(dbColumn.getColumnName().equals(tableShowColumn)) {
    	    		  dbColumn.setTableShowColumn(true);
    	    		  dbColumn.setTableShowColumnWidth(tableShowColumnWidth);
    	    	  }
    	      }
    	      //因为下拉字典列和类型一一对应，所以就只能用i循环
    	      for(int i=0;i<pullDicColumns.size();i++) {
    	    	  if(dbColumn.getColumnName().equals(pullDicColumns.get(i))) {
    	    		  dbColumn.setPullDicColumn(true);
    	    		  dbColumn.setPullDicType(pullDicTypeColumns.get(i));
    	    	  }
    	      }
    	      retColumnList.add(dbColumn);
    	}
    	return retColumnList;
    }
    
    /***
     *根据数据库数据类型，获取到对应的java数据类型
     *@param  dataType 数据库数据类型
     *@return java数据类型
     ***/
    private String getJavaTypeByDataType(String dataType){
    	 Map<String, String> javaTypeMap = new HashMap<String, String>();
    	 javaTypeMap.put("tinyint", "Long");
         javaTypeMap.put("smallint", "Long");
         javaTypeMap.put("mediumint", "Long");
         javaTypeMap.put("int", "Long");
         javaTypeMap.put("integer", "Long");
         javaTypeMap.put("bigint", "Long");
         javaTypeMap.put("float", "Float");
         javaTypeMap.put("double", "Double");
         javaTypeMap.put("decimal", "BigDecimal");
         javaTypeMap.put("bit", "Boolean");
         javaTypeMap.put("char", "String");
         javaTypeMap.put("varchar", "String");
         javaTypeMap.put("tinytext", "String");
         javaTypeMap.put("text", "String");
         javaTypeMap.put("mediumtext", "String");
         javaTypeMap.put("longtext", "String");
 		 javaTypeMap.put("time", "Date");
         javaTypeMap.put("date", "Date");
         javaTypeMap.put("datetime", "Date");
         javaTypeMap.put("timestamp", "Date");
         return javaTypeMap.get(dataType);
    }
    
    /**
     * 获取模板参数信息 
     * @return 模板参数配置
     */
    private VelocityContext getVelocityContext(TableInfo table,GenerateCodeParamDto codeParamDto){
        String packageName =codeParamDto.getPackageName()==null?"com":codeParamDto.getPackageName();
        String author =codeParamDto.getAuthor()==null?"zhangysh":codeParamDto.getAuthor();

        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("removePrefixTableName", table.getRemovePrefixTableName());
        velocityContext.put("tableComment", replaceKeyWord(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("doclassName", table.getDoclassName());
        velocityContext.put("doclassname", table.getDoclassname());
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("package", packageName);
        velocityContext.put("author", author);
        velocityContext.put("datetime", DateOperate.StringdatetoStringYMD(DateOperate.getCurrentStrDate(), "yyyy-MM-dd", "yyyy年MM月dd日"));
        return velocityContext;
    }
    /**
     * 获取所有模板信息
     * @return 模板列表
     */
    private List<String> getTemplates()
    {
        List<String> templates = new ArrayList<String>();
        templates.add("templates/vm/java/DataObj.java.vm");
        templates.add("templates/vm/java/DataTransObj.java.vm");
        templates.add("templates/vm/java/DataViewObj.java.vm");
        templates.add("templates/vm/java/Controller.java.vm");
        templates.add("templates/vm/java/Service.java.vm");
        templates.add("templates/vm/java/ServiceImpl.java.vm");
        templates.add("templates/vm/java/Dao.java.vm");
        templates.add("templates/vm/xml/Dao.xml.vm");
        templates.add("templates/vm/html/list.html.vm");
        templates.add("templates/vm/html/add.html.vm");
        templates.add("templates/vm/html/edit.html.vm");
        templates.add("templates/vm/java/UiController.java.vm");
        templates.add("templates/vm/java/UiService.java.vm");
        return templates;
    }
    
    /***
     *实例化Velocity参数
     ****/
    private void initVelocity(){
	    Properties p = new Properties();
	    try{
	        // 加载classpath目录下的vm文件
	        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	        // 定义字符集
	        p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
	        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
	        Velocity.init(p);
	    }catch (Exception e){
	        throw new RuntimeException(e);
	    }
    }
    
    /****
     *表的注释去掉字符，不然注解很别扭 
     *@param keyWord 表注释
     ***/
    private String replaceKeyWord(String keyWord){
        String retStr = keyWord.replaceAll("(?:表|信息)", "");
        return retStr;
    }

}
