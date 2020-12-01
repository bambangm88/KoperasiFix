package com.rsah.koperasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rsah.koperasi.Auth.Login;
import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Menu.Barang.MenuBarang;
import com.rsah.koperasi.Menu.Pengaturan;
import com.rsah.koperasi.Menu.Pinjaman.MainPinjaman;
import com.rsah.koperasi.Menu.Profile.Profile;
import com.rsah.koperasi.Menu.Saldo.DetailSaldo;

import com.rsah.koperasi.Model.Json.JsonSaldo;
import com.rsah.koperasi.Model.Response.ResponseSaldo;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    TextView txtUser , txtTelp , id_karyawan ;

    ImageView iv_face ;

    ProgressDialog pDialog;

    SessionManager sessionManager ;

    CardView cvSetting, cvPeserta , cvBarang , cvSaldo , cvPInjaman  ;

    private Context mContext;
    private ApiService API;

    public static String saldo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        txtUser = findViewById(R.id.Username);
       // txtEmail = findViewById(R.id.email);
        txtTelp = findViewById(R.id.telp);
        id_karyawan= findViewById(R.id.id_karyawan);
        //id_koperasi= findViewById(R.id.id_koperasi);

        cvSetting = findViewById(R.id.cv_Setting);
        cvPeserta= findViewById(R.id.cv_Peserta);
        cvBarang= findViewById(R.id.cv_barang);
        cvSaldo= findViewById(R.id.card_saldo);
        cvPInjaman= findViewById(R.id.cv_Pinjaman);

        iv_face = findViewById(R.id.iv_face);

        Toolbar toolbar ;


       // txtEmail.setText(sessionManager.getKeyEmail());
        txtUser.setText(sessionManager.getUsername());
        txtTelp.setText(sessionManager.getNoTelp() + " | " + sessionManager.getKeyEmail());
        id_karyawan.setText("ID KARY : "+sessionManager.getKeyIdCard() + " | "+ "ID KOP : "+sessionManager.getKeyId());
        //id_koperasi.setText("ID KOP : "+sessionManager.getKeyId());

        toolbar = (Toolbar) findViewById(R.id.toolbar_pay);
        setSupportActionBar(toolbar);

        String url = this.getString(R.string.baseImageUrl)+sessionManager.getImageUrl() ;


        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        int w = resource.getWidth() ;
                        int h = resource.getHeight() ;

                        if (w > h){
                            iv_face.setImageBitmap(resource);
                            iv_face.setRotation(90);
                        }


                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });













        mContext = this ;
        API = Server.getAPIService();

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");



        cvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Pengaturan.class));

            }
        });

        cvPeserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Profile.class));

            }
        });

        cvPInjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MainPinjaman.class));

            }
        });


        cvBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MenuBarang.class));

            }
        });


        cvSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, DetailSaldo.class);
                i.putExtra("idkop", sessionManager.getKeyId());
                i.putExtra("companyname", sessionManager.getKeyIdCompany());
                i.putExtra("name", sessionManager.getUsername());
                i.putExtra("saldo", saldo);


                mContext.startActivity(i);


            }
        });





        checkSaldo();


    }





    private void checkSaldo(){

        JsonSaldo json = new JsonSaldo();
        json.setMemberID(sessionManager.getKeyId());
        pDialog.show();
        Call<ResponseSaldo> call = API.getSaldo(json);
        call.enqueue(new Callback<ResponseSaldo>() {
            @Override
            public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                if(response.isSuccessful()) {
                    if (response.body().getMetadata() != null) {


                        String message = response.body().getMetadata().getMessage() ;
                        String status = response.body().getMetadata().getCode() ;

                        if(status.equals(Constant.ERR_200)){

                            pDialog.cancel();
                            String sal = response.body().getResponse().getData().get(0).getSaldo();
                            if (sal == null || sal.equals("null")){
                                saldo = "Rp 0";
                            }else {
                                saldo = sal ;
                            }
                            //saldo.setText("Rp "+response.body().getDataSaldo().get(0).getSaldo());

                        }else{
                            pDialog.cancel();
                            //Toast.makeText(mContext, "Email / Password salah", Toast.LENGTH_LONG).show();
                            saldo = "Rp 0";
                        }

                    }else{
                        pDialog.cancel();
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    pDialog.cancel();
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSaldo> call, Throwable t) {

                pDialog.cancel();

                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }






}
