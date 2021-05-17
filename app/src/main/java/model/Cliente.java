package model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Clase que define la coneccion con retrofit
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 * */
public class Cliente {

    public static Retrofit getCliente(String url){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }
}
