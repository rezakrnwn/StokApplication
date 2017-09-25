package com.gees.geesapplication.model.satuan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ServerToko on 18/09/2017.
 */

public class SatuanDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("record_by")
    @Expose
    private Integer recordBy;
    @SerializedName("update_by")
    @Expose
    private Integer updateBy;
    @SerializedName("record_date")
    @Expose
    private String recordDate;
    @SerializedName("update_date")
    @Expose
    private String updateDate;
    @SerializedName("date_stamp")
    @Expose
    private String dateStamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getRecordBy() {
        return recordBy;
    }

    public void setRecordBy(Integer recordBy) {
        this.recordBy = recordBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }
}
