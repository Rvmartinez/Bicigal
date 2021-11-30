package com.esei.bicigal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Bundle b = getIntent().getExtras();
        float distancia=0;
        long time=0;
        if(b != null){
            distancia = b.getFloat("distancia");
            time = b.getLong("time");
        }

        TextView distanciaTV = this.findViewById(R.id.distanceTextView);
        distanciaTV.setText(distancia+"km");
        TextView timeTV = this.findViewById(R.id.timeTextView);
        timeTV.setText(time+"s");
    }
}