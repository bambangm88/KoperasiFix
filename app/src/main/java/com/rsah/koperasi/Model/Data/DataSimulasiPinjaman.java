package com.rsah.koperasi.Model.Data;

import com.google.gson.annotations.SerializedName;

public class DataSimulasiPinjaman {


    @SerializedName("besar_angsuran")
    private String besar_angsuran;

    @SerializedName("fAsuransi")
    private String fAsuransi;

    @SerializedName("fJasa")
    private String fJasa;

    @SerializedName("fAdministrasi")
    private String fAdministrasi;

    @SerializedName("BesarPencairan")
    private String BesarPencairan;


    public String getBesar_angsuran() {
        return besar_angsuran;
    }

    public void setBesar_angsuran(String besar_angsuran) {
        this.besar_angsuran = besar_angsuran;
    }

    public String getfAsuransi() {
        return fAsuransi;
    }

    public void setfAsuransi(String fAsuransi) {
        this.fAsuransi = fAsuransi;
    }

    public String getfJasa() {
        return fJasa;
    }

    public void setfJasa(String fJasa) {
        this.fJasa = fJasa;
    }

    public String getfAdministrasi() {
        return fAdministrasi;
    }

    public void setfAdministrasi(String fAdministrasi) {
        this.fAdministrasi = fAdministrasi;
    }

    public String getBesarPencairan() {
        return BesarPencairan;
    }

    public void setBesarPencairan(String besarPencairan) {
        BesarPencairan = besarPencairan;
    }











}
