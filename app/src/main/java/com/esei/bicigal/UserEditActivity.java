package com.esei.bicigal;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.UsuarioModel;

public class UserEditActivity extends AppCompatActivity {
@Override
    protected  void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.user_edit_activity);

    EditText name=this.findViewById(R.id.id_name);
    EditText email=this.findViewById(R.id.id_email);
    EditText pass=this.findViewById(R.id.id_password);

    Button bt_Edit=this.findViewById(R.id.id_edit);
    Button bt_Cancel=this.findViewById(R.id.id_cancel);

    bt_Edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           AlertDialog.Builder builder=new AlertDialog.Builder(getBaseContext());
            builder.
            builder.setTitle("Validacion");
            builder.setMessage("Introduzca datos");
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            //   BicigalDB dbHelper = new BicigalDB(this);
           // SQLiteDatabase db = dbHelper.getWritableDatabase();
            String nameAux=name.getText().toString();
            String emailAux=email.getText().toString();

            // String passAux= pass.getText().toString();
            //db.execSQL();



        }
    });
}
}
