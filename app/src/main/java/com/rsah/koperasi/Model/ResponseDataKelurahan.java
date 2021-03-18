package com.rsah.koperasi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseDataKelurahan {



    @SerializedName("data")
    @Expose
    private List<ResponseKelurahan> data = null;


    public List<ResponseKelurahan> getData() {
        return data;
    }

    public void setData(List<ResponseKelurahan> data) {
        this.data = data;
    }







}





