package com.rsah.koperasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.library.BuildConfig;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.developer.kalert.KAlertDialog;
import com.google.android.material.snackbar.Snackbar;
import com.rsah.koperasi.Auth.Login;
import com.rsah.koperasi.Auth.Register_Next_Simpan_New;
import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.Constant.VersionChecker;
import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Menu.Barang.MenuBarang;
import com.rsah.koperasi.Menu.Pengaturan;
import com.rsah.koperasi.Menu.Pinjaman.MainPinjaman;
import com.rsah.koperasi.Menu.Pinjaman.SimulasiPinjaman;
import com.rsah.koperasi.Menu.Pinjaman.TrackPinjaman;
import com.rsah.koperasi.Menu.Profile.Profile;
import com.rsah.koperasi.Menu.Saldo.DetailSaldo;

import com.rsah.koperasi.Menu.Simpanan.PencairanSimpanan;
import com.rsah.koperasi.Menu.Simpanan.Simpanan;
import com.rsah.koperasi.Menu.Simpanan.listSimpanan;
import com.rsah.koperasi.Menu.VersionActivity;
import com.rsah.koperasi.Model.Json.JsonProfile;
import com.rsah.koperasi.Model.Json.JsonSaldo;
import com.rsah.koperasi.Model.Json.JsonVersion;
import com.rsah.koperasi.Model.Response.ResponseProfile;
import com.rsah.koperasi.Model.Response.ResponseSaldo;
import com.rsah.koperasi.Model.Response.VersionResponse;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    TextView txtUser , txtTelp , email , idkop , idkary , txtUcapan ;

    ImageView iv_face ;

    ProgressDialog pDialog;

    SessionManager sessionManager ;
    private RelativeLayout rlprogress ;
    CardView cvSetting, cvPeserta , cvBarang , cvSaldo , cvPInjaman , cvKeluar , cvSimpanan , cv_shu ,cvPencairanSimpanan , cv_simulasi;

    private Context mContext;
    private ApiService API;

    public static String saldo ;

    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        rlprogress = findViewById(R.id.rlprogress);
        txtUser = findViewById(R.id.Username);
       // txtEmail = findViewById(R.id.email);
        txtTelp = findViewById(R.id.noTelp);
        email= findViewById(R.id.email);
        idkop= findViewById(R.id.id_koperasi_new);
        idkary= findViewById(R.id.id_karyawan_new);
        txtUcapan= findViewById(R.id.txtUcapan);
        //id_koperasi= findViewById(R.id.id_koperasi);

        cvSetting = findViewById(R.id.cv_setting);
        cvPeserta= findViewById(R.id.cv_Peserta);
        cvBarang= findViewById(R.id.cv_barang);
        cvSaldo= findViewById(R.id.card_saldo);
        cvKeluar= findViewById(R.id.cv_keluar);
        cvSimpanan= findViewById(R.id.cv_simpanan);
        cv_shu= findViewById(R.id.cv_shu);
        cvPInjaman= findViewById(R.id.cv_pinjaman);
        cvPencairanSimpanan= findViewById(R.id.cv_pencairan_simpanan);
        cv_simulasi= findViewById(R.id.cv_simulasi);

        iv_face = findViewById(R.id.iv_face);

        Toolbar toolbar ;
        toolbar = (Toolbar) findViewById(R.id.toolbar_pay);
        setSupportActionBar(toolbar);


       // txtEmail.setText(sessionManager.getKeyEmail());
        txtUser.setText(sessionManager.getUsername());
        txtTelp.setText(sessionManager.getNoTelp());
        email.setText(sessionManager.getKeyEmail());
        idkary.setText(sessionManager.getKeyIdCard());
        idkop.setText(sessionManager.getKeyId());
        //id_koperasi.setText("ID KOP : "+sessionManager.getKeyId());

        String url = this.getString(R.string.baseImageUrl)+sessionManager.getImageUrl() ;

        if (!sessionManager.getImageUrl().equals("")) {
            Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                            int w = resource.getWidth();
                            int h = resource.getHeight();

                            if (w > h) {
                                iv_face.setImageBitmap(resource);
                                iv_face.setRotation(90);
                            } else {
                                iv_face.setImageBitmap(resource);
                            }


                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }




        Helper.cekUcapan(txtUcapan,sessionManager.getUsername() );

        Helper.countDown(MainActivity.this);





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

        cv_simulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, SimulasiPinjaman.class));

            }
        });

        cvPencairanSimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, PencairanSimpanan.class));

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

       cvSimpanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // startActivity(new Intent(MainActivity.this, Simpanan.class));
                startActivity(new Intent(MainActivity.this, listSimpanan.class));

            }
        });


        cvBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, MenuBarang.class));

            }
        });

        cv_shu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new KAlertDialog(MainActivity.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Notification")
                        .setContentText("SHU Dalam Pengembangan")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                            }
                        })
                        .show();

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


        cvKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                show_dialog();

            }
        });
        ScrollView scrollView = (ScrollView) findViewById(R.id.mainscroll);
        refreshLayout = findViewById(R.id.swipe_to_refresh_layout);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                // horizontal scroll position
                int scrollX = scrollView.getScrollX();

                // vertical scroll position
                int scrollY = scrollView.getScrollY();

                if (scrollY < 5 ){
                    refreshLayout.setEnabled(true);
                }else{
                    refreshLayout.setEnabled(false);
                }

               // Toast.makeText(MainActivity.this,""+scrollY,Toast.LENGTH_LONG).show();
            }
        });





        //cekVersion();


        refreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark, android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkProfile();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        checkProfile();


    }


    public void show_dialog(){

        new KAlertDialog(mContext, KAlertDialog.WARNING_TYPE)
                .setTitleText("Warning")
                .setContentText("Anda yakin akan keluar ?")
                .setConfirmText("Keluar")
                .setCancelText("Tetap disini")
                .cancelButtonColor(R.color.green, mContext)
                .confirmButtonColor(R.color.red, mContext)
                .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        //getDataCustomerFromDBSetToTextview();



                        SessionManager sessionManager = new SessionManager(MainActivity.this);
                        sessionManager.logoutUser();
                        Intent intent = new Intent(MainActivity.this, Login.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


                    }
                })
                .show();




    }


    private void checkProfile(){

        JsonProfile json = new JsonProfile();
        json.setMemberID(sessionManager.getKeyId());

        showProgress(true);
        Call<ResponseProfile> call = API.getProfile(json);
        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {
                if(response.isSuccessful()) {
                    if (response.body().getMetadata() != null) {

                        String message = response.body().getMetadata().getMessage() ;
                        String status = response.body().getMetadata().getCode() ;

                        if(status.equals(Constant.ERR_200)) {

                            showProgress(false);

                            // txtEmail.setText(sessionManager.getKeyEmail());
                            txtUser.setText(response.body().getResponse().getData().get(0).getFisrtName());
                            txtTelp.setText(response.body().getResponse().getData().get(0).getMobilePhone());
                            email.setText(response.body().getResponse().getData().get(0).getEmail());
                            idkary.setText(response.body().getResponse().getData().get(0).getNo_IDCard());
                            idkop.setText(response.body().getResponse().getData().get(0).getMemberID());
                            //id_koperasi.setText("ID KOP : "+sessionManager.getKeyId());

                            String imgFace = response.body().getResponse().getData().get(0).getImgFace();

                            Helper.cekUcapan(txtUcapan, response.body().getResponse().getData().get(0).getFisrtName());


                            String url = MainActivity.this.getString(R.string.baseImageUrl) + imgFace;

                            if (!imgFace.equals("")) {


                            Glide.with(MainActivity.this)
                                    .asBitmap()
                                    .load(url)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                            int w = resource.getWidth();
                                            int h = resource.getHeight();

                                            if (w > h) {
                                                iv_face.setImageBitmap(resource);
                                                iv_face.setRotation(90);
                                            } else {
                                                iv_face.setImageBitmap(resource);
                                            }


                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                        }
                                    });
                            }



                        }else{
                            showProgress(false);
                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }else{
                        showProgress(false);
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    showProgress(false);
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {

                showProgress(false);

                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
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



    private void cekVersion() {
        showProgress(true);
        JsonVersion json = new JsonVersion();
        //json.setVersionCode(String.valueOf(BuildConfig.VERSION_CODE));
        json.setAppIdentity("KOPERASI");
        Call<VersionResponse> call = API.versionApp(json);
        call.enqueue(new Callback<VersionResponse>() {
            @Override
            public void onResponse(Call<VersionResponse> call, Response<VersionResponse> response) {
                VersionResponse versionResponse = response.body();
                showProgress(false);
                if (response.isSuccessful()) {

                    String message = response.body().getMetadata().getMessage() ;
                    String status = response.body().getMetadata().getCode() ;

                    if(status.equals(Constant.ERR_200)){
                        String vCode = String.valueOf(BuildConfig.VERSION_CODE) ;
                        if (vCode.equals(versionResponse.getResponse().getData().get(0).getVersion().replace(".0",""))){
                           // Helper.notifVersion("Aplikasi Koperasi", MainActivity.this, "Aplikasi sudah yang terbaru", "0", "", "OK");

                        }else{
                            Helper.notifVersion(versionResponse.getResponse().getData().get(0).getVersion(),MainActivity.this, "Download Update Versi Terbaru ", "1", versionResponse.getResponse().getData().get(0).getLink(), "Download");
                        }

                    }else{
                        Helper.notifikasi_warning(message,MainActivity.this);
                    }


                } else {

                    Helper.notifikasi_warning("Terjadi Kesalahan",MainActivity.this);



                }
            }

            @Override
            public void onFailure(Call<VersionResponse> call, Throwable t) {
                showProgress(true);
//                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.lay_version), "Koneksi Tidak Stabil, Periksa Koneksi Internet Anda", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                startActivity(getIntent());
                            }
                        });
                snackbar.show();
                Log.e("Error", "onFailure: " + t.getMessage());
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        Check_version();
       // Helper.countDown(MainActivity.this);
    }

    public void Check_version() {
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }
}
