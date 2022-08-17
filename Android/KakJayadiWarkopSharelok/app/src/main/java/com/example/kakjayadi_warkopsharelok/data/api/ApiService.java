package com.example.kakjayadi_warkopsharelok.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static String URL = "http://xn--tlffc9dlij.id"; //alamat website
    private static String BASE_URL = URL+"/warkop_sharelock/api/";
    public static Retrofit retrofit;

    public static ApiConfig getApiConfig(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiConfig.class);
    }
}
