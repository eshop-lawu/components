package com.lawu.shiro.model;

import java.util.List;

/**
 * @author Leach
 * @date 2017/4/18
 */
public class ShiroRole {

    private String roleKey;

    private List<String> permissionsKey;

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public List<String> getPermissionsKey() {
        return permissionsKey;
    }

    public void setPermissionsKey(List<String> permissionsKey) {
        this.permissionsKey = permissionsKey;
    }
}
