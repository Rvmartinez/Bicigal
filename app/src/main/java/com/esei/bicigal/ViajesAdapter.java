package com.esei.bicigal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.BicicletaModel;
import com.esei.bicigal.ui.slideshow.BiciSpecShowCurrent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ViajesAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<Integer> images;
    ArrayList<String> fechas;
    ArrayList<String> nombresBici;
    int[] imageSource;
    public ViajesAdapter(@NonNull Context context, ArrayList<String> fechas, ArrayList<Integer> images, ArrayList<String> nombresBici) {
        super(context, R.layout.custom_list_view,R.id.topsideLv,fechas);
        this.context = context;
        this.images = images;
        this.fechas = fechas;
        this.nombresBici = nombresBici;
        imageSource = new int[]{R.drawable.ncm_prague,R.drawable.montana_trial,R.drawable.decathlon_basica};
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View singleItem = convertView;
        ProgramViewHolder holder = null;

        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.custom_list_view,parent,false);

            holder = new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);
        }
        else{
            holder = (ProgramViewHolder) singleItem.getTag();
        }

        holder.itemImage.setImageResource(imageSource[new Random().nextInt(3)]);
        holder.fechaTv.setText(fechas.get(position));
        holder.nombreBiciTv.setText(nombresBici.get(position));

        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBike=new Intent(context,BikeShowCurrent.class);
                Bundle b=new Bundle();
                b.putString("fechas",fechas.get(position));
                b.putInt("posicion",position);
                b.putString("nombre",nombresBici.get(position));
                intentBike.putExtras(b);

                //context.startActivity(intentBike,b);
                context.startActivity(intentBike);
                // Toast.makeText(context, "aqui va la vista principal de "+fechas.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        return singleItem;
    }
}
