package model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cliente {

    public static Retrofit getCliente(String url){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        System.out.println("---->: "+retrofit.toString());
        System.out.println("---->: "+retrofit.baseUrl());
        return retrofit;
    }
}
