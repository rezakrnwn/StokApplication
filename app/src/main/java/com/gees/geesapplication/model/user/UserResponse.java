package com.gees.geesapplication.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SERVER on 11/09/2017.
 */

public class UserResponse {

    @SerializedName("info")
    @Expose
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
