package model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class metodosGenerales {
    /*Inicio de los metodos de verificacion de Conexion a internet*/
    private static ConnectivityManager manager;
    /*
     * Metodo que nos regresa si la red en la que nos encontramos esta disponible y con conexion a internet*/
    public static boolean isOnline(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
    /*
     * Metodo que nos informa si la red es WIFI*/
    public static boolean isConnectedWifi(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /*
     * Metodo que nos informa si la red a la que estamos conectados es Movil*/
    public static boolean isConnectedMobile(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
