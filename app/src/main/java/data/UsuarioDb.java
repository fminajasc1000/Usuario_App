package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import data.UsuarioProyecto.UsuarioEntry;

public class UsuarioDb extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuario.db";

    /**
     * Clase donde se encuentran almacenados los metodos de conexion a internet
     * */
    /**
     * Como el constructor recibe el contexto de la app, para mmantener la herencia del objet helper
     * */
    public UsuarioDb(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //this.contexto = context;
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
                + UsuarioEntry.PROYECT_MANAGER + " TEXT NOT NULL,"
                + UsuarioEntry.DESCRIPCION + " TEXT NOT NULL,"
                + UsuarioEntry.DESARROLLADOR1 + " TEXT NOT NULL,"
                + UsuarioEntry.DESARROLLADOR2 + " TEXT,"
                + "UNIQUE (" + UsuarioEntry.ID + "))");
        mockData(db);
    }
    /**
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
    public int deleteUsuario(String lawyerId) {
        return getWritableDatabase().delete(
                UsuarioEntry.TABLE_NAME,
                UsuarioEntry.ID + " LIKE ?",
                new String[]{lawyerId});
    }

    /**
     * metodo para actualizar un usuarios
     * @return int, regresa el status de la eliminación del objeto
     * del usuario
     * */
    public int updateUsuario(Usuario usuario, String usuarioId) {
        return getWritableDatabase().update(
                UsuarioEntry.TABLE_NAME,
                usuario.toContentValues(),
                UsuarioEntry.ID + " LIKE ?",
                new String[]{usuarioId}
        );
    }
}
