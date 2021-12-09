package com.esei.bicigal.Database;

import static java.sql.Types.BOOLEAN;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.esei.bicigal.BicicletasAdapter;
import com.esei.bicigal.Models.BicicletaModel;
import com.esei.bicigal.Models.UsuarioModel;
import com.esei.bicigal.Models.ViajeModel;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BicigalDB extends SQLiteOpenHelper{
    private static BicigalDB instance;
    //USUARIO
    public static final String USUARIOS_TABLE = "usuarios";
    public static final String VIAJES_TABLE = "viajes";
    public static final String BICICLETA_SPEC_TABLE = "bicicletasSpec";

    public static final String NOMBRE="nombres";
    public static final String EMAIL="emails";
    public static final String LOGIN="logins";
    public static final String PASSWORD="passwords";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    public static BicigalDB getDB(Context c){
        if(instance == null) instance = new BicigalDB(c);
        return instance;
    }

    private BicigalDB(Context context) { super( context, "DATABASE", null, DATABASE_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.beginTransaction();
        try{
            //  TABLA USUARIOS
            db.execSQL( "CREATE TABLE IF NOT EXISTS "+ USUARIOS_TABLE + "("
                    + LOGIN + " text PRIMARY KEY,"
                    + NOMBRE + " text NOT NULL,"
                    + EMAIL + " text NOT NULL,"
                    + PASSWORD + " text NOT NULL"
                    + ")" );

            db.execSQL("CREATE TABLE IF NOT EXISTS "+VIAJES_TABLE+"("
                    + "viajeId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"fecha text NOT NULL,"
                    + "nombreBici text NOT NULL,"
                    + "imagePosition INTEGER NOT NULL"
                    + ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS "+BICICLETA_SPEC_TABLE+"("
                    + "biciId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"modelo text NOT NULL,"
                    + "nombre text NOT NULL,"
                    + "imagePosition INTEGER NOT NULL,"
                    + "material text NOT NULL,"
                    + "pulgadas text NOT NULL,"
                    + "velocidades text NOT NULL,"
                    + "tipoCuadro text NOT NULL,"
                    + "color text"
                    + ")");
            db.setTransactionSuccessful();

        }
        finally {
            db.endTransaction();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.beginTransaction();

        try {
            db.execSQL( "DROP TABLE IF EXISTS "+ USUARIOS_TABLE );
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        this.onCreate( db );
    }
    //OPERACIONES DE LECTURA
    public ArrayList<UsuarioModel> getAllUsers(){
        ArrayList<UsuarioModel> toret = new ArrayList<>();

        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + USUARIOS_TABLE, null);

            if (cursor.moveToFirst()) {
                do {
                    String cor = cursor.getString(0);
                    String name = cursor.getString(1);
                    String log = cursor.getString(2);
                    String pas = cursor.getString(3);
                    String esa=cursor.getString(4);


                    UsuarioModel u = new UsuarioModel(name, cor, log,pas,Integer.valueOf(esa));
                    toret.add(u);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return toret;
    }
    public boolean userNameIsAvailable(String name){
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + USUARIOS_TABLE
                        + " WHERE " + LOGIN + "= ?",
                new String[] {name});
        int c = cursor.getCount();
        cursor.close();
        return c == 0;
    }
    //OPERACIONES DE ESCRITURA
    public void createUser(UsuarioModel u){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO " + USUARIOS_TABLE + "(" + LOGIN + ", " + NOMBRE + ", " + EMAIL + "," + PASSWORD +") VALUES(?,?,?,?)",
                    new String[]{u.getLogin(), u.getNombre(), u.getEmail(), u.getPassword()});
            db.setTransactionSuccessful();
        }catch(Exception e){
            System.out.println("error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + e.getMessage());
        } finally {
            db.endTransaction();
        }

        return;
    }

    public ViajeModel GetViajeById(String id ){
        ViajeModel u = null;
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + VIAJES_TABLE
                        + " WHERE viajeId = "+id,new String[]{id});
        if ( cursor.moveToFirst() ) {
            u = new ViajeModel(cursor.getString( 1 ), cursor.getString(2),Integer.valueOf(cursor.getString(3)));
        }
        cursor.close();

        return u;
    }

    public boolean addViaje(ViajeModel viaje){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO " + VIAJES_TABLE + "(fecha, nombreBici, imagePosition "+") VALUES(?,?,?)",
                    new String[]{ viaje.getFecha(),viaje.getBiciName(),String.valueOf(viaje.getImagePosition())});
            db.setTransactionSuccessful();
        }catch(Exception e){
            return false;
        } finally {
            db.endTransaction();
        }

        return true;
    }

    //METODOS PRIVADOS
    //  MÉTODOS PRIVADOS


    public UsuarioModel grabUser(String log ,String pas){
        UsuarioModel u = null;
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + USUARIOS_TABLE
                        + " WHERE " + LOGIN + log  + PASSWORD+ " ?= ",
                new String[] {pas});
        if ( cursor.moveToFirst() ) {
            u = new UsuarioModel( cursor.getString( 1 ), cursor.getString( 0 ), cursor.getString(2),cursor.getString(3),cursor.getInt(4));
        }
        cursor.close();

        return u;
    }
    public boolean editarUsuario(String login, String nuevoNombre, String nuevoEmail, String nuevaPass)throws SQLException{
        boolean editado = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE, nuevoNombre);
        values.put(EMAIL, nuevoEmail);
        values.put(PASSWORD,nuevaPass);

        try{
            db.beginTransaction();
            db.update(USUARIOS_TABLE, values, LOGIN+"=?", new String[]{login});
            db.setTransactionSuccessful();
            editado = true;
        } finally{
            db.endTransaction();
        }
        return editado;

    }
    //Check if is admin
    public boolean isAdmin(String log,String pas){
        if(checkUser(log,pas)){

            Cursor cursor = this.getReadableDatabase().rawQuery(
                    "SELECT * FROM " + USUARIOS_TABLE
                            + " WHERE " + LOGIN + "=?"  + " AND " + PASSWORD+ "=?" ,
                    new String[]{String.valueOf(log),String.valueOf(pas)});

            cursor.close();

            return true;
        }
        return false;

    }


    //Ckecks user in db
    public boolean checkUser(String log ,String pas){
        boolean toret = false;
        UsuarioModel u = null;
        try{
            Cursor cursor = this.getReadableDatabase().rawQuery(
                    "SELECT * FROM " + USUARIOS_TABLE
                            + " WHERE " + LOGIN + "=?"  + " AND " + PASSWORD+ "=?" ,
                    new String[]{String.valueOf(log),String.valueOf(pas)});

            toret=true;
            cursor.close();

        }catch (Exception e){
            System.out.println("ERROR!!!!!!!!!!!" + e.getMessage());
        }
        return toret;
    }

    public ArrayList<ViajeModel> getAllViajes(){
        ArrayList<ViajeModel> toret = new ArrayList<>();

        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + VIAJES_TABLE, null);

            if (cursor.moveToFirst()) {
                do {
                    String viajeId = cursor.getString(0);
                    String hora = cursor.getString(1);
                    String dia = cursor.getString(2);
                    String imagePosition = cursor.getString(3);

                    ViajeModel viaje = new ViajeModel(Integer.valueOf(viajeId), hora, dia,Integer.valueOf(imagePosition));
                    toret.add(viaje);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return toret;
    }


    public ArrayList<BicicletaModel> getAllBicis() {
        ArrayList<BicicletaModel> toret = new ArrayList<>();

        try {
            Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + BICICLETA_SPEC_TABLE, null);

            if (cursor.moveToFirst()) {
                do {
                    String biciId  = cursor.getString(0);
                    String modelo = cursor.getString(1);
                    String nombre = cursor.getString(2);
                    int imagePosition = Integer.valueOf(cursor.getString(3));
                    String material = cursor.getString(4);
                    String pulgadas = cursor.getString(5);
                    String velocidades = cursor.getString(6);
                    String tipoCuadro = cursor.getString(7);
                    String color = cursor.getString(8);

                    BicicletaModel bici = new BicicletaModel(nombre, material, pulgadas, velocidades,color,tipoCuadro,modelo,imagePosition);
                    toret.add(bici);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return toret;
    }

    public void     addBicicleta(BicicletaModel b){
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        try {
            db.execSQL("INSERT INTO " + BICICLETA_SPEC_TABLE + "(modelo,nombre,imagePosition,material,pulgadas,velocidades,tipoCuadro,color) VALUES(?,?,?,?,?,?,?,?)",
                    new String[]{b.getModelo(), b.getName(), String.valueOf(b.getImagePosition()),b.getMaterial(), b.getPulgadas(),b.getVelocidades(),b.getTipoCuadro(),b.getColor()});
            db.setTransactionSuccessful();
        }catch(Exception e){
            System.out.println("error!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + e.getMessage());
        } finally {
            db.endTransaction();
        }

        return;
    }

    public BicicletaModel getBiciById(int posicion){
        BicicletaModel b = null;
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + BICICLETA_SPEC_TABLE
                        + " WHERE biciId =?",
                new String[] {String.valueOf(posicion)});
        if ( cursor.moveToFirst() ) {
            b = new BicicletaModel( cursor.getString( 2 ), cursor.getString( 4 ), cursor.getString(5),cursor.getString(6),
                    cursor.getString( 8 ),cursor.getString( 7 ),cursor.getString( 1 ),Integer.valueOf(cursor.getString( 3 )));
        }
        cursor.close();

        return b;
    }

    //Recibe login del usuario a editar y los nuevos valores que se quieren sustituir por los
    //anteriores y los modifica en la base de datos, devolviendo true si se modifico correctamente
    public boolean editUser(String login, String nuevoNombre, String nuevoEmail, String nuevaPass){
        boolean editado = false;
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE, nuevoNombre);
        values.put(EMAIL, nuevoEmail);
        values.put(PASSWORD,nuevaPass);

        try{
            db.beginTransaction();
            db.update(USUARIOS_TABLE, values, LOGIN+"=?", new String[]{login});
            db.setTransactionSuccessful();
            editado = true;
        } finally{
            db.endTransaction();
        }
        return editado;

    }

}
