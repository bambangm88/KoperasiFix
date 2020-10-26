package com.rsah.koperasi.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rsah.koperasi.Model.ResponseData;
import com.rsah.koperasi.R;
import com.rsah.koperasi.api.ApiService;
import com.rsah.koperasi.api.Server;
import com.rsah.koperasi.sessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {



    ProgressDialog pDialog;

    EditText pwd_old, pwd_baru, pwd_confirm ;

    private Context mContext;
    private ApiService API;

    Button btn_ubah ;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        API = Server.getAPIService();
        mContext = this ;

        pDialog = new ProgressDialog(ChangePassword.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Memuat...");

        pwd_old = findViewById(R.id.et_pwdOld) ;
        pwd_baru = findViewById(R.id.et_pwdBaru);
        pwd_confirm = findViewById(R.id.et_pwdConfirm);
        btn_ubah = findViewById(R.id.btn_ubah);
        session = new SessionManager(getApplicationContext());

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pwdOld = pwd_old.getText().toString();
                String pwdbaru = pwd_baru.getText().toString();
                String pwdConfirm = pwd_confirm.getText().toString();
                String MID = session.getKeyEmail();

                if (pwdOld.equals("")||pwdbaru.equals("")||pwdConfirm.equals("")){

                    Toast.makeText(mContext,"Silahkan Isi",Toast.LENGTH_SHORT).show();

                }else if (pwdbaru.length() < 8 )   {

                    Toast.makeText(mContext,"Password Minimal 8 Karakter",Toast.LENGTH_SHORT).show();

                } else if (!pwdbaru.equals(pwdConfirm)){

                    Toast.makeText(mContext,"Konfirmasi Passwod Tidak Sama",Toast.LENGTH_SHORT).show();

                } else {

                    UbahPwd(pwdOld,pwdbaru,MID);


                }


            }
        });

    }




    private void UbahPwd(String pwdOld, String pwdbaru , String mID){

        pDialog.show();
        Call<ResponseData> call = API.ubahPwd(mID,pwdOld,pwdbaru);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()) {
                    if (response.body().getSuccess() != null) {

                        if(response.body().getSuccess().equals("0")){

                            pDialog.cancel();
                            Toast.makeText(mContext, "Password Sebelumnya Salah", Toast.LENGTH_LONG).show();

                        }else{
                            pDialog.cancel();

                            Toast.makeText(mContext, "Ubah Password Berhasil", Toast.LENGTH_LONG).show();
                            finish();
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
            public void onFailure(Call<ResponseData> call, Throwable t) {

                pDialog.cancel();

                Toast.makeText(mContext, "Internal server error / check your connection", Toast.LENGTH_SHORT).show();
                Log.e("Error", "onFailure: "+t.getMessage() );
            }
        });
    }




}
