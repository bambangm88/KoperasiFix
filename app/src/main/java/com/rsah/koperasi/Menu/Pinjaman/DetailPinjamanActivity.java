package com.rsah.koperasi.Menu.Pinjaman;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rsah.koperasi.Constant.Constant;
import com.rsah.koperasi.Helper.Helper;
import com.rsah.koperasi.Model.Json.JsonProfile;
import com.rsah.koperasi.Model.Response.ResponsePinjaman;
import com.rsah.koperasi.Model.Response.ResponseProfile;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPinjamanActivity extends AppCompatActivity {


    SessionManager sessionManager ;

    private Context mContext;
    private ApiService API;
    private ImageView profilePict ;
    private RelativeLayout rlprogress ;
    private Button btnRecord ,btnSimulasiCicilan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pinjaman);

        rlprogress = findViewById(R.id.rlprogress);
        mContext = this ;
        API = Server.getAPIService();
        sessionManager = new SessionManager(mContext);

        btnRecord = findViewById(R.id.btnrecord);
        btnSimulasiCicilan= findViewById(R.id.btnSimulasi);

        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle=getIntent().getExtras();
        String ref =bundle.getString("invoice");
        Log.e("TAG", "onCreate: reffff "+ref );
        JsonProfile json = new JsonProfile();
        json.setReference(ref);
        getDetailPinjaman(json);


        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,RecordPinjaman.class);
                i.putExtra("invoice", ref);
                startActivity(i);
            }
        });

        btnSimulasiCicilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,SimulasiCicilan.class);
                i.putExtra("invoice", ref);
                startActivity(i);
            }
        });



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

    private void getDetailPinjaman(JsonProfile json){

        //pDialog.show();
        showProgress(true);
        Call<ResponsePinjaman> call = API.pinjamanDetail(json);
        call.enqueue(new Callback<ResponsePinjaman>() {
            @Override
            public void onResponse(Call<ResponsePinjaman> call, Response<ResponsePinjaman> response) {
                if(response.isSuccessful()) {
                    if (response.body().getMetadata() != null) {

                        String message = response.body().getMetadata().getMessage() ;
                        String status = response.body().getMetadata().getCode() ;

                        if(status.equals(Constant.ERR_200)){

                            showProgress(false);

                            TextView jumlah = findViewById(R.id.tv_nominal);
                            TextView reference = findViewById(R.id.reference);
                            TextView pstatus = findViewById(R.id.tv_status);
                            TextView tanggal = findViewById(R.id.tv_tgl);
                            TextView tenor = findViewById(R.id.tv_tenor);
                            TextView angsuran = findViewById(R.id.tv_angsuran);
                            TextView jatuhtempo = findViewById(R.id.tv_jatuh_tempo);

                            TextView asuransi = findViewById(R.id.tv_asuransi);
                            TextView jasa = findViewById(R.id.tv_jasa);
                            TextView administrasi = findViewById(R.id.tv_administrasi);
                            TextView besarPencairan = findViewById(R.id.tv_besarPencairan);

                            String statusdesc  = response.body().getResponse().getData().get(0).getStatusDesc();
                            String statusX  = response.body().getResponse().getData().get(0).getStatus();

                            jumlah.setText(Helper.changeToRupiah(response.body().getResponse().getData().get(0).getJumlahPinjaman()));
                            reference.setText("#"+response.body().getResponse().getData().get(0).getOrderIdPinjaman());
                            pstatus.setText(response.body().getResponse().getData().get(0).getStatusDesc());
                            tanggal.setText(Helper.parseDate(response.body().getResponse().getData().get(0).getTanggalPinjaman()));
                            jatuhtempo.setText(Helper.parseDate(response.body().getResponse().getData().get(0).getJatuhTempo()));
                            tenor.setText(response.body().getResponse().getData().get(0).getLamaAngsuran()+" Bulan");
                            angsuran.setText(Helper.changeToRupiah(response.body().getResponse().getData().get(0).getBesarAngsuran()));


                            Helper.colostatuspinjaman(statusX,statusdesc,pstatus,mContext);

                            if (statusX.equals("3")){
                                btnSimulasiCicilan.setVisibility(View.VISIBLE);
                            }


                        }else{
                            //pDialog.cancel();
                            showProgress(false);
                            Helper.notifikasi_warning(message,mContext);
                            //Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                            //finish();
                        }

                    }else{
                        //pDialog.cancel();
                        showProgress(false);
                        Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                    }

                }else{
                    //pDialog.cancel();
                    showProgress(false);
                    Toast.makeText(mContext, "Error Response Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePinjaman> call, Throwable t) {

                //pDialog.cancel();
                showProgress(false);
                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
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


}