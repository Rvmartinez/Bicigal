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
        setContentView(R.layout.activity_bikeshow);
        // int[] imageSource;
        //int[] bikeImages=getIntent().getExtras().putIntArray("fotoBici");
        //String dates=savedInstanceState.getString("fechas");
        String dates=getIntent().getExtras().getString("fechas");
        //int pos=savedInstanceState.getInt("posicion");
        String bikename=getIntent().getExtras().getString("nombre");
        //imageSource = new int[]{R.drawable.ncm_prague,R.drawable.montana_trial,R.drawable.decathlon_basica};
        //int imageSelection=imageSource[posicion];

        TextView bike=findViewById(R.id.id_bicicleta);
        bike.setText(bikename);

        // TextView km=findViewById(R.id.id_km);

        TextView fecha=findViewById(R.id.id_fecha);
        fecha.setText(dates);

        // Button btAtras=this.findViewById(R.id.bt_atras);




    }

}
