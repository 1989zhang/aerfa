package com.zhangysh.accumulate.pojo.webim.viewobj;

import com.zhangysh.accumulate.pojo.webim.dataobj.AefwebimPerson;

/**
 * 个人数据对象，对应表： aefwebim_person
 * 
 * @author zhangysh
 * @date 2019年10月27日
 */
public class AefwebimPersonVo extends AefwebimPerson{

	private static final long serialVersionUID = 1L;
	
	private String username;//昵称(layim要求字段不能修改字段,为统一保持一致)
    private String status;//在线状态:online在线,hide隐身,offline离线(layim要求字段不能修改字段,为统一保持一致)
    private String avatar;//个人头像全路径(layim要求字段不能修改字段,为统一保持一致)
	private String phoneNo;//和sysPerson保持一致
	private String email;//和sysPerson保持一致
	private String personSex;//和sysPerson保持一致
	
	
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPersonSex() {
		return personSex;
	}
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	
}
