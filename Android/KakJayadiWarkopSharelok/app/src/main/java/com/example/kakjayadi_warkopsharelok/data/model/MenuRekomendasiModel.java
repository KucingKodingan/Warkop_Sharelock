package com.example.kakjayadi_warkopsharelok.data.model;

import com.google.gson.annotations.SerializedName;

public class MenuRekomendasiModel {
    @SerializedName("id_menu")
    private String id_menu;
    @SerializedName("nama_menu")
    private String nama_menu;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("jenis_menu")
    private String jenis_menu;
    @SerializedName("harga")
    private String harga;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("kapasitas")
    private String kapasitas;
    @SerializedName("jumlah_pesanan")
    private String jumlah_pesanan;

    @SerializedName("nominal_awal")
    private String nominal_awal;
    @SerializedName("nominal_batas")
    private String nominal_batas;

    public MenuRekomendasiModel(String id_menu, String nama_menu, String kategori, String jenis_menu, String harga, String gambar, String kapasitas, String jumlah_pesanan, String nominal_awal, String nominal_batas) {
        this.id_menu = id_menu;
        this.nama_menu = nama_menu;
        this.kategori = kategori;
        this.jenis_menu = jenis_menu;
        this.harga = harga;
        this.gambar = gambar;
        this.kapasitas = kapasitas;
        this.jumlah_pesanan = jumlah_pesanan;
        this.nominal_awal = nominal_awal;
        this.nominal_batas = nominal_batas;
    }

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getNama_menu() {
        return nama_menu;
    }

    public void setNama_menu(String nama_menu) {
        this.nama_menu = nama_menu;
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getJumlah_pesanan() {
        return jumlah_pesanan;
    }

    public void setJumlah_pesanan(String jumlah_pesanan) {
        this.jumlah_pesanan = jumlah_pesanan;
    }

    public String getNominal_awal() {
        return nominal_awal;
    }

    public void setNominal_awal(String nominal_awal) {
        this.nominal_awal = nominal_awal;
    }

    public String getNominal_batas() {
        return nominal_batas;
    }

    public void setNominal_batas(String nominal_batas) {
        this.nominal_batas = nominal_batas;
    }
}
