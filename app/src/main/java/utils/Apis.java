package utils;

import model.Cliente;
import model.UsuarioService;
/**
 * Clase que contiene la conf del servicio
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 * */
public class Apis {
    public static final String URL_001="http://192.168.1.5:8080/usuario/";

    public static UsuarioService getUsuarioService(){
        return Cliente.getCliente(URL_001).create(UsuarioService.class);
    }
}
