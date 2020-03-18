package com.zhangysh.accumulate.pojo.webim.viewobj;
import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimFriend;

/**
 * 好友前台展示对象
 * 
 * @author zhangysh
 * @date 2019年10月08日
 */
public class AefwebimFriendVo extends AefwebimFriend{

	private static final long serialVersionUID = 1L;
	
	//人员id继承AefwebimFriend
	private String username;//昵称(layim要求字段不能修改字段)
    private String status;//在线状态:online在线,hide隐身,offline离线(layim要求字段不能修改字段)
    private String sign;//心情签名(layim要求字段不能修改字段)
    private String avatar;//个人头像全路径(layim要求字段不能修改字段)

	private String type;//还有申请完成添加至面板，凭属性分类
	private Long groupid;//群id(layim要求字段不能修改字段)
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
}
