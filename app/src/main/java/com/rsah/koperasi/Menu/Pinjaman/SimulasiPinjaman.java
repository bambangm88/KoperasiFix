package com.rsah.koperasi.Menu.Pinjaman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rsah.koperasi.Auth.Register_Next_Simpan_New;
import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.Model.Data.DataAgsuran;
import com.rsah.koperasi.Model.Data.DataCompany;
import com.rsah.koperasi.Model.Json.JsonSimulasiPinjaman;
import com.rsah.koperasi.Model.Json.JsonUbahPwd;
import com.rsah.koperasi.Model.Response.ResponseAngsuran;
import com.rsah.koperasi.Model.Response.ResponseCompany;
import com.rsah.koperasi.Model.Response.ResponseSimulasiPinjaman;
import com.rsah.koperasi.Model.Response.ResponseUbahPwd;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class SimulasiPinjaman extends AppCompatActivity {



    ProgressDialog pDialog;

    EditText pwd_old, pwd_baru, pwd_confirm ;

    private Context mContext;
    private ApiService API;

    Button btn_ubah ;

    SessionManager session;
    private RelativeLayout rlprogress , rlprogressLoading;

    private ArrayList<String> arrayCompany = new ArrayList<String>();

    private List<DataAgsuran> AllCompanyList = new ArrayList<>();
    Spinner company ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_pinjaman);

        API = Server.getAPIService();
        mContext = this ;
        rlprogress = findViewById(R.id.rlprogress);

        pwd_old = findViewById(R.id.et_jumlahPinjaman) ;

        btn_ubah = findViewById(R.id.btn_ubah);
        session = new SessionManager(getApplicationContext());
        company = findViewById(R.id.spangsuran);
        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fetchAngsuran();

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlah_pinjaman = pwd_old.getText().toString();
                String tenor = company.getSelectedItem().toString();

                if (jumlah_pinjaman.equals("0") || jumlah_pinjaman.equals("")){
                    Toast.makeText(mContext, "Silahkan Isi Jumlah Pinjaman", Toast.LENGTH_LONG).show();
                }
                else if (tenor.equals("-- Angsuran --")){
                    Toast.makeText(mContext, "Silahkan Pilih Angsuran", Toast.LENGTH_LONG).show();
                }else{
                    JsonSimulasiPinjaman json = new JsonSimulasiPinjaman();
                    json.setJumlahPinjaman(jumlah_pinjaman);
                    json.setTenor(tenor);
                    json.setMemberID(session.getKeyId());
                    //json.setMemberID("2003000207");
                    generate(json);
                }
            }
        });

    }




    private void generate(JsonSimulasiPinjaman json){

    showProgress(true);
        Call<ResponseSimulasiPinjaman> call = API.simulasiPinjaman(json);
        call.enqueue(new Callback<ResponseSimulasiPinjaman>() {
            @Override
            public void onResponse(Call<ResponseSimulasiPinjaman> call, Response<ResponseSimulasiPinjaman> response) {
                if(response.isSuccessful()) {
                    showProgress(false);
                    if (response.body().getMetadata() != null) {

                        String message = response.body().getMetadata().getMessage();
                        String status = response.body().getMetadata().getCode();

                        if (status.equals(Constant.ERR_200)) {



                            Intent i = new Intent(mContext, DetailSimulasiPinjaman.class);
                            i.putExtra("asuransi", response.body().getResponse().getData().get(0).getfAsuransi());
                            i.putExtra("jasa", response.body().getResponse().getData().get(0).getfJasa());
                            i.putExtra("administrasi", response.body().getResponse().getData().get(0).getfAdministrasi());
                            i.putExtra("besarPencairan", response.body().getResponse().getData().get(0).getBesarPencairan());
                            i.putExtra("besarAngsuran", response.body().getResponse().getData().get(0).getBesar_angsuran());
                            startActivity(i);



                        }else{

                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                        }

                    }else{

                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }



                }else{
                    showProgress(false);
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSimulasiPinjaman> call, Throwable t) {

                showProgress(false);

                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+"Terjadi Gangguan Pada Server");
            }
        });
    }


    private void fetchAngsuran() {
        showProgress(true);
        API.fetchAngsuran().enqueue(new Callback<ResponseAngsuran>() {
            @Override
            public void onResponse(Call<ResponseAngsuran> call, Response<ResponseAngsuran> response) {
                if (response.isSuccessful()){
                    if (response.body().getMetadata() != null) {
                        String message = response.body().getMetadata().getMessage();
                        String status = response.body().getMetadata().getCode();
                        if (status.equals(Constant.ERR_200)) {
                            showProgress(false);
                            AllCompanyList.addAll(response.body().getResponse().getData());
                            arrayCompany.add("-- Angsuran --") ;
                            for(DataAgsuran model : AllCompanyList) {
                                if(!arrayCompany.contains(model.getTenor())){
                                    arrayCompany.add(model.getTenor()) ;
                                }
                            }
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, simple_spinner_item,arrayCompany );
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            company.setAdapter(spinnerArrayAdapter);
                        }else {
                            showProgress(false);
                            Toast.makeText(mContext,message, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        showProgress(false);
                        Toast.makeText(mContext,"Error response data", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    showProgress(false);
                    Toast.makeText(mContext,"Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAngsuran> call, Throwable t) {
                showProgress(false);
                Toast.makeText(mContext,"Terjadi Gangguan Pada Server", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showProgress(Boolean bool){

        if (bool){
            rlprogress.setVisibility(View.VISIBLE);
        }else {
            rlprogress.setVisibility(View.GONE);
        }
    }


    //homeback
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here

                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
