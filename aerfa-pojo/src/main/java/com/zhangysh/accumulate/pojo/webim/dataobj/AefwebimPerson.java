package com.zhangysh.accumulate.pojo.webim.dataobj;

import java.util.Date;
import com.zhangysh.accumulate.pojo.base.BaseDataObj;

/**
 * 个人数据对象，对应表： aefwebim_person
 * 
 * @author zhangysh
 * @date 2019年10月27日
 */
public class AefwebimPerson extends BaseDataObj{

	private static final long serialVersionUID = 1L;
	
	/** 个人id **/
	private Long personId;
	/** 心情签名 **/
	private String signature;
	/** 出生日期 **/
	private Date birthday;
	/** 职业 **/
	private String job;
	/** 星座 **/
	private String constellation;
	/** 血型 **/
	private String bloodType;
	/** 微信号 **/
	private String wechat;
	/** QQ号 **/
	private String qqNumber;

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	@Override
	public String toString() {
		return "AefwebimPerson [id=" + id + ",personId=" + personId + ",signature=" + signature + ",birthday=" + birthday + ",job=" + job + ",constellation=" + constellation + ",bloodType=" + bloodType + ",wechat=" + wechat + ",qqNumber=" + qqNumber + ",]";
    }
}
