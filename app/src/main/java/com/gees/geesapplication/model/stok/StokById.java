package com.gees.geesapplication.model.stok;

import com.gees.geesapplication.model.items.Stok;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ServerToko on 22/09/2017.
 */

public class StokById {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("stok")
    @Expose
    private List<Stok> stok = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Stok> getStok() {
        return stok;
    }

    public void setStok(List<Stok> stok) {
        this.stok = stok;
    }
}
