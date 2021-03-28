package com.rsah.koperasi.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.rsah.koperasi.BuildConfig;
import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.Constant.VersionChecker;
import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Model.Json.JsonVersion;
import com.rsah.koperasi.Model.Response.VersionResponse;
import com.rsah.koperasi.Model.Version;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionActivity extends AppCompatActivity {

    SessionManager session;
    RelativeLayout rlProgress;
    ApiService API;
    TextView btnCekVersi, developer, namaApp;

    SwipeRefreshLayout refresh;

    private void findElement(){
        API = Server.getAPIService();
        session = new SessionManager(getApplicationContext());
        rlProgress = findViewById(R.id.rlprogress);
        refresh = findViewById(R.id.refresh);

        btnCekVersi = findViewById(R.id.btnCekVersi);
        developer = findViewById(R.id.developer);
        namaApp = findViewById(R.id.namaApp);

        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(VersionActivity.this, Pengaturan.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        findElement();
        setListener();
        getInfoAplikasi();
    }

    private void getInfoAplikasi() {
        JsonVersion json = new JsonVersion();
        json.setAppIdentity("KOPERASI");
        Call<VersionResponse> call = API.versionApp(json);
        call.enqueue(new Callback<VersionResponse>() {
            @Override
            public void onResponse(Call<VersionResponse> call, Response<VersionResponse> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMetadata().getMessage() ;
                    String status = response.body().getMetadata().getCode() ;

                    if(status.equals(Constant.ERR_200)){

                        VersionResponse versionResponse = response.body();
                        namaApp.setText(versionResponse.getResponse().getData().get(0).getNama());
                        developer.setText(versionResponse.getResponse().getData().get(0).getAuthor());

                        //cekVersion();
                        String versionName = BuildConfig.VERSION_NAME;
                        TextView v = findViewById(R.id.tvversion);
                        v.setText("Version "+versionName);


                    }else{
                        Helper.notifikasi_warning(message,VersionActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<VersionResponse> call, Throwable t) {
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

    private void setListener() {

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfoAplikasi();
                refresh.setRefreshing(false);
            }
        });

        btnCekVersi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check_version();
            }
        });
    }

    public void Check_version() {
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
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
                            Helper.notifVersion("Aplikasi Koperasi",VersionActivity.this, "Aplikasi sudah yang terbaru", "0", "", "OK");

                        }else{
                            Helper.notifVersion(versionResponse.getResponse().getData().get(0).getVersion(),VersionActivity.this, "Download Update Versi Terbaru ", "1", versionResponse.getResponse().getData().get(0).getLink(), "Download");
                        }

                    }else{
                        Helper.notifikasi_warning(message,VersionActivity.this);
                    }


                } else {

                    Helper.notifikasi_warning("Terjadi Kesalahan",VersionActivity.this);



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

    public void showProgress(boolean bool) {
        if (bool) {
            rlProgress.setVisibility(View.VISIBLE);
        } else {
            rlProgress.setVisibility(View.GONE);
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