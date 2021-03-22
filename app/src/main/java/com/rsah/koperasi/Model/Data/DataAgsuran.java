package com.rsah.koperasi.Model.Data;

import com.google.gson.annotations.SerializedName;

public class DataAgsuran {




    @SerializedName("ID")
    private String ID;


    @SerializedName("Tenor")
    private String Tenor;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenor() {
        return Tenor;
    }

    public void setTenor(String tenor) {
        Tenor = tenor;
    }


























}
