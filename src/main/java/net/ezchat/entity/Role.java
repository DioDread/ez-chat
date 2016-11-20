package net.ezchat.entity;

import javax.json.Json;
import javax.json.JsonObject;

public class Role {

    private int roleId;
    private String roleName;

    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    /**
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("roleId", roleId)
                .add("roleName", roleName)
                .build();
    }
}
