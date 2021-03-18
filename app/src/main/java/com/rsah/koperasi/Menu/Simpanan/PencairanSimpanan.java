package com.rsah.koperasi.Menu.Simpanan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rsah.koperasi.Auth.Login;
import com.rsah.koperasi.Menu.ChangePassword;
import com.rsah.koperasi.Menu.VersionActivity;
import com.rsah.koperasi.R;
import com.rsah.koperasi.sessionManager.SessionManager;

public class PencairanSimpanan extends AppCompatActivity {


    LinearLayout ubahPwd , keluar  , version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencairan_simpanan);


        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }



    public void show_dialog(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(PencairanSimpanan.this);
        builder1.setMessage("Logout ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SessionManager sessionManager = new SessionManager(PencairanSimpanan.this);
                        sessionManager.logoutUser();
                        Intent intent = new Intent(PencairanSimpanan.this, Login.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
