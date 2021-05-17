package model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsuarioService {

    @GET("listar/")
    Call<List<Usuario>> getUsuario();

}
