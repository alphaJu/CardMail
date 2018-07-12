package com.example.q.cardmail;

import android.util.Log;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {
    //retrofit/
    private static Retrofit builder =
            new Retrofit.Builder()
                    .baseUrl("http://52.231.66.41:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
}
