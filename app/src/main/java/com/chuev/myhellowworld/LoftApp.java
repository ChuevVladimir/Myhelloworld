package com.chuev.myhellowworld;

import android.app.Application;

import com.chuev.myhellowworld.remote.AuthAPI;
import com.chuev.myhellowworld.remote.MoneyAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftApp extends Application {
    public MoneyAPI moneyAPI;
    public AuthAPI authAPI;
    public static String AUTH_KEY="12314124gsdgdssh12141523563246";
    @Override
    public void onCreate() {
        super.onCreate();
        configureretrofit();
    }

    private void configureretrofit()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient =new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://loftschool.com/android-api/basic/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        moneyAPI = retrofit.create(MoneyAPI.class);
        authAPI = retrofit.create(AuthAPI.class);
    }
}
