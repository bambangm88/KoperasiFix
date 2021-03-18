package com.rsah.koperasi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseDataKecamatan {



    @SerializedName("data")
    @Expose
    private List<ResponseKecamatan> data = null;


    public List<ResponseKecamatan> getData() {
        return data;
    }

    public void setData(List<ResponseKecamatan> data) {
        this.data = data;
    }







}





