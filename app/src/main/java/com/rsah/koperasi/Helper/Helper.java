package com.rsah.koperasi.Helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.rsah.koperasi.Auth.Login;
import com.rsah.koperasi.Auth.Register_Next_Simpan_New;
import com.rsah.koperasi.R;
import com.rsah.koperasi.sessionManager.SessionManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Helper {


    public static boolean checkNullInputLogin(String username , String Password){

        Boolean check = true ;

        if(username.equals("") || Password.equals("")){

            check = false ;

        }

        return check ;


    }


    public static String chekJenisKelamin(String JenisKelamin){

        String jk = "" ;

        if (JenisKelamin.equals("Perempuan")){
            jk = "P";
        }else{
            jk = "L";
        }

        return jk ;

    }


    public static String convertStatus(String code){

        String status = "" ;

        if (code.equals("01")){
            status = "Terdaftar";
        }else if (code.equals("00")){
            status = "Selesai";
        }else{
            status = "Expired";
        }

        return status ;

    }


    public static String convertHariToDaySession(String day){

        String daySession = "" ;

        if (day.contains("Minggu")){
            daySession = "1";
        }

        if (day.contains("Senin")){
            daySession = "2";
        }

        if (day.contains("Selasa")){
            daySession = "3";
        }

        if (day.contains("Rabu")){
            daySession = "4";
        }

        if (day.contains("Kamis")){
            daySession = "5";
        }

        if (day.contains("Jumat")){
            daySession = "6";
        }

        if (day.contains("Sabtu")){
            daySession = "7";
        }


        return daySession ;

    }



    public static String changeToRupiah(String text)
    {
        String rp_ = text.replace(".0000","");
        String rp = rp_.replace(".00","");
        if (rp.equals("")){
            rp = "0" ;
        }
        //conversi currency
        int number = Integer.parseInt(rp);
        String currency = NumberFormat.getNumberInstance(Locale.US).format(number);
        return "Rp "+currency;
    }


    public static String changeToRupiah2(String text)
    {
        String rp = text.replace(".0000","");
        //conversi currency
        int number = Integer.parseInt(rp);
        String currency = NumberFormat.getNumberInstance(Locale.US).format(number);
        return "Rp "+currency;
    }

    String a = "".toString();


    public static String Hash_SHA256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }



    public static void notifikasi_warning(String message , Context context){
        new KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                .setTitleText("Notification")
                .setContentText(message)
                .setConfirmText("OK")
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }


    public static void showDateDialog(Context mContext , final TextView text , String Format){

        DatePickerDialog datePickerDialog;

        final SimpleDateFormat dateFormatter = new SimpleDateFormat(Format, Locale.US);

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(mContext , new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                text.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap imageView2Bitmap(ImageView view){
        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
        return bitmap;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    public static void notifVersion(String versi, Activity context, String title, String select, String link, String button) {
        new KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                .setTitleText(versi)
                .setContentText(title)
                .setConfirmText(button)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if(select.equals("1")){
                            Toast.makeText(context, "Download", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(link));
                            context.startActivity(i);
                        }
                    }
                })
                .show();
    }

    public static void cekUcapan(TextView textView , String name) {
        Calendar calendar = Calendar.getInstance();
        Locale id = new Locale("in", "ID");
        SimpleDateFormat waktu = new SimpleDateFormat("HH", id);
        int jam = Integer.parseInt(waktu.format(calendar.getTime()));
        String ucapan = "Selamat Datang";
        if (jam < 4 || jam > 18) {
            ucapan = "Selamat Malam";
        } else if (jam >= 4 && jam < 10) {
            ucapan = "Selamat Pagi";
        } else if (jam >= 10 && jam < 14) {
            ucapan = "Selamat Siang";
        } else if (jam >= 14 && jam <= 18) {
            ucapan = "Selamat Sore";
        }
        textView.setText(ucapan+", "+name);
    }


    public static void countDown(Context mContext) {
        new CountDownTimer(150000, 150000) {
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                try {
                    new KAlertDialog(mContext, KAlertDialog.WARNING_TYPE)
                            .setTitleText("Session Anda Berakhir")
                            .setContentText("Anda tidak melakukan aktivitas apapun !")
                            .setConfirmText("Keluar")
                            .setCancelText("Tetap disini")
                            .cancelButtonColor(R.color.green, mContext)
                            .confirmButtonColor(R.color.red_btn_bg_color, mContext)
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
                                    SessionManager sessionManager = new SessionManager(mContext) ;
                                    sessionManager.logoutUser();
                                    Intent i = new Intent(mContext, Login.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                            Intent.FLAG_ACTIVITY_NEW_TASK);
                                    ((Activity) mContext).startActivity(i);
                                }
                            })
                            .show();

                } catch (Exception ex) {

                }
            }
        }.start();
    }




    public static String parseDate(String inputDateString) {
        String dateTime ="" ;
        try {

            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

            String inputText = inputDateString;
            Date date = inputFormat.parse(inputText);
            dateTime = outputFormat.format(date);
            return dateTime ;

        }catch (Exception ex){

        }


        return dateTime ;
    }
    
    
    public static void colostatuspinjaman(String status, String statusDesc, TextView status_, Context mContext){
        if (status.equals("1")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.yellow));
        } else if (status.equals("2")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.yellow));
        }else if (status.equals("3")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.yellow));
        }else if (status.equals("4")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.green));
        }else if (status.equals("5")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.red));
        }else if (status.equals("6")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.green));
        }else if (status.equals("7")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.green));
        }else if (status.equals("8")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.green));
        }else if (status.equals("9")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        else if (status.equals("10")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        else if (status.equals("11")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        else if (status.equals("12")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        else if (status.equals("13")){
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.red));
        }else{
            status_.setText(statusDesc);
            status_.setTextColor(mContext.getResources().getColor(R.color.red));
        }

    }



}
