package com.rsah.koperasi.api;





import com.rsah.koperasi.Model.Json.JsonInsertBarang;
import com.rsah.koperasi.Model.Json.JsonKeranjang;
import com.rsah.koperasi.Model.Json.JsonLogin;
import com.rsah.koperasi.Model.Json.JsonPesanan;
import com.rsah.koperasi.Model.Json.JsonProfile;
import com.rsah.koperasi.Model.Json.JsonRegister;
import com.rsah.koperasi.Model.Json.JsonRegistrasiEmpID;
import com.rsah.koperasi.Model.Json.JsonSaldo;
import com.rsah.koperasi.Model.Json.JsonSimpananSukarela;
import com.rsah.koperasi.Model.Json.JsonSimulasiPinjaman;
import com.rsah.koperasi.Model.Json.JsonUbahPwd;
import com.rsah.koperasi.Model.Json.JsonVersion;
import com.rsah.koperasi.Model.Response.ResponseAngsuran;
import com.rsah.koperasi.Model.Response.ResponseBarang;
import com.rsah.koperasi.Model.Response.ResponseCaraPembayaran;
import com.rsah.koperasi.Model.Response.ResponseCompany;
import com.rsah.koperasi.Model.Response.ResponseInsertBarang;
import com.rsah.koperasi.Model.Response.ResponseJangkaWaktu;
import com.rsah.koperasi.Model.Response.ResponseJenisPinjaman;
import com.rsah.koperasi.Model.Response.ResponseKeranjang;
import com.rsah.koperasi.Model.Response.ResponseLogin;
import com.rsah.koperasi.Model.Response.ResponseNominalPinjaman;
import com.rsah.koperasi.Model.Response.ResponsePesanan;
import com.rsah.koperasi.Model.Response.ResponsePinjaman;
import com.rsah.koperasi.Model.Response.ResponseProfile;
import com.rsah.koperasi.Model.Response.ResponseRecordPinjaman;
import com.rsah.koperasi.Model.Response.ResponseRegister;
import com.rsah.koperasi.Model.Response.ResponseRegistrasiEmpID;
import com.rsah.koperasi.Model.Response.ResponseSaldo;
import com.rsah.koperasi.Model.Response.ResponseSimpanan;
import com.rsah.koperasi.Model.Response.ResponseSimulasiPinjaman;
import com.rsah.koperasi.Model.Response.ResponseUbahPwd;
import com.rsah.koperasi.Model.Response.VersionResponse;
import com.rsah.koperasi.Model.ResponseData;
import com.rsah.koperasi.Model.ResponseDataKabupaten;
import com.rsah.koperasi.Model.ResponseDataKecamatan;
import com.rsah.koperasi.Model.ResponseDataKelurahan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiService {



    @Headers("Content-Type: application/json")
    @POST("koperasi/listCompany")
    Call<ResponseCompany> fetchCompany();


    @GET("koperasi/listTenorAngsuran")
    Call<ResponseAngsuran> fetchAngsuran();

    @Headers("Content-Type: application/json")
    @POST("koperasi/listJenisPinjaman")
    Call<ResponseJenisPinjaman> jenisPinjaman();


    @Headers("Content-Type: application/json")
    @POST("koperasi/listNominalPinjaman")
    Call<ResponseNominalPinjaman> nominalPinjaman();

    @Headers("Content-Type: application/json")
    @POST("koperasi/listTenorPinjaman")
    Call<ResponseJangkaWaktu> jangkaWaktuPinjaman();


    @Headers("Content-Type: application/json")
    @POST("koperasi/Register")
    Call<ResponseRegister> Register(@Body JsonRegister body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/EditProfile")
    Call<ResponseRegister> EditProfile(@Body JsonRegister body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/login")
    Call<ResponseLogin> Login(@Body JsonLogin body);


    @Headers("Content-Type: application/json")
    @POST("koperasi/ForgotPassword")
    Call<ResponseLogin> checkEmail(@Body JsonRegister body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/UbahPwd")
    Call<ResponseUbahPwd> ubahPwd(@Body JsonUbahPwd body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/simulasiPinjaman")
    Call<ResponseSimulasiPinjaman> simulasiPinjaman(@Body JsonSimulasiPinjaman body);

    @GET("poe")
    Call<ResponseData> Token();

    @GET()
    Call<ResponseData> Wilayah(@Url String url);

    @GET()
    Call<ResponseDataKabupaten> Kabupaten(@Url String url);

    @GET()
    Call<ResponseDataKecamatan> Kecamatan(@Url String url);


    @GET()
    Call<ResponseDataKelurahan> Kelurahan(@Url String url);


    @GET("koperasi/caraPembayaran")
    Call<ResponseCaraPembayaran> cara_pembayaran();


    @Headers("Content-Type: application/json")
    @POST("koperasi/saldo")
    Call<ResponseSaldo> getSaldo(@Body JsonSaldo body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/lastestSimpanan")
    Call<ResponseSaldo> getLastestSimpanan(@Body JsonSaldo body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/Barang")
    Call<ResponseBarang> getBarang();


    @Headers("Content-Type: application/json")
    @POST("koperasi/peserta")
    Call<ResponseProfile> getProfile(@Body JsonProfile body);


    @Headers("Content-Type: application/json")
    @POST("koperasi/pinjamanByReference")
    Call<ResponsePinjaman> getPinjamanByRef(@Body JsonProfile body);


    @Headers("Content-Type: application/json")
    @POST("koperasi/pinjamanByReference")
    Call<ResponsePinjaman> pinjamanDetail(@Body JsonProfile body);


    @Headers("Content-Type: application/json")
    @POST("koperasi/InsertBarangToKeranjang")
    Call<ResponseInsertBarang> requestInsertKrj(@Body JsonInsertBarang body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/ListKeranjang")
    Call<ResponseKeranjang> getKeranjang(@Body JsonKeranjang body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/listPesanan")
    Call<ResponsePesanan> getPesanan(@Body JsonPesanan body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/listSimpananSukarela")
    Call<ResponseSimpanan> getSimpananSukarela(@Body JsonSimpananSukarela body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/listPinjaman")
    Call<ResponsePinjaman> getPinjaman(@Body JsonSimpananSukarela body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/listRecordPinjaman")
    Call<ResponseRecordPinjaman> getRecordPinjaman(@Body JsonProfile body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/simulasiCicilan")
    Call<ResponseRecordPinjaman> getSimulasiCicilan(@Body JsonProfile body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/addSimpananSukarela")
    Call<ResponseRegister> addSimpananSukarela(@Body JsonSimpananSukarela body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/CekRegistrasiByEmpID")
    Call<ResponseRegistrasiEmpID> cekRegistrasiByEmpID(@Body JsonRegistrasiEmpID body);

    @Headers("Content-Type: application/json")
    @POST("koperasi/cekVersion")
    Call<VersionResponse> versionApp(@Body JsonVersion body);



}
