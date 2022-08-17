package com.example.kakjayadi_warkopsharelok.data.model;

import com.google.gson.annotations.SerializedName;

public class PesananModel {
    @SerializedName("no")
    private String no;
    @SerializedName("id_pesanan")
    private String id_pesanan;
    @SerializedName("email")
    private String email;
    @SerializedName("nama")
    private String nama;
    @SerializedName("id_menu")
    private String id_menu;
    @SerializedName("menu")
    private String menu;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("jenis_menu")
    private String jenis_menu;
    @SerializedName("harga")
    private String harga;
    @SerializedName("jumlah")
    private String jumlah;
    @SerializedName("total")
    private String total;
    @SerializedName("kasir")
    private String kasir;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("tgl_pesanan")
    private String tgl_pesanan;

    public PesananModel(String no, String id_pesanan, String email, String nama,
                        String id_menu, String menu, String kategori, String jenis_menu,
                        String harga, String jumlah, String total, String kasir,
                        String keterangan, String tgl_pesanan) {

        this.no = no;
        this.id_pesanan = id_pesanan;
        this.email = email;
        this.nama = nama;
        this.id_menu = id_menu;
        this.menu = menu;
        this.kategori = kategori;
        this.jenis_menu = jenis_menu;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
        this.kasir = kasir;
        this.keterangan = keterangan;
        this.tgl_pesanan = tgl_pesanan;
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

    public String getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(String id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getKasir() {
        return kasir;
    }

    public void setKasir(String kasir) {
        this.kasir = kasir;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTgl_pesanan() {
        return tgl_pesanan;
    }

    public void setTgl_pesanan(String tgl_pesanan) {
        this.tgl_pesanan = tgl_pesanan;
    }
}
