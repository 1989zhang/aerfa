package com.zhangysh.accumulate.pojo.sys.transobj;

/**
 * 保存角色和资源对应关系数据传输对象
 *
 * @author zhangysh
 * @date 2020年02月28日
 */
public class AefsysRoleDataPermissionDto {

    /** 角色ID **/
    private Long roleId;
    /** 数据权限的ids **/
    private String dataPermissionIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getDataPermissionIds() {
        return dataPermissionIds;
    }

    public void setDataPermissionIds(String dataPermissionIds) {
        this.dataPermissionIds = dataPermissionIds;
    }
}
