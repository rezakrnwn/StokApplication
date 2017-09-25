package com.gees.geesapplication.model.satuan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ServerToko on 18/09/2017.
 */

public class Satuan {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<SatuanDatum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<SatuanDatum> getData() {
        return data;
    }

    public void setData(List<SatuanDatum> data) {
        this.data = data;
    }
}
