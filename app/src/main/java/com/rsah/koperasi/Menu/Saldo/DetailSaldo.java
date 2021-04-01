package com.rsah.koperasi.Menu.Saldo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.MainActivity;
import com.rsah.koperasi.Menu.Barang.Adapter.LastestSimpananAdapter;
import com.rsah.koperasi.Menu.Barang.Adapter.RecordPinjamanAdapter;
import com.rsah.koperasi.Model.Data.DataRecordPinjaman;
import com.rsah.koperasi.Model.Data.DataSaldo;
import com.rsah.koperasi.Model.Json.JsonProfile;
import com.rsah.koperasi.Model.Json.JsonSaldo;
import com.rsah.koperasi.Model.Response.ResponseRecordPinjaman;
import com.rsah.koperasi.Model.Response.ResponseSaldo;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSaldo extends AppCompatActivity {
    private ApiService API;
    ProgressDialog pDialog;
    private SessionManager sessionManager ;
    private Context mContext;
    private TextView textname,textcompanycode, textidkop,textsaldo , textsaldo_sukarela , textsaldo_wajib ;
    SwipeRefreshLayout refreshLayout;
    private RelativeLayout rlprogress ;


    private RecyclerView rvSimpanan;
    private List<DataSaldo> AllSimpananList = new ArrayList<>();
    private LastestSimpananAdapter Adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_saldo);


        rvSimpanan = findViewById(R.id.rvLastestSimpanan);
        sessionManager = new SessionManager(this);
        API = Server.getAPIService();
        mContext = this ;
        pDialog = new ProgressDialog(DetailSaldo.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvSimpanan.setLayoutManager(mLayoutManager);
        rvSimpanan.setItemAnimator(new DefaultItemAnimator());

        Adapter = new LastestSimpananAdapter(this, AllSimpananList);

        rlprogress = findViewById(R.id.rlprogress);
        textname = findViewById(R.id.textname);
        textcompanycode = findViewById(R.id.textcompanycode);
        textidkop = findViewById(R.id.textidkop);
        textsaldo= findViewById(R.id.textsaldo);
        textsaldo_sukarela= findViewById(R.id.textsaldo_sukarela);
        textsaldo_wajib= findViewById(R.id.textsaldo_wajib);


        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        textname.setText(sessionManager.getUsername());
        textcompanycode.setText(sessionManager.getKeyIdCompany());
        textidkop.setText(sessionManager.getKeyId());

        checkSaldo();


        JsonSaldo json = new JsonSaldo();
        json.setMemberID(sessionManager.getKeyId());
        getSimpanan(json);


        refreshLayout = findViewById(R.id.swipe_to_refresh_layout);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark, android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkSaldo();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
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


    }



    private void checkSaldo(){

        JsonSaldo json = new JsonSaldo();
        json.setMemberID(sessionManager.getKeyId());
       // pDialog.show();
        showProgress(true);
        Call<ResponseSaldo> call = API.getSaldo(json);
        call.enqueue(new Callback<ResponseSaldo>() {
            @Override
            public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                if(response.isSuccessful()) {
                    if (response.body().getMetadata() != null) {


                        String message = response.body().getMetadata().getMessage() ;
                        String status = response.body().getMetadata().getCode() ;

                        if(status.equals(Constant.ERR_200)){


                            showProgress(false);
                            String sal = response.body().getResponse().getData().get(0).getSaldo();
                            String sal_sukarela = response.body().getResponse().getData().get(0).getSaldo_sukarela();
                            String sal_wajib = response.body().getResponse().getData().get(0).getSaldo_wajib();
                            if (sal == null || sal.equals("null")){
                               // saldo = "Rp 0";
                                textsaldo.setText("Rp 0");
                                textsaldo_sukarela.setText("Rp 0");
                                textsaldo_wajib.setText("Rp 0");
                                Toast.makeText(mContext, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
                            }else {

                                textsaldo.setText(sal);
                                textsaldo_sukarela.setText(sal_sukarela);
                                textsaldo_wajib.setText(sal_wajib);
                            }
                            //saldo.setText("Rp "+response.body().getDataSaldo().get(0).getSaldo());

                        }else{
                            //pDialog.cancel();
                            showProgress(false);
                            //Toast.makeText(mContext, "Email / Password salah", Toast.LENGTH_LONG).show();

                            textsaldo.setText("Rp 0");
                            textsaldo_sukarela.setText("Rp 0");
                            textsaldo_wajib.setText("Rp 0");
                        }

                    }else{
                       // pDialog.cancel();
                        showProgress(false);
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                        textsaldo.setText("Rp 0");
                        textsaldo_sukarela.setText("Rp 0");
                        textsaldo_wajib.setText("Rp 0");
                    }

                }else{
                   /// pDialog.cancel();
                    showProgress(false);
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    textsaldo.setText("Rp 0");
                    textsaldo_sukarela.setText("Rp 0");
                    textsaldo_wajib.setText("Rp 0");
                }
            }

            @Override
            public void onFailure(Call<ResponseSaldo> call, Throwable t) {

               // pDialog.cancel();
                showProgress(false);
                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }




    private void getSimpanan(JsonSaldo json){

        //pDialog.show();
        showProgress(true);
        Call<ResponseSaldo> call = API.getLastestSimpanan(json);
        call.enqueue(new Callback<ResponseSaldo>() {
            @Override
            public void onResponse(Call<ResponseSaldo> call, Response<ResponseSaldo> response) {
                if(response.isSuccessful()) {
                    showProgress(false);
                    if (response.body().getMetadata() != null) {

                        String message = response.body().getMetadata().getMessage();
                        String status = response.body().getMetadata().getCode();

                        if (status.equals(Constant.ERR_200)) {

                            //pDialog.cancel();
                            AllSimpananList.addAll(response.body().getResponse().getData());
                            rvSimpanan.setAdapter(new LastestSimpananAdapter(mContext, AllSimpananList));
                            Adapter.notifyDataSetChanged();

                        }else{
                            //pDialog.cancel();
                            //icon_simpanan_sukarela.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                        }

                    }else {
                        // pDialog.cancel();
                        showProgress(false);
                        finish();
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    // pDialog.cancel();
                    showProgress(false);
                    finish();
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSaldo> call, Throwable t) {

                // pDialog.cancel();
                showProgress(false);
                finish();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+"Terjadi Gangguan Pada Server" );
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