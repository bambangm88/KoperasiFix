package com.rsah.koperasi.api;


import static com.rsah.koperasi.util.Utility.BASE_URL_API;

public class Server {
    public static ApiService getAPIService() {

        return Client.getClient(BASE_URL_API).create(ApiService.class);
    }

    public static ApiService getAPIServiceRestApi() {

        return Client.getClientRestApi("https://x.rajaapi.com/").create(ApiService.class);
    }

}
