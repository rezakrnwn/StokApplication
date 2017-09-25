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

}
