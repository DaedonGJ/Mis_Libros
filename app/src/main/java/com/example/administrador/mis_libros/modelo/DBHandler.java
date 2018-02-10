package com.example.administrador.mis_libros.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by alumno2 on 01/02/2018.
 */

public class DBHandler {
    //nombre y la versíon de la BD
    private static final String DB_NAME = "Mi_BD";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "libros";

    //Nombres de las columnas de las tabla
    private static final String KEY_ROWID = "_id";
    private static final String KEY_TITULO = "titulo";
    private static final String KEY_AUTOR = "autor";
    private static final String KEY_RESUMEN = "resumen";
    private static final String KEY_GENERO = "genero";

    //sentencia de creacion de la tabla
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + KEY_ROWID + " integer primary key autoincrement, "
            + KEY_TITULO + " text, " + KEY_AUTOR + " text, " + KEY_RESUMEN + " text, " +
            KEY_GENERO + " text);";

    //ATRIBUTOS DE INSTACIA
    private final Context ctx;
    //mas,,
    private SQLiteDatabase db;
    private DBHelper dbh;

    public DBHandler(Context ctx) {
        this.ctx = ctx;
    }

    public DBHandler abrir() {
        //instanciar un Handler con el contesto
        //asignar varlo a los atributos
        dbh = new DBHelper(ctx);
        db = dbh.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbh.close();

    }

    //métodos de gestion de base de datos
    public long insert(Libros l) {
        //otro bundle
        //el long es la
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITULO, l.titulo);
        cv.put(KEY_AUTOR, l.autor);
        cv.put(KEY_RESUMEN, l.resumen);
        cv.put(KEY_GENERO, l.getGenero().ordinal());
        Log.i("Libros", "insertando");
        return db.insert(TABLE_NAME, null, cv);

    }
    public  boolean delete(long rowId){
        return db.delete(TABLE_NAME,KEY_ROWID+"= "+rowId,null)>0;
    }
    public  boolean update(long rowId, Libros l){
        String[] seleccion={rowId+""};
        ContentValues campos = new ContentValues();
        campos.put(KEY_TITULO, l.titulo);
        campos.put(KEY_AUTOR, l.autor);
        campos.put(KEY_RESUMEN, l.resumen);
        campos.put(KEY_GENERO, l.getGenero().ordinal());

        return db.update(TABLE_NAME,campos,KEY_ROWID+" =? ",seleccion)==1;
    }
    public Cursor list() {
        //las proyectiones son las  columnas que se quienere ver
        String[] proyeccion = {
                KEY_ROWID, KEY_TITULO, KEY_AUTOR, KEY_RESUMEN, KEY_GENERO
        };

        Cursor c = db.query(TABLE_NAME, proyeccion, null, null, null, null, null);
        if (c != null) c.moveToFirst();
        return c;
    }//LISTADO

    public Cursor getItem(long rowID) {
        String[] proyeccion = {
                KEY_ROWID, KEY_TITULO, KEY_AUTOR, KEY_RESUMEN, KEY_GENERO
        };
        Cursor c = db.query(TABLE_NAME, proyeccion, KEY_ROWID + "= " + rowID, null, null, null, null);
        if (c != null) c.moveToFirst();
        return c;
    }
    /*
    Escribimos una clase interna DBHelper que aude a  gestionar las versiones de la BD,
     */

    public static class DBHelper extends SQLiteOpenHelper {


        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            Log.i("Libros", CREATE_TABLE);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
            Log.i("Libros", DB_NAME + " version: " + DB_VERSION);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }//cbhelper


}//clas

