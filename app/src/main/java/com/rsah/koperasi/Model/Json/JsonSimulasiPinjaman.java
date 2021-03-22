package com.rsah.koperasi.Model.Json;

import com.google.gson.annotations.SerializedName;

public class JsonSimulasiPinjaman {

    @SerializedName("memberID")
    private String memberID;

    @SerializedName("jumlahPinjaman")
    private String jumlahPinjaman;

    @SerializedName("tenor")
    private String tenor;

    public String getJumlahPinjaman() {
        return jumlahPinjaman;
    }

    public void setJumlahPinjaman(String jumlahPinjaman) {
        this.jumlahPinjaman = jumlahPinjaman;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }



    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }





}
