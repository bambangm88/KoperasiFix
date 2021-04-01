package com.rsah.koperasi.Model.Data;

import com.google.gson.annotations.SerializedName;

public class DataSaldo {



    @SerializedName("saldo")
    private String saldo;

    @SerializedName("saldo_sukarela")
    private String saldo_sukarela;

    @SerializedName("saldo_wajib")
    private String saldo_wajib;

    @SerializedName("Tanggal")
    private String Tanggal;

    @SerializedName("Pokok")
    private String Pokok;

    @SerializedName("Wajib")
    private String Wajib;

    @SerializedName("Sukarela")
    private String Sukarela;

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getPokok() {
        return Pokok;
    }

    public void setPokok(String pokok) {
        Pokok = pokok;
    }

    public String getWajib() {
        return Wajib;
    }

    public void setWajib(String wajib) {
        Wajib = wajib;
    }

    public String getSukarela() {
        return Sukarela;
    }

    public void setSukarela(String sukarela) {
        Sukarela = sukarela;
    }


    public String getSaldo_sukarela() {
        return saldo_sukarela;
    }

    public void setSaldo_sukarela(String saldo_sukarela) {
        this.saldo_sukarela = saldo_sukarela;
    }

    public String getSaldo_wajib() {
        return saldo_wajib;
    }

    public void setSaldo_wajib(String saldo_wajib) {
        this.saldo_wajib = saldo_wajib;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }


































}
