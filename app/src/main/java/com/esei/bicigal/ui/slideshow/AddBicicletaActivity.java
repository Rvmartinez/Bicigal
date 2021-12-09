package com.esei.bicigal.ui.slideshow;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.BicicletaModel;
import com.esei.bicigal.R;

import java.util.Random;

public class AddBicicletaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbicicleta);



        Button b = this.findViewById(R.id.addBiciButton);
        b.setOnClickListener(view -> {
            EditText ed = this.findViewById(R.id.addColorEntry);
            String color = ed.getText().toString();
            ed = this.findViewById(R.id.addMaterialEntry);
            String material = ed.getText().toString();
            ed = this.findViewById(R.id.addModeloEntry);
            String modelo = ed.getText().toString();
            ed = this.findViewById(R.id.addPulgadasEntry);
            String pulgadas = ed.getText().toString();
            ed = this.findViewById(R.id.addVelocidadesEntry);
            String velocidades = ed.getText().toString();

            ed = this.findViewById(R.id.addNombreEntry);
            String nombre = ed.getText().toString();

            ed = this.findViewById(R.id.addTipoEntry);
            String tipoCuadro = ed.getText().toString();

            BicigalDB db = BicigalDB.getDB(getBaseContext());
            if(TextUtils.isEmpty(tipoCuadro)  && TextUtils.isEmpty(nombre) && TextUtils.isEmpty(velocidades) && TextUtils.isEmpty(pulgadas) && TextUtils.isEmpty(modelo) && TextUtils.isEmpty(color) && TextUtils.isEmpty(material)){
                db.addBicicleta(new BicicletaModel(nombre,material,pulgadas,velocidades,color,tipoCuadro,modelo,new Random().nextInt(3)));
                finish();
            }
            else Toast.makeText(getBaseContext(),"hellothere",Toast.LENGTH_LONG).show();

        });

    }
    
}
