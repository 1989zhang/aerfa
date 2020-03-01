package com.zhangysh.accumulate.pojo.sys.transobj;

/**
 * 保存角色和资源对应关系数据传输对象
 *
 * @author zhangysh
 * @date 2020年02月28日
 */
public class AefsysRoleResourceDto {

    /** 角色ID **/
    private Long roleId;
    /** 资源ID用,隔开 **/
    private String resourceIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
