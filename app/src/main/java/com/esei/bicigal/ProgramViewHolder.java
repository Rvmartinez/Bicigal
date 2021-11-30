package com.esei.bicigal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {
    ImageView itemImage;
    TextView fechaTv;
    TextView nombreBiciTv;

    ProgramViewHolder(View v){
        itemImage = v.findViewById(R.id.viajeImage);
        fechaTv = v.findViewById(R.id.fechaViajeTV);
        nombreBiciTv = v.findViewById(R.id.nombreBiciTV);
    }
}
