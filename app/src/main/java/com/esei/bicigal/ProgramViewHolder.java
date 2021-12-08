package com.esei.bicigal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder {
    ImageView itemImage;
    TextView fechaTv;
    TextView nombreBiciTv;

    ProgramViewHolder(View v){
        itemImage = v.findViewById(R.id.customImage);
        fechaTv = v.findViewById(R.id.topsideLv);
        nombreBiciTv = v.findViewById(R.id.bottomsideLv);
    }
}
