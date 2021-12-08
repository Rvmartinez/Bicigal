package com.esei.bicigal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BikeShowCurrent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] imageSource;
        ArrayList<String>fechas=savedInstanceState.getStringArrayList("fechas");
        int posicion=savedInstanceState.getInt("posicion");
        String nombreBici=savedInstanceState.getStringArrayList("nombre").get(posicion);
        imageSource = new int[]{R.drawable.ncm_prague,R.drawable.montana_trial,R.drawable.decathlon_basica};
        int imageSelection=imageSource[posicion];
        setContentView(R.layout.activity_bikeshow);
        TextView bike=findViewById(R.id.id_bicicleta);
        bike.setText(nombreBici);

        TextView km=findViewById(R.id.id_km);

        TextView fecha=findViewById(R.id.id_fecha);
        fecha.setText(fechas.get(posicion));

        Button btAtras=this.findViewById(R.id.bt_atras);
      /*  btAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/



    }

}
