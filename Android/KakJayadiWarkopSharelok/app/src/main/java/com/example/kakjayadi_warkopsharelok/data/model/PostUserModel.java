package com.example.kakjayadi_warkopsharelok.data.model;

import com.google.gson.annotations.SerializedName;

public class PostUserModel {
    @SerializedName("nama")
    private String nama;
    @SerializedName("nomor_wa")
    private String nomor_wa;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("sebagai")
    private String sebagai;
    @SerializedName("shift")
    private String shift;

    public PostUserModel(String nama, String nomor_wa, String email, String password, String sebagai, String shift) {
        this.nama = nama;
        this.nomor_wa = nomor_wa;
        this.email = email;
        this.password = password;
        this.sebagai = sebagai;
        this.shift = shift;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor_wa() {
        return nomor_wa;
    }

    public void setNomor_wa(String nomor_wa) {
        this.nomor_wa = nomor_wa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSebagai() {
        return sebagai;
    }

    public void setSebagai(String sebagai) {
        this.sebagai = sebagai;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
