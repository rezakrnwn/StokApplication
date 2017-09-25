package com.gees.geesapplication.model.supplier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ServerToko on 19/09/2017.
 */

public class SupplierDatum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_company")
    @Expose
    private Integer idCompany;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("telepon")
    @Expose
    private String telepon;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_person_telepone")
    @Expose
    private String contactPersonTelepone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
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

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonTelepone() {
        return contactPersonTelepone;
    }

    public void setContactPersonTelepone(String contactPersonTelepone) {
        this.contactPersonTelepone = contactPersonTelepone;
    }
}
