package com.gees.geesapplication.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SERVER on 12/09/2017.
 */

public class SignupResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }
}
