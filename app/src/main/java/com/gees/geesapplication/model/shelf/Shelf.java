package com.gees.geesapplication.model.shelf;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ServerToko on 19/09/2017.
 */

public class Shelf {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<ShelfDatum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<ShelfDatum> getData() {
        return data;
    }

    public void setData(List<ShelfDatum> data) {
        this.data = data;
    }
}
