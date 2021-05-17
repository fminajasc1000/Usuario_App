package model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Metodos para conocer el status de la conexion a internet
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 */
public class metodosGenerales {
    /**
     * Inicio de los metodos de verificacion de Conexion a internet
     * */
    private static ConnectivityManager manager;
    /**
     * Metodo que nos regresa si la red en que nos encontramos esta disponible y con conexion a internet
     * @param context contexto de la app
     * @return boolean respuesta si o no*/
    public static boolean isOnline(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
    /**
     * Metodo que nos informa si la red es WIFI
     * @param context contexto de la aplicacion
     * @return boolean respuesta si o no*/
    public static boolean isConnectedWifi(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Método que nos informa si la rd a la que estamos conectados es Movil
     * @return boolean respuesta si o no*/
    public static boolean isConnectedMobile(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }
}
