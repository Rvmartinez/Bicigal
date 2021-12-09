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
        String dates=getIntent().getExtras().getString("fechas");
        String bikename=getIntent().getExtras().getString("nombre");

        TextView bike=findViewById(R.id.id_bicicleta);
        bike.setText(bikename);

        TextView fecha=findViewById(R.id.id_fecha);
        fecha.setText(dates);






    }

}
