package com.esei.bicigal.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class    BiciSpecShowCurrent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] imageSource;
        imageSource = new int[]{R.drawable.ncm_prague,R.drawable.montana_trial,R.drawable.decathlon_basica};
        setContentView(R.layout.activity_bike_specs);

        Bundle b = getIntent().getExtras();

        if(b != null){
            TextView tv = this.findViewById(R.id.biciColorSpec);
            tv.setText(b.getString("color"));
            tv = this.findViewById(R.id.biciCuadroSpec);
            tv.setText(b.getString("tipoCuadro"));
            tv = this.findViewById(R.id.biciNameSpec);
            tv.setText(b.getString("name"));
            tv = this.findViewById(R.id.biciMaterialSpec);
            tv.setText(b.getString("material"));
            tv = this.findViewById(R.id.biciPulgadasSpec);
            tv.setText(b.getString("pulgadas"));
            tv = this.findViewById(R.id.biciVelocidadesSpec);
            tv.setText(b.getString("velocidades"));
            ImageView imageView = this.findViewById(R.id.biciSpecImage);
            imageView.setImageResource(imageSource[b.getInt("imagePosition")]);
        }

    }
}
