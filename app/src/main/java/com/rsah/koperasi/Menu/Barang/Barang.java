package com.rsah.koperasi.Menu.Barang;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;


import com.rsah.koperasi.Menu.Barang.Adapter.BarangAdapter;
import com.rsah.koperasi.Model.ResponseData;
import com.rsah.koperasi.Model.ResponseEntityBarang;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Barang extends AppCompatActivity {



    ProgressDialog pDialog;
    ApiService API;
    private Spinner spinnerMonitor;
    private SessionManager sessionManager ;

    private RecyclerView rvTeam;
    private List<ResponseEntityBarang> AllBarangList = new ArrayList<>();
    private BarangAdapter Adapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        mContext = this ;
        API = Server.getAPIService();

        sessionManager = new SessionManager(this);

        rvTeam = findViewById(R.id.rvRoom_search_doctor);

        rvTeam.setLayoutManager(new GridLayoutManager(this, 2));
        rvTeam.setItemAnimator(new DefaultItemAnimator());

        Adapter = new BarangAdapter(this, AllBarangList);

        pDialog = new ProgressDialog(Barang.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");



        getBarang();


    }



    private void getBarang(){


        pDialog.show();


        Call<ResponseData> call = API.getBarang();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getDataBarang() != null) {

                        if(!response.body().getDataBarang().isEmpty()){

                            pDialog.cancel();
                            AllBarangList.addAll(response.body().getDataBarang());
                            rvTeam.setAdapter(new BarangAdapter(mContext, AllBarangList));
                            Adapter.notifyDataSetChanged();

                        }else{

                            pDialog.cancel();
                            finish();
                            Toast.makeText(mContext, "Team Tidak Ditemukan", Toast.LENGTH_LONG).show();

                        }


                    }else{
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
            public void onFailure(Call<ResponseData> call, Throwable t) {

                pDialog.cancel();
                finish();
                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }







}
