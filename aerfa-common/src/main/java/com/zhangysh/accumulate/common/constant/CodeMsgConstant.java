package com.zhangysh.accumulate.common.constant;
import com.zhangysh.accumulate.common.pojo.CodeMsg;

/**
 * 错误返回常量:CodeMsg
 * @author 创建者：zhangysh
 * @date 2018年9月12日
 */
public class CodeMsgConstant {
	
	/********** 系统账号密码提示对象 **********/
    // 账号密码空
	public static final CodeMsg SYS_ACCOUNT_PASSWORD_EMPTY_ERROR = new CodeMsg(10001, "账号或密码为空");
	// 账号密码数据库不匹配
	public static final CodeMsg SYS_ACCOUNT_PASSWORD_WRONG_ERROR = new CodeMsg(10002, "账号或密码错误");

	/********** 系统操作提示对象 **********/
	/** session失效提示 **/
	public static final CodeMsg SYS_USER_LOST_SESSION_ERROR = new CodeMsg(10003, "登录信息已失效");
	/** 数据唯一性校验失败，数据重复错误 **/
	public static final CodeMsg SYS_DATA_REPEAT_ERROR = new CodeMsg(10005, "数据重复");	
	/** 数据校验失败：密码不匹配 **/
	public static final CodeMsg SYS_DATA_VALIDATE_ERROR = new CodeMsg(10006, "校验失败");
	/** 数据操作失败：新增、修改、删除、刷新等提示 **/
	public static final CodeMsg SYS_DATA_HANDLER_ERROR = new CodeMsg(10004, "操作失败");
	/** 数据操作失败：填充详细提示 **/
	public static final CodeMsg SYS_DATA_OPERATE_ERROR = new CodeMsg(10004, "操作失败:%s");
	/** 获取读取数据失败 **/
	public static final CodeMsg SYS_DATA_ACHIEVE_ERROR = new CodeMsg(10007, "获取数据失败:%s");
	
	
	/********** 文件上传ufs提示对象 **********/
	public static final CodeMsg UFS_UPLOAD_FILE_ERROR = new CodeMsg(11001, "上传文件失败:%s");

}
