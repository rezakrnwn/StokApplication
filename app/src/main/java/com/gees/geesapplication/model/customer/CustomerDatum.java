package com.gees.geesapplication.model.customer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ServerToko on 19/09/2017.
 */

public class CustomerDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("id_provinsi")
    @Expose
    private Integer idProvinsi;
    @SerializedName("id_kota")
    @Expose
    private Integer idKota;
    @SerializedName("id_daerah")
    @Expose
    private Integer idDaerah;
    @SerializedName("customer_type")
    @Expose
    private Integer customerType;
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

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIdProvinsi() {
        return idProvinsi;
    }

    public void setIdProvinsi(Integer idProvinsi) {
        this.idProvinsi = idProvinsi;
    }

    public Integer getIdKota() {
        return idKota;
    }

    public void setIdKota(Integer idKota) {
        this.idKota = idKota;
    }

    public Integer getIdDaerah() {
        return idDaerah;
    }

    public void setIdDaerah(Integer idDaerah) {
        this.idDaerah = idDaerah;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
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
