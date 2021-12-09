package com.esei.bicigal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.UsuarioModel;

import java.sql.SQLException;

public class UserEditActivity extends AppCompatActivity {
@Override
    protected  void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_edit_activity);
    EditText name=this.findViewById(R.id.id_name);
    EditText email=this.findViewById(R.id.id_email);
    EditText pass=this.findViewById(R.id.id_password);
    EditText antLog=this.findViewById(R.id.id_AntiguoLog);
    EditText antPas=this.findViewById(R.id.id_AntiguaPass);
    Button bt_Edit=this.findViewById(R.id.id_edit);
    Button bt_Cancel=this.findViewById(R.id.id_cancel);

    bt_Edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            BicigalDB dbHelper = BicigalDB.getDB(getBaseContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String nameAux=name.getText().toString();
            String emailAux=email.getText().toString();
            String passAux=pass.getText().toString();
            String antLogAux= antLog.getText().toString();
            String antPasAux= antPas.getText().toString();
            boolean estado=!nameAux.isEmpty()&&!emailAux.isEmpty()&&!passAux.isEmpty()&&!antLogAux.isEmpty()&&!antPasAux.isEmpty();

            if(dbHelper.checkUser(antLogAux,antPasAux)&& estado){
               UsuarioModel u=new UsuarioModel(nameAux,emailAux,antLogAux,passAux);
              UsuarioModel usu =dbHelper.grabUser(antLogAux,antPasAux);
              usu.setEmail(emailAux);
              usu.setNombre(nameAux);
              usu.setPassword(passAux);

                try {
                    dbHelper.editarUsuario(antLogAux,nameAux,emailAux,passAux);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }else{
                Toast.makeText(getBaseContext(),"Por favor, introduzca datos correctos",Toast.LENGTH_LONG).show();
            }

            // String passAux= pass.getText().toString();
            //db.execSQL();



        }
    });
}
}
