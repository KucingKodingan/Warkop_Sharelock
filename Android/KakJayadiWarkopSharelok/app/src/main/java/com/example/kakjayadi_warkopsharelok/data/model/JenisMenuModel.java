package com.example.kakjayadi_warkopsharelok.data.model;

import com.google.gson.annotations.SerializedName;

public class JenisMenuModel {
    @SerializedName("id_jenis_menu")
    private String id_jenis_menu;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("jenis_menu")
    private String jenis_menu;

    public JenisMenuModel(String id_jenis_menu, String kategori, String jenis_menu) {
        this.id_jenis_menu = id_jenis_menu;
        this.kategori = kategori;
        this.jenis_menu = jenis_menu;
    }

    public String getId_jenis_menu() {
        return id_jenis_menu;
    }

    public void setId_jenis_menu(String id_jenis_menu) {
        this.id_jenis_menu = id_jenis_menu;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJenis_menu() {
        return jenis_menu;
    }

    public void setJenis_menu(String jenis_menu) {
        this.jenis_menu = jenis_menu;
    }
}
