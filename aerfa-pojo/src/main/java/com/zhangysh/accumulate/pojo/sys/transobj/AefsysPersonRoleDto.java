package com.zhangysh.accumulate.pojo.sys.transobj;

import java.io.Serializable;

/**
 * 人员对应角色保存数据传输对象
 *
 * @author zhangysh
 * @date 2020年03月01日
 */
public class AefsysPersonRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long personId;
    private String roleIds;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
