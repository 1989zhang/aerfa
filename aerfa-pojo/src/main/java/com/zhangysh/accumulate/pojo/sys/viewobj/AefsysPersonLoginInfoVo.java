package com.zhangysh.accumulate.pojo.sys.viewobj;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPersonLoginInfo;

/**
 * 个人登录前台展示对象
 * 
 * @author zhangysh
 * @date 2019年05月21日
 */
public class AefsysPersonLoginInfoVo extends AefsysPersonLoginInfo{

	private static final long serialVersionUID = 1L;

	//前台展示日期modelMap.put("X",x)模式所以从设日期格式属性
	private String loginInTimeStr;
	private String loginOutTimeStr;
	
	public String getLoginInTimeStr() {
		return loginInTimeStr;
	}
	public void setLoginInTimeStr(String loginInTimeStr) {
		this.loginInTimeStr = loginInTimeStr;
	}
	public String getLoginOutTimeStr() {
		return loginOutTimeStr;
	}
	public void setLoginOutTimeStr(String loginOutTimeStr) {
		this.loginOutTimeStr = loginOutTimeStr;
	}

}
