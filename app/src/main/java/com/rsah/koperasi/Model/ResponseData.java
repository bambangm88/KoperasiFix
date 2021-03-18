package com.rsah.koperasi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsah.koperasi.Model.Data.DataLogin;
import com.rsah.koperasi.Model.Data.DataSaldo;
import com.rsah.koperasi.Model.Data.DataPeserta;
import com.rsah.koperasi.Model.Data.DataBarang;
import com.rsah.koperasi.Model.Data.DataCompany;
import com.rsah.koperasi.Model.Response.ResponseRegister;

import java.util.List;


public class ResponseData {

    @SerializedName("dataRegister")
    @Expose
    private List<ResponseRegister> dataRegister = null;
    public List<ResponseRegister> getDataRegister() {
        return dataRegister;
    }

    public void setDataRegister(List<ResponseRegister> dataRegister) {
        this.dataRegister = dataRegister;
    }


    @SerializedName("dataCompany")
    @Expose
    private List<DataCompany> dataCompany = null;

    public List<DataCompany> getDataCompany() {
        return dataCompany;
    }

    public void setDataCompany(List<DataCompany> dataCompany) {
        this.dataCompany = dataCompany;
    }

    @SerializedName("dataLogin")
    @Expose
    private List<DataLogin> dataLogin = null;

    public List<DataLogin> getDataLogin() {
        return dataLogin;
    }

    public void setDataLogin(List<DataLogin> dataLogin) {
        this.dataLogin = dataLogin;
    }

    @SerializedName("Success")
    @Expose
    private String Success ;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    @SerializedName("token")
    @Expose
    private String token ;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    @SerializedName("data")
    @Expose
    private List<ResponseWilayah> data = null;


    public List<ResponseWilayah> getData() {
        return data;
    }

    public void setData(List<ResponseWilayah> data) {
        this.data = data;
    }




    @SerializedName("dataBarang")
    @Expose
    private List<DataBarang> dataBarang = null;
    public List<DataBarang> getDataBarang() {
        return dataBarang;
    }

    public void setDataBarang(List<DataBarang> dataBarang) {
        this.dataBarang = dataBarang;
    }


    @SerializedName("dataSaldo")
    @Expose
    private List<DataSaldo> dataSaldo = null;

    public List<DataSaldo> getDataSaldo() {
        return dataSaldo;
    }

    public void setDataSaldo(List<DataSaldo> dataSaldo) {
        this.dataSaldo = dataSaldo;
    }


    @SerializedName("dataPeserta")
    @Expose
    private List<DataPeserta> dataPeserta = null;

    public List<DataPeserta> getDataPeserta() {
        return dataPeserta;
    }

    public void setDataPeserta(List<DataPeserta> dataPeserta) {
        this.dataPeserta = dataPeserta;
    }

    @SerializedName("dataKeranjang")
    @Expose
    private List<DataBarang> dataKeranjang = null;


    public List<DataBarang> getDataKeranjang() {
        return dataKeranjang;
    }

    public void setDataKeranjang(List<DataBarang> dataKeranjang) {
        this.dataKeranjang = dataKeranjang;
    }




    @SerializedName("dataPesanan")
    @Expose
    private List<DataBarang> dataPesanan= null;

    public List<DataBarang> getDataPesanan() {
        return dataPesanan;
    }

    public void setDataPesanan(List<DataBarang> dataPesanan) {
        this.dataPesanan = dataPesanan;
    }















}







