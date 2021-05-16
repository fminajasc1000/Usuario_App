package data;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

import data.UsuarioProyecto.UsuarioEntry;

/**
 * Clase que define al usuario
 * @version 1.0, 15/05/2021
 * @author Franciscominajas
 * */
public class Usuario {

    /**
     * Atributos de la base de datos que representan los datos a representar
     *
     * */
    private String id; //identificador del objeto
    private String nombre; //nombre del proyecto
    private String proyectManager; //nombre del proyect manager
    private String descripcion; //descripcion del proyecto
    private String desarrollador1; //nombre del primer desarrollador
    private String desarrollador2; //nombre del segundo desarrollador

    /**
     * Crea un usuario a partir de los aributos basicos
     * @param nombre Nombre del proyecto
     * @param proyectManager nombre del administrador del proyecto
     * @param descripcion Descripcion del proyecto
     * @param desarrollador1 Nombre del desarrollador1 del proyecto
     * @param desarrollador2 Nombre del desarrollador2 del proyecto
     * */
    public Usuario(String nombre, String proyectManager, String descripcion, String desarrollador1, String desarrollador2) {
        this.id = UUID.randomUUID().toString();;
        this.nombre = nombre;
        this.proyectManager = proyectManager;
        this.descripcion = descripcion;
        this.desarrollador1 = desarrollador1;
        this.desarrollador2 = desarrollador2;
    }

    public Usuario(String id, String nombre, String proyectManager, String descripcion, String desarrollador1, String desarrollador2) {
        this.id = id;
        this.nombre = nombre;
        this.proyectManager = proyectManager;
        this.descripcion = descripcion;
        this.desarrollador1 = desarrollador1;
        this.desarrollador2 = desarrollador2;
    }

    /**
     * Esencialmente aqui se fabrica un nuevo usuario del tipo db
     * @param cursor informacion del usuario
     *
     * */
    public Usuario(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(UsuarioEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(UsuarioEntry.NOMBRE));
        proyectManager = cursor.getString(cursor.getColumnIndex(UsuarioEntry.PROYECT_MANAGER));
        descripcion = cursor.getString(cursor.getColumnIndex(UsuarioEntry.DESCRIPCION));
        desarrollador1 = cursor.getString(cursor.getColumnIndex(UsuarioEntry.DESARROLLADOR1));
        desarrollador2 = cursor.getString(cursor.getColumnIndex(UsuarioEntry.DESARROLLADOR2));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(UsuarioEntry.ID, id);
        values.put(UsuarioEntry.NOMBRE, nombre);
        values.put(UsuarioEntry.PROYECT_MANAGER, proyectManager);
        values.put(UsuarioEntry.DESCRIPCION, descripcion);
        values.put(UsuarioEntry.DESARROLLADOR1, desarrollador1);
        values.put(UsuarioEntry.DESARROLLADOR2, desarrollador2);
        return values;
    }
    /**
     * Getters and setters */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProyectManager() {
        return proyectManager;
    }

    public void setProyectManager(String proyectManager) {
        this.proyectManager = proyectManager;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDesarrollador1() {
        return desarrollador1;
    }

    public void setDesarrollador1(String desarrollador1) {
        this.desarrollador1 = desarrollador1;
    }

    public String getDesarrollador2() {
        return desarrollador2;
    }

    public void setDesarrollador2(String desarrollador2) {
        this.desarrollador2 = desarrollador2;
    }

}
