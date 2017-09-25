package com.gees.geesapplication.model.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ServerToko on 19/09/2017.
 */

public class Customer {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<CustomerDatum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CustomerDatum> getData() {
        return data;
    }

    public void setData(List<CustomerDatum> data) {
        this.data = data;
    }
}
