package com.codepan.twinsrobo_apps.APIs;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
    private static final String baseURL = "https://twinsroboserver.onedhonyreff.repl.co/twinsrobo/";    //ini hosting dari Replit.co
//    private static final String baseURL = "http://192.168.43.59/twinsrobo/";    //ini jaringan hotspot
    //    private static final String baseURL = "http://10.59.103.110/twinsrobo/";    // ini jaringan wifi lab
//    private static final String baseURL = "http://192.168.0.167/twinsrobo/";    // ini jaringan wifi tempat magang
    private static Retrofit retro;

    public static Retrofit konekRetrofit() {

        if (retro == null) {

//            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                    .connectTimeout(1200, TimeUnit.SECONDS)
//                    .readTimeout(1200, TimeUnit.SECONDS)
//                    .writeTimeout(1200, TimeUnit.SECONDS)
//                    .build();

            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
//                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
}
