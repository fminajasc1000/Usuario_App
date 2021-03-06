package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.UsuarioProyecto.UsuarioEntry;
import model.Usuario;
import model.UsuarioService;
import model.metodosGenerales;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Apis;
/**
 * Clase manejadora de la informacion local y remota
 * @version 1.0, 17/05/2021
 * @author Franciscominajas
 * */
public class UsuarioDb extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuario.db";
    /**
    * Instancia de usuario service
     * */
    UsuarioService usuarioService;
    List<Usuario> listaUsuario = new ArrayList<Usuario>();
    Context contexto;

    /**
     * Metodos de conexion a internet*/
    metodosGenerales metodos;

    /**
     * Clase donde se encuentran almacenados los metodos de conexion a internet
     * */
    /**
     * Como el constructor recibe el contexto de la app, para mmantener la herencia del objet helper
     * */
    public UsuarioDb(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.contexto = context;
    }
    /**
     * Se crea la base de datos en caso de no existir
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UsuarioEntry.TABLE_NAME + " ("
                + UsuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UsuarioEntry.ID + " TEXT NOT NULL,"
                + UsuarioEntry.NOMBRE + " TEXT NOT NULL,"
                + UsuarioEntry.PROJECT + " TEXT,"
                + UsuarioEntry.DESCRIPCION + " TEXT,"
                + UsuarioEntry.DESARROLLADOR1 + " TEXT ,"
                + UsuarioEntry.DESARROLLADOR2 + " TEXT ,"
                + "UNIQUE (" + UsuarioEntry.ID + "))");
        //mockData(db);
        /**
         * Verificacion de la conectividad
         * */
        metodos = new metodosGenerales();
        if((metodos.isConnectedWifi(contexto) && metodos.isOnline(contexto)) ||
                (metodos.isConnectedMobile(contexto) && metodos.isOnline(contexto))){
            listarUsuarios(db);

        }
        UnSegundo();
    }
    /**
     * Un segundo
     * */
    private void UnSegundo(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e)
        { }
    }

    /**
     * Consumo de datos de la API REST*/
    public void listarUsuarios(SQLiteDatabase sqLiteDatabase) {

        usuarioService = Apis.getUsuarioService();
        Call<List<Usuario>>call = usuarioService.getUsuario();

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                //operacion ok
                //obtencion de la info del api-rest e insercion en db
                listaUsuario = response.body();
                for(Usuario u: listaUsuario){
                    mockUsuario(sqLiteDatabase, new Usuario(u.getId(), u.getNombre(), u.getProject(),
                            u.getDescripcion(), u.getDesarrollador1(), u.getDesarrollador2()));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                //operacion no ok
                //Log.e("Error: ",t.getMessage());
                System.out.println("NOOOOOOOO");
            }
        });
    }
    /**
     *
     * Datos ficticios
     * */
    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockUsuario(sqLiteDatabase, new Usuario("Telmex", "Carlos Perez\"",
                "dsfdfdfdfds", "carlos_perez",
                "carlos_perez"));
        mockUsuario(sqLiteDatabase, new Usuario("Lala", "Daniel Samper",
                "sdfdsfdf", "carlos_perez",
                "daniel_samper"));
        mockUsuario(sqLiteDatabase, new Usuario("Alpura", "Lucia Aristizabal",
                "dsfdsfdfdf", "carlos_perez",
                "lucia_aristizabal"));
        mockUsuario(sqLiteDatabase, new Usuario("Comer", "Marina Acosta",
                "sdfdsfdf", "carlos_perez",
                "marina_acosta"));
        mockUsuario(sqLiteDatabase, new Usuario("Telcel", "Olga Ortiz",
                "sdfdsfdf", "carlos_perez",
                "olga_ortiz"));
        mockUsuario(sqLiteDatabase, new Usuario("Telefono", "Pamela Briger",
                "sfdffsfs", "carlos_perez",
                "pamela_briger"));
        mockUsuario(sqLiteDatabase, new Usuario("Logitec", "Rodrigo Benavidez",
                "sdfdsfdfdsf", "carlos_perezs",
                "rodrigo_benavidez"));
        mockUsuario(sqLiteDatabase, new Usuario("Spotify", "Tom Bonz",
                "sdfdsfdsf", "carlos_perez",
                "tom_bonz"));
    }
    /**
     * metodo para ingresar un nuevo registro de usuarios
     * @return long, regresa el id
     * del usuario
     * */
    public long mockUsuario(SQLiteDatabase db, Usuario usuario) {
        return db.insert(
                UsuarioEntry.TABLE_NAME,
                null,
                usuario.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }
    /**
     * Metodo que convertira el objeto Usuario en Content values y lo insertara a la
     * base de datos.
     * @paramobjeto usuario
     * */
    public long saveUsuario(Usuario usuario) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        agregarUsuario(usuario);

        return sqLiteDatabase.insert(
                UsuarioEntry.TABLE_NAME,
                null,
                usuario.toContentValues());

    }
    /**
     * metodo para leer todos los usuarios
     * @return Cursor, regresa un objeto del tipo cursor que contiene la informacion
     * del usuario
     * */
    public Cursor getAllUsuario() {
        /**
        *   Sincronizacion con internet
         *   METODO QUE SINCRONIZA EL LOCAL CON EL SERVER, EN CASO DE FALTA DE INTERNET,
         *   SE HACE EN CADA ACTUALIZACION O MODIFICACION DE ALGUN REGISTRO
        * */
        //Toast.makeText(contexto,"Inicio de sincronizacion",Toast.LENGTH_LONG).show();
        if((metodos.isConnectedWifi(contexto) && metodos.isOnline(contexto)) ||
                (metodos.isConnectedMobile(contexto) && metodos.isOnline(contexto))) {
            System.out.println("INICIO SINCRONIZANDO");
            usuarioService = Apis.getUsuarioService();
            Call<List<Usuario>> call = usuarioService.getUsuario();

            Cursor temporal = getReadableDatabase()
                    .query(
                            UsuarioEntry.TABLE_NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null);

            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    //operacion ok
                    //obtencion de la info del api-rest e insercion en db
                    listaUsuario = response.body();
                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                    //operacion no ok
                    //Log.e("Error: ",t.getMessage());
                    System.out.println("NOOOOOOOO");
                }
            });


            List<Usuario> aa = new ArrayList<Usuario>();//contiene la informaci??n local
            while (temporal.moveToNext()) {
                Usuario aaa = new Usuario(
                        temporal.getString(1),
                        temporal.getString(2),
                        temporal.getString(3),
                        temporal.getString(4),
                        temporal.getString(5),
                        temporal.getString(6)
                );
                //System.out.println(aaa.toString());
                aa.add(aaa);
            }

            List<Usuario> actualizarServer = new ArrayList<Usuario>();
            List<Usuario> agregarServer = new ArrayList<Usuario>();
            List<Usuario> borrarServer = new ArrayList<Usuario>();

            List<Usuario> actualizarLocal = new ArrayList<Usuario>();
            List<Usuario> agregarLocal = new ArrayList<Usuario>();
            List<Usuario> borrarLocal = new ArrayList<Usuario>();


            for (Usuario uu : listaUsuario) {
                for (Usuario uuu : aa) {
                    if (uu.getId().equals(uuu.getId())) {
                        actualizarServer.add(uuu);//actualizar hacia el server
                    } else {
                        borrarServer.add(uuu);
                        //agregarLocal.add((uuu);
                    }
                }
            }

            for (Usuario uu : aa) {
                for (Usuario uuu : listaUsuario) {
                    if (uu.getId().equals(uuu.getId())) {
                        actualizarLocal.add(uuu); //actualizar hacia el local
                    } else {
                        //borrarLocal.add(uuu);
                        agregarServer.add(uuu);
                    }
                }
            }

            //actualizar info del servidor
            for (Usuario proceso : actualizarServer) {
                actualizarServer(proceso, proceso.getId());
            }
            //actualizar info del local
            for (Usuario proceso : actualizarLocal) {
                getWritableDatabase().update(
                        UsuarioEntry.TABLE_NAME,
                        proceso.toContentValues(),
                        UsuarioEntry.ID + " LIKE ?",
                        new String[]{proceso.getId()});
            }
            //borrar info del servidor
            for (Usuario proceso : borrarServer) {
                borrarServer(proceso.getId());
            }
            //agregar info del servidor
            for (Usuario proceso : agregarServer) {
                agregarUsuario(proceso);
            }
            //Toast.makeText(contexto,"Fin sincronizacion",Toast.LENGTH_LONG).show();
            System.out.println("FIN SINCRONIZACION");
        }
        return getReadableDatabase()
                .query(
                        UsuarioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }
    /**
     * metodo para obtenewr un usuario por id
     * @return Cursor, regresa un objeto del tipo cursor que contiene la informacion
     * del usuario
     * */
    public Cursor getUsuarioById(String usuarioId) {
        Cursor c = getReadableDatabase().query(
                UsuarioEntry.TABLE_NAME,
                null,
                UsuarioEntry.ID + " LIKE ?",
                new String[]{usuarioId},
                null,
                null,
                null);
        return c;
    }
    /**
     * metodo para eliminar un usuario
     * @return int, regresa el status de la eliminacion
     * del usuario
     * */
    public int deleteUsuario(String usuarioId) {
        borrarServer(usuarioId);
        return getWritableDatabase().delete(
                UsuarioEntry.TABLE_NAME,
                UsuarioEntry.ID + " LIKE ?",
                new String[]{usuarioId});
    }

    /**
     * metodo para actualizar un usuarios
     * @return int, regresa el status de la eliminaci??n del objeto
     * del usuario
     * */
    public int updateUsuario(Usuario usuario, String usuarioId) {
        actualizarServer(usuario, usuarioId);
        return getWritableDatabase().update(
                UsuarioEntry.TABLE_NAME,
                usuario.toContentValues(),
                UsuarioEntry.ID + " LIKE ?",
                new String[]{usuarioId}
        );
    }

    public void borrarServer(String usuarioId){
        metodos = new metodosGenerales();
        usuarioService = Apis.getUsuarioService();

        if((metodos.isConnectedWifi(contexto) && metodos.isOnline(contexto)) ||
                (metodos.isConnectedMobile(contexto) && metodos.isOnline(contexto))) {

            Call<Usuario> call = usuarioService.deleteUsuario(usuarioId);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(contexto,"Exito registro borrado",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e("Error",t.getMessage());
                }
            });
        }
        UnSegundo();
    }

    public void actualizarServer(Usuario usuario, String id){
        metodos = new metodosGenerales();
        usuarioService = Apis.getUsuarioService();

        if((metodos.isConnectedWifi(contexto) && metodos.isOnline(contexto)) ||
                (metodos.isConnectedMobile(contexto) && metodos.isOnline(contexto))) {

            Call<Usuario> calls = usuarioService.updateUsuario(usuario, id);
            calls.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> calls, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(contexto,"Exito registro actualizado",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> calls, Throwable t) {
                    Log.e("Error",t.getMessage());
                }
            });
        }
        UnSegundo();
    }

    public void agregarUsuario(Usuario usuario){
        metodos = new metodosGenerales();
        usuarioService = Apis.getUsuarioService();

        if((metodos.isConnectedWifi(contexto) && metodos.isOnline(contexto)) ||
                (metodos.isConnectedMobile(contexto) && metodos.isOnline(contexto))) {

            Call<Usuario> call = usuarioService.addUsuario(usuario);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(contexto,"Exito registro exitoso",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.e("Error",t.getMessage());
                }
            });
        }
        UnSegundo();
    }
}
