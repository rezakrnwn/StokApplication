package com.gees.geesapplication.model.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SERVER on 11/09/2017.
 */

public class Items {



    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("barang")
    @Expose
    private List<Barang> barang = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Barang> getBarang() {
        return barang;
    }

    public void setBarang(List<Barang> barang) {
        this.barang = barang;
    }
}
