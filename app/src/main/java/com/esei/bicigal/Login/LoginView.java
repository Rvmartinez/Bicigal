package com.esei.bicigal.Login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.HomeActivity;
import com.esei.bicigal.R;
import com.esei.bicigal.SplashScreenActivity;

public class LoginView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = (Button) findViewById(R.id.btn_logging);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

    }

    private void onLogin(){
        EditText txt_login = (EditText) findViewById(R.id.txt_login);
        EditText txt_password = (EditText) findViewById(R.id.txt_pass);

        String login = txt_login.getText().toString();
        String password = txt_password.getText().toString();

        BicigalDB  database = BicigalDB.getDB(this);

        boolean exists = database.checkUser(login,password);
        if(exists){
           // Toast.makeText(this," EXISTE USUARIO",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getBaseContext(), HomeActivity.class));

        }
        else Toast.makeText(this,"NO EXISTE USUARIO",Toast.LENGTH_LONG).show();




    }
}
