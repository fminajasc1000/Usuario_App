package model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("actualizar/{id}")
    Call<Usuario> updateUsuario(@Body Usuario usuario, @Path("id") String id);

    @POST("eliminar/{id}")
    Call<Usuario> deleteUsuario(@Path("id") String id);
}
