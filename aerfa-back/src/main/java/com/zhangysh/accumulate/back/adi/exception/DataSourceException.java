package com.zhangysh.accumulate.back.adi.exception;

import com.zhangysh.accumulate.back.sys.base.BaseException;

/**
 * 创建动态数据源异常信息类
 * @author zhangysh
 * @date 2019年06月19日
 */
public class DataSourceException extends BaseException{

	private static final long serialVersionUID = 1L;

	public DataSourceException(String defaultMessage) {
		super(defaultMessage);
	}
}
