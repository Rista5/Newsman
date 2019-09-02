package com.newsman.newsman.new_rest;

import android.icu.util.TimeUnit;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.newsman.newsman.auxiliary.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit createInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constant.getBaseUrl())
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder()
                                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()))
                .build();
    }

    public static Retrofit createSimple() {
        return new Retrofit.Builder()
                .baseUrl(Constant.getBaseUrl())
                .build();
    }

//    public static OkHttpClient createOkClient() {
//        return new OkHttpClient()
//                .newBuilder()
//                .connectTimeout(5, TimeUnit.SECOND).
//
//    }
}
