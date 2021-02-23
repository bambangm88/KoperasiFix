package com.rsah.koperasi.Auth.Upload_Foto;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rsah.koperasi.R;

import java.io.IOException;

import static com.rsah.koperasi.Auth.Register_Next_Simpan_New.bitmapUpload;
import static com.rsah.koperasi.Auth.Register_Next_Simpan_New.chooseUpload_set;


public class Foto_Bukti_Transfer extends AppCompatActivity {

    int bitmap_size = 60; // range 1 - 100
    Bitmap bitmap, decoded;
    ProgressDialog pDialog;
    private int GALLERY = 1, CAMERA = 2;

    Button btn_upload ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_bukti);

        btn_upload = findViewById(R.id.btn_upload);

        Toolbar toolbar = findViewById(R.id.toolbar_pay);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();

            }
        });


    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select berkas from gallery",
                "Capture berkas from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }



    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {



                     Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                     bitmapUpload = bitmap ;
                     chooseUpload_set = "4";

                    finish();


                     //fotoIdCard.setImageBitmap(bitmap);


//                    if(chooseUpload.equals("1")){
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                        fotoIdCard.setImageBitmap(bitmap);
//                    }
//
//                    if(chooseUpload.equals("2")){
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                        fotoPribadi.setImageBitmap(bitmap);
//                    }



                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Foto_Bukti_Transfer.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {



                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                bitmapUpload = thumbnail ;
                chooseUpload_set = "4";



            // fotoIdCard.setImageBitmap(thumbnail);
                finish();



            // imageView2Bitmap(berkas1);

            // Toast.makeText(UploadBerkas.this, "encode"+getStringImage(imageView2Bitmap(berkas1)), Toast.LENGTH_SHORT).show();
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
