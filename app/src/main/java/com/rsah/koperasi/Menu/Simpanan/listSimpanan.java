package com.rsah.koperasi.Menu.Simpanan;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.Menu.Barang.Adapter.BarangAdapter;
import com.rsah.koperasi.Menu.Barang.Adapter.SimpananAdapter;
import com.rsah.koperasi.Model.Data.DataBarang;
import com.rsah.koperasi.Model.Data.DataSimpanan;
import com.rsah.koperasi.Model.Json.JsonSimpananSukarela;
import com.rsah.koperasi.Model.Response.ResponseBarang;
import com.rsah.koperasi.Model.Response.ResponseSimpanan;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listSimpanan extends AppCompatActivity {



    ProgressDialog pDialog;
    ApiService API;
    private Spinner spinnerMonitor;
    private SessionManager sessionManager ;

    private RecyclerView rvTeam;
    private List<DataSimpanan> AllSimpananList = new ArrayList<>();
    private SimpananAdapter Adapter;

    private Context mContext;
    private FloatingActionButton fabAddSimpanan ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_simpanan_sukarela);

        mContext = this ;
        API = Server.getAPIService();

        sessionManager = new SessionManager(this);

        rvTeam = findViewById(R.id.rvSimpananSukarela);

        fabAddSimpanan = findViewById(R.id.btnAdd);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvTeam.setLayoutManager(mLayoutManager);
        rvTeam.setItemAnimator(new DefaultItemAnimator());

        Adapter = new SimpananAdapter(this, AllSimpananList);

        pDialog = new ProgressDialog(listSimpanan.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");


        JsonSimpananSukarela json = new JsonSimpananSukarela();
        json.setMemberID(sessionManager.getKeyId());
        getSimpanan(json);

        fabAddSimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(listSimpanan.this,UploadSimpanan.class));
            }
        });

    }



    private void getSimpanan(JsonSimpananSukarela json){

        pDialog.show();
        Call<ResponseSimpanan> call = API.getSimpananSukarela(json);
        call.enqueue(new Callback<ResponseSimpanan>() {
            @Override
            public void onResponse(Call<ResponseSimpanan> call, Response<ResponseSimpanan> response) {
                if(response.isSuccessful()) {

                    if (response.body().getMetadata() != null) {

                        String message = response.body().getMetadata().getMessage();
                        String status = response.body().getMetadata().getCode();

                        if (status.equals(Constant.ERR_200)) {

                            pDialog.cancel();
                            AllSimpananList.addAll(response.body().getResponse().getData());
                            rvTeam.setAdapter(new SimpananAdapter(mContext, AllSimpananList));
                            Adapter.notifyDataSetChanged();

                        }else{
                            pDialog.cancel();
                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                        }

                    }else {
                        pDialog.cancel();
                        finish();
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    pDialog.cancel();
                    finish();
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSimpanan> call, Throwable t) {

                pDialog.cancel();
                finish();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }







}
