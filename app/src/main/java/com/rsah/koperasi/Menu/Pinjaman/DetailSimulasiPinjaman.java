package com.rsah.koperasi.Menu.Pinjaman;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Model.Json.JsonSaldo;
import com.rsah.koperasi.Model.Response.ResponseSaldo;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSimulasiPinjaman extends AppCompatActivity {
    private ApiService API;
    ProgressDialog pDialog;
    private SessionManager sessionManager ;
    private Context mContext;
    private TextView textasuransi,textadministrasi, textjasa,textbesarangsuran , textpencairan ;
    SwipeRefreshLayout refreshLayout;
    private RelativeLayout rlprogress ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulasi_pinjaman_detail);


        sessionManager = new SessionManager(this);
        API = Server.getAPIService();
        mContext = this ;
        pDialog = new ProgressDialog(DetailSimulasiPinjaman.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        rlprogress = findViewById(R.id.rlprogress);
        textadministrasi = findViewById(R.id.textadministrasi);
        textasuransi = findViewById(R.id.textasuransi);
        textjasa = findViewById(R.id.textjasa);
        textbesarangsuran = findViewById(R.id.textbesarangsuran);
        textpencairan = findViewById(R.id.textbesarpencairan);


        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();


        if (extras.getString("asuransi") == null ){
            finish();
            Toast.makeText(DetailSimulasiPinjaman.this,"Terjadi Kesalahan",Toast.LENGTH_LONG).show();
            return;
        }


        if (extras.getString("administrasi") == null ){
            finish();
            Toast.makeText(DetailSimulasiPinjaman.this,"Terjadi Kesalahan",Toast.LENGTH_LONG).show();
            return;
        }


        if (extras.getString("jasa") == null ){
            finish();
            Toast.makeText(DetailSimulasiPinjaman.this,"Terjadi Kesalahan",Toast.LENGTH_LONG).show();
            return;
        }

        if (extras.getString("besarPencairan") == null ){
            finish();
            Toast.makeText(DetailSimulasiPinjaman.this,"Terjadi Kesalahan",Toast.LENGTH_LONG).show();
            return;
        }

        if (extras.getString("besarAngsuran") == null ){
            finish();
            Toast.makeText(DetailSimulasiPinjaman.this,"Terjadi Kesalahan",Toast.LENGTH_LONG).show();
            return;
        }

        String asuransiX = extras.getString("asuransi");
        String administrasiX = extras.getString("administrasi");
        String jasaX = extras.getString("jasa");
        String bpencairan = extras.getString("besarPencairan");
        String bAngsuran = extras.getString("besarAngsuran");



        textasuransi.setText(Helper.changeToRupiah2(asuransiX));
        textadministrasi.setText(Helper.changeToRupiah2(administrasiX));
        textjasa.setText(Helper.changeToRupiah2(jasaX));
        textpencairan.setText(Helper.changeToRupiah2(bpencairan));
        textbesarangsuran.setText(Helper.changeToRupiah2(bAngsuran));




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