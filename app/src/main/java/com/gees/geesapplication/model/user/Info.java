package com.gees.geesapplication.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SERVER on 11/09/2017.
 */

public class Info {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("role_name")
    @Expose
    private String roleName;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
