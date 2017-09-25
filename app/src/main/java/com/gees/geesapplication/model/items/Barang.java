package com.gees.geesapplication.model.items;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SERVER on 11/09/2017.
 */

public class Barang {

    private int recyclerType;

    private String idSelected;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("stok_akhir")
    @Expose
    private String stokAkhir;
    @SerializedName("stok")
    @Expose
    private List<Stok> stok = null;

    public void setIdSelected(String idSelected) {
        this.idSelected = idSelected;
    }

    public String getIdSelected() {
        return idSelected;
    }

    public void setRecyclerType(int recyclerType) {
        this.recyclerType = recyclerType;
    }

    public int getRecyclerType() {
        return recyclerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStokAkhir() {
        return stokAkhir;
    }

    public void setStokAkhir(String stokAkhir) {
        this.stokAkhir = stokAkhir;
    }

    public List<Stok> getStok() {
        return stok;
    }

    public void setStok(List<Stok> stok) {
        this.stok = stok;
    }
}
