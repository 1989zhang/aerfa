package com.zhangysh.accumulate.back.tdm.exception;

import com.zhangysh.accumulate.back.sys.base.BaseException;

/**
 * 查询数据异常信息类
 * @author zhangysh
 * @date 2019年06月19日
 */
public class QueryDataException extends BaseException{

	private static final long serialVersionUID = 1L;

	public QueryDataException(String defaultMessage) {
		super(defaultMessage);
	}
}
