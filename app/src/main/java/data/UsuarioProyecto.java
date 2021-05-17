package data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para usuarios
 * Defincion de las tablas de manera global
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 */
public class UsuarioProyecto {
    public static abstract class UsuarioEntry implements BaseColumns {
        public static final String TABLE_NAME ="usuario";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String PROJECT = "project";
        public static final String DESCRIPCION = "descripcion";
        public static final String DESARROLLADOR1 = "desarrollador1";
        public static final String DESARROLLADOR2 = "desarrollador2";
    }
}
