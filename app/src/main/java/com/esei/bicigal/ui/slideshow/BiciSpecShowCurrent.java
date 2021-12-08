package com.esei.bicigal.ui.slideshow;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.R;

import java.util.ArrayList;

public class BiciSpecShowCurrent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] imageSource;
        imageSource = new int[]{R.drawable.ncm_prague,R.drawable.montana_trial,R.drawable.decathlon_basica};
        setContentView(R.layout.activity_bicispec_showcurrent);
    }
}
