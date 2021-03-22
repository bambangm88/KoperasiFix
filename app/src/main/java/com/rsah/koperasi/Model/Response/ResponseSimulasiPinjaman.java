package com.rsah.koperasi.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rsah.koperasi.Model.Data.DataSaldo;
import com.rsah.koperasi.Model.Data.DataSimulasiPinjaman;

import java.util.List;

public class ResponseSimulasiPinjaman {

    @SerializedName("metadata")
    private Metadata metadata = null;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @SerializedName("response")
    private Response response = null;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {

        @SerializedName("data")
        @Expose
        private List<DataSimulasiPinjaman> data = null;


        public List<DataSimulasiPinjaman> getData() {
            return data;
        }

        public void setData(List<DataSimulasiPinjaman> data) {
            this.data = data;
        }





    }


}
