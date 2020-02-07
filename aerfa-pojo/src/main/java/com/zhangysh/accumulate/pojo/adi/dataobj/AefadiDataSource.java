package com.zhangysh.accumulate.pojo.adi.dataobj;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据源数据对象，对应表： aefadi_data_source
 * 
 * @author zhangysh
 * @date 2019年07月08日
 */
public class AefadiDataSource implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** id标识(动态数据源的标示id+code)**/
	private Long id;
	/** 数据源编码 (动态数据源的标示id+code)**/
	private String code;
	/** 数据源名称 **/
	private String name;
	/** 数据源地址 **/
	private String url;
	/** 登录账号 **/
	private String username;
	/** 登录密码 **/
	private String password;
	/** 类型mysql,oracle **/
	private String type;
	/** 备注描述 **/
	private String remark;
	/** 创建人员名称 **/
	private String createBy;
	/** 创建时间 **/
	private Date createTime;
	/** 更新人员名称 **/
	private String updateBy;
	/** 更新时间 **/
	private Date updateTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDataSourceId() {
		return this.getId()+this.getCode();
	}
	
	@Override
	public String toString() {
		return "AefadiDataSource [id=" + id + ",code=" + code + ",name=" + name + ",url=" + url + ",username=" + username + ",password=" + password + ",type=" + type + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
