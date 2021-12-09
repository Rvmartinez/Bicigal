package com.esei.bicigal.Database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.esei.bicigal.BicicletasAdapter;
import com.esei.bicigal.Models.BicicletaModel;
import com.esei.bicigal.Models.UsuarioModel;
import com.esei.bicigal.Models.ViajeModel;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

    public static BicigalDB getDB(Context c){
        if(instance == null) instance = new BicigalDB(c);
        return instance;
    }

    private BicigalDB(Context context) { super( context, "DATABASE", null, DATABASE_VERSION );}

    @Override
    public void onCreate(SQLiteDatabase db) {
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

                    UsuarioModel u = new UsuarioModel(name, cor, log,pas);
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
            db.execSQL("INSERT INTO " + USUARIOS_TABLE + "(" + NOMBRE + ", " + EMAIL +"," + LOGIN + "," + PASSWORD + ") VALUES(?,?,?,?)",
                    new String[]{u.getNombre(), u.getEmail(), u.getLogin(), u.getPassword()});
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
            u = new ViajeModel(cursor.getString( 1 ), cursor.getString(2),Integer.valueOf(cursor.getString(3)) );
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

    private UsuarioModel grabUser(String log ,String pas){
        UsuarioModel u = null;
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "SELECT * FROM " + USUARIOS_TABLE
                        + " WHERE " + LOGIN + log  + PASSWORD+ " ?= ",
                new String[] {pas});
        if ( cursor.moveToFirst() ) {
            u = new UsuarioModel( cursor.getString( 1 ), cursor.getString( 0 ), cursor.getString(2),cursor.getString(3) );
        }
        cursor.close();

        return u;
    }

    //Ckecks user in db
    public UsuarioModel checkUser(String log ,String pas){
        UsuarioModel u = null;
        try{
            Cursor cursor = this.getReadableDatabase().rawQuery(
                    "SELECT * FROM " + USUARIOS_TABLE
                            + " WHERE " + LOGIN + "=?"  + " AND " + PASSWORD+ "=?" ,
                    new String[]{String.valueOf(log),String.valueOf(pas)});
            if ( cursor.moveToFirst() ) {
                u = new UsuarioModel( cursor.getString( 1 ), cursor.getString( 0 ), cursor.getString(2),cursor.getString(3) );
            }
            cursor.close();

        }catch (Exception e){
            System.out.println("ERROR!!!!!!!!!!!" + e.getMessage());
        }
        return u;
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

    public boolean deleteBiciById(int position) {
        SQLiteDatabase db = this.getWritableDatabase();
        BicicletaModel b = null;
        try{
            db.beginTransaction();
            db.execSQL(
                    "DELETE FROM "
                            + BICICLETA_SPEC_TABLE
                            + " WHERE "
                            + "biciId=?"
                    , new String[]{String.valueOf(position)});
            db.setTransactionSuccessful();
        }catch(SQLException exception){
            return false;
        }finally {
            db.endTransaction();
        }
        return true;
    }
}
