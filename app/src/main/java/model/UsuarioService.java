package model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Clase donnde se encuentran los metodos de comunicacion
 * @version 1.0, 16/05/2021
 * @author Franciscominajas
 * */
public interface UsuarioService {

    @GET("listar")
    Call<List<Usuario>> getUsuario();

    @POST("agregar")
    Call<Usuario> addUsuario(@Body Usuario usuario);
}
