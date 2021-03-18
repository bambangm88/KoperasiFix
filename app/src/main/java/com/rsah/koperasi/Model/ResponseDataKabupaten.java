package com.rsah.koperasi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseDataKabupaten {



    @SerializedName("data")
    @Expose
    private List<ResponseKabupaten> data = null;


    public List<ResponseKabupaten> getData() {
        return data;
    }

    public void setData(List<ResponseKabupaten> data) {
        this.data = data;
    }







}





