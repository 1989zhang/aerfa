package com.zhangysh.accumulate.common.constant;

/*****
 * 系统数据库定义相关的属性常量，用来做判断和默认值等
 * @author zhangysh
 * @date 2019年5月28日
 *****/
public class SysDefineConstant {

	/**********系统名称参数配置常量**********/
	/**系统名称全称配置参数**/
	public static final String CONFIG_DATA_FULL_SYSTEM_NAME="fullSystemName";
	/**系统名称简称配置参数**/
	public static final String CONFIG_DATA_SHORT_SYSTEM_NAME="shortSystemName";
	/**系统图片头像服务器ip访问地址前缀即nginx代理地址配置参数**/
	public static final String CONFIG_DATA_SYS_PIC_IP_ADDRESS="sysPicIpAddress";
	/** 系统信息发布html服务器ip访问地址前缀即nginx代理地址配置参数 **/
	public static final String CONFIG_DATA_SYS_HTML_IP_ADDRESS="sysHtmlIpAddress";

	/**template报表模板相关存储路径配置参数要带结尾符**/
	public static final String CONFIG_DATA_TEMPLATE_FOLDER_PATH="templateFolderPath";
	/**template报表模板相关的模板子文件夹，前面是储路径配置参数 **/
	public static final String TEMPLATE_FOLDER_PATH_TEMPLATE_FILE="templateFile";
	/**template报表模板相关的临时文件生成导出展示文件的文件夹，前面是储路径配置参数 **/
	public static final String TEMPLATE_FOLDER_PATH_EXPORT_FILE="exportFile";



	/********** 系统默认个人头像路径 **********/
	public static final String SYS_PERSON_DEFAULT_HEAD_PIC="/aerfa/myresource/images/default_personal.jpg";


	/********** 数据库默认字段常量 **********/
	/** 数据库默认id字段 **/
	public static final String DB_COLUMN_ID="id";
	/** 数据库默认排序降序 **/
	public static final String DB_ORDER_BY_DESC="desc";

	/**********系统数据库参数配置常量**********/
	/** 系统定时任务相关定义变量：job执行名称 **/
	public static final String JOB_EXECUTE_NAME="JOB_EXECUTE_NAME";
	/** 系统定时任务相关定义变量：job执行参数 **/
	public static final String JOB_EXECUTE_PROPERTIES="JOB_EXECUTE_PROPERTIES";
	/**数据库多个数据默认取第一个的标记0**/
	public static final Integer DB_DEFAULT_FIRST_ARR=0;
	/**数据库行政区划查询顶级区划的父级区划，即没有的区划序号**/
	public static final Long DB_DIVISION_TOP_PARENT_ID=0L;
	/**数据库行政区划查询中国的序号**/
	public static final Long DB_DIVISION_CHINA_ID=1L;
	/**数据库默认上下班时间定义的序号1,id不能从0开始**/
	public static final Long DB_WORK_DAY_TIME_DEFINE_ID=1L;

	/**数据库人员登录状态在线标记1**/
	public static final Long DB_LOGIN_STATUS_ON_LINE=1L;
	/**数据库人员登录状态离线标记0**/
	public static final Long DB_LOGIN_STATUS_OFF_LINE=0L;
	/**数据库工作日上班状态上班标记1**/
	public static final Long DB_WORK_DAY_STATUS_DUTY=1L;
	/**数据库工作日上班状态不上班标记0**/
	public static final Long DB_WORK_DAY_STATUS_RUSH=0L;

	/** 超级管理员人员ID **/
	public static final Long PERSON_ID_SUPERMASTER=1L;
	/** 智能小法人员ID **/
	public static final Long PERSON_ID_WEBAIXF=10L;
	/** 超级管理员角色ID **/
	public static final Long ROLE_ID_SUPERADMIN=1L;



	/**********系统数据字典参数配置常量**********/
	/** 数据字典通过状态是1 **/
	public static final Long DIC_COMMON_STATUS_YES=1L;
	/** 数据字典通用状态否0 **/
	public static final Long DIC_COMMON_STATUS_NO=0L;
	/**数据库可用状态的可用标记1**/
	public static final Long DIC_USEABLE_STATUS_VALID=1L;
	/**数据库可用状态的不可用标记0**/
	public static final Long DIC_USEABLE_STATUS_INVALID=0L;
	/** 数据字典结果状态成功1 ***/
	public static final Long DIC_RESULT_STATUS_SUCESS=1L;
	/** 数据字典结果状态失败0 ***/
	public static final Long DIC_RESULT_STATUS_FAIL=0L;
	/** 数据字典资源类型系统system ***/
	public static final String DIC_RESOURCE_TYPE_SYSTEM="system";
	/** 数据字典资源类型模块module ***/
	public static final String DIC_RESOURCE_TYPE_MODULE="module";
	/** 数据字典资源类型菜单menu ***/
	public static final String DIC_RESOURCE_TYPE_MENU="menu";
	/** 数据字典资源类型按钮button ***/
	public static final String DIC_RESOURCE_TYPE_BUTTON="button";
	/** 系统定时任务相关定义变量：job执行计划等待执行 **/
	public static final String DIC_EXECUTE_POLICY_WAITING ="1";
	/** 系统定时任务相关定义变量：job执行计划立即执行 **/
	public static final String DIC_EXECUTE_POLICY_IMMEDIATELY ="2";
	/** 数据字典信息发布类型通知信息tzxx**/
	public static final String DIC_PUBLISH_INFO_TYPE_TZXX ="tzxx";
	/** 数据字典信息发布类型工作动态gzdt**/
	public static final String DIC_PUBLISH_INFO_TYPE_GZDT ="gzdt";



}
