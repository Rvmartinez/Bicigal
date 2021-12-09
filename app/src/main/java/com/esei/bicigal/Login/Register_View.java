package com.esei.bicigal.Login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.UsuarioModel;
import com.esei.bicigal.R;

public class Register_View extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //this.u = (Usuario) info.getExtras().get("user");
        Button aceptar=this.findViewById(R.id.btRegistrarse);
        Button cancelar=this.findViewById(R.id.bt_Cancelar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Register_View.this.rellenarDatos();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private boolean verify(){
       return true;
    }
    public void rellenarDatos(){

        EditText lyt_login = (EditText) findViewById(R.id.id_Login);
        String login = lyt_login.getText().toString();

        EditText lyt_email = (EditText)  findViewById(R.id.id_email);
        String email = lyt_email.getText().toString();

        EditText lyt_nombre = (EditText)  findViewById(R.id.id_nombre);
        String nombre = lyt_nombre.getText().toString();

        EditText lyt_password = (EditText)  findViewById(R.id.id_Password);
        String password = lyt_password.getText().toString();

        UsuarioModel u = new UsuarioModel(nombre,email,login,password,0);

        if(verify()){
            BicigalDB db = BicigalDB.getDB(Register_View.this.getApplicationContext());
            //Crear usuario
            //if(db.userNameIsAvailable(u.getNombre()))
            db.createUser(u);

            AlertDialog.Builder builder = new AlertDialog.Builder(Register_View.this);
            builder.setTitle("Usuario registrado");
            builder.setMessage("Se ha realizado el registro con éxito");
            builder.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Register_View.this.finish();
                }
            });
            builder.create().show();


        } else {
            //showAlert();
        }

    }



}
