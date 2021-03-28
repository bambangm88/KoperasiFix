package com.rsah.koperasi.Menu.Pinjaman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.rsah.koperasi.MainActivity;
import com.rsah.koperasi.Menu.Pengaturan;
import com.rsah.koperasi.R;

public class MainPinjaman extends AppCompatActivity {

    LinearLayout list_pinjaman , simulasi ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pinjaman);

        list_pinjaman = findViewById(R.id.list_pinjaman);
        simulasi = findViewById(R.id.simulasi_pinjaman);

        list_pinjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPinjaman.this, TrackPinjaman.class));
            }
        });

        simulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPinjaman.this, SimulasiPinjaman.class));
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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