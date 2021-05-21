package com.codepan.twinsrobo_apps.APIs;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
//    private static final String baseURL = "http://192.168.43.59/twinsrobo/";    //ini jaringan hotspot
    private static final String baseURL = "http://10.59.100.164/twinsrobo/";    // ini jaringan wifi lab
//    private static final String baseURL = "http://192.168.0.167/twinsrobo/";    // ini jaringan wifi tempat magang
    private static Retrofit retro;

    public static Retrofit konekRetrofit() {

        if (retro == null) {
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
}
