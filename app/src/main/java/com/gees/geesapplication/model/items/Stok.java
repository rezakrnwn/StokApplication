package com.gees.geesapplication.model.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SERVER on 11/09/2017.
 */

public class Stok {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_gudang")
    @Expose
    private String idGudang;
    @SerializedName("id_shelf")
    @Expose
    private String idShelf;
    @SerializedName("id_company")
    @Expose
    private String idCompany;
    @SerializedName("id_barang")
    @Expose
    private String idBarang;
    @SerializedName("id_supplier")
    @Expose
    private String idSupplier;
    @SerializedName("id_customer")
    @Expose
    private String idCustomer;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("stok_awal")
    @Expose
    private String stokAwal;
    @SerializedName("masuk")
    @Expose
    private String masuk;
    @SerializedName("keluar")
    @Expose
    private String keluar;
    @SerializedName("stok_akhir")
    @Expose
    private String stokAkhir;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("date_stamp")
    @Expose
    private String dateStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGudang() {
        return idGudang;
    }

    public void setIdGudang(String idGudang) {
        this.idGudang = idGudang;
    }

    public String getIdShelf() {
        return idShelf;
    }

    public void setIdShelf(String idShelf) {
        this.idShelf = idShelf;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }

    public String getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(String idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStokAwal() {
        return stokAwal;
    }

    public void setStokAwal(String stokAwal) {
        this.stokAwal = stokAwal;
    }

    public String getMasuk() {
        return masuk;
    }

    public void setMasuk(String masuk) {
        this.masuk = masuk;
    }

    public String getKeluar() {
        return keluar;
    }

    public void setKeluar(String keluar) {
        this.keluar = keluar;
    }

    public String getStokAkhir() {
        return stokAkhir;
    }

    public void setStokAkhir(String stokAkhir) {
        this.stokAkhir = stokAkhir;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }
}
