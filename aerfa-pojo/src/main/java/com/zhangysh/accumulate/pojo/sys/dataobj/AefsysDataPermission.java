package com.zhangysh.accumulate.pojo.sys.dataobj;

import com.zhangysh.accumulate.pojo.base.BaseDataObj;
/**
 * 数据权限数据对象，对应表： aefsys_data_permission
 * 
 * @author zhangysh
 * @date 2020年02月16日
 */
public class AefsysDataPermission extends BaseDataObj {

	private static final long serialVersionUID = 1L;

	/** 表名标识 **/
	private String tableNameIdentify;
	/** 权限类型:1无权限控制，2自定义权限，3本单位创建数据，4本人创建数据 **/
	private String permissionType;
	/** 权限SQL **/
	private String permissionSql;
	/** 角色id **/
	private Long roleId;
	/** 备注描述 **/
	private String remark;

	public String getTableNameIdentify() {
		return tableNameIdentify;
	}
	public void setTableNameIdentify(String tableNameIdentify) {
		this.tableNameIdentify = tableNameIdentify;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	public String getPermissionSql() {
		return permissionSql;
	}
	public void setPermissionSql(String permissionSql) {
		this.permissionSql = permissionSql;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "AefsysDataPermission [id=" + id + ",tableNameIdentify=" + tableNameIdentify + ",permissionType=" + permissionType + ",permissionSql=" + permissionSql + ",roleId=" + roleId + ",remark=" + remark + ",createBy=" + createBy + ",createTime=" + createTime + ",updateBy=" + updateBy + ",updateTime=" + updateTime + ",]";
    }
}
