package utils;

import model.Cliente;
import model.UsuarioService;

public class Apis {
    public static final String URL_001="http://192.168.1.64:8080/usuario/";

    public static UsuarioService getUsuarioService(){
        //System.out.println("------>: "+Cliente.getCliente(URL_001).create(UsuarioService.class));
        return Cliente.getCliente(URL_001).create(UsuarioService.class);
    }
}
