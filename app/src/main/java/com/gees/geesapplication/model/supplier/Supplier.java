package com.gees.geesapplication.model.supplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ServerToko on 19/09/2017.
 */

public class Supplier {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<SupplierDatum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<SupplierDatum> getData() {
        return data;
    }

    public void setData(List<SupplierDatum> data) {
        this.data = data;
    }
}
