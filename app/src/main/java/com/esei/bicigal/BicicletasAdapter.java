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

public class BicicletasAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<Integer> images;
    ArrayList<String> modelos;
    ArrayList<String> nombresBici;
    int[] imageSource;
    public BicicletasAdapter(@NonNull Context context, ArrayList<String> modelos, ArrayList<Integer> images, ArrayList<String> nombresBici) {
        super(context, R.layout.custom_list_view, R.id.topsideLv, modelos);
        this.context = context;
        this.images = images;
        this.modelos = modelos;
        this.nombresBici = nombresBici;
        imageSource = new int[]{R.drawable.ncm_prague, R.drawable.montana_trial, R.drawable.decathlon_basica};
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

        holder.itemImage.setImageResource(imageSource[images.get(position)]);
        holder.fechaTv.setText(modelos.get(position));
        holder.nombreBiciTv.setText(nombresBici.get(position));

        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), BiciSpecShowCurrent.class);
                BicigalDB db = BicigalDB.getDB(context);
                BicicletaModel bici = db.getBiciById(position);
                Bundle b = new Bundle();
                b.putString("modelo",bici.getModelo());
                b.putString("color",bici.getColor());
                b.putString("material",bici.getMaterial());
                b.putString("name",bici.getName());
                b.putString("tipoCuadro",bici.getTipoCuadro());
                b.putString("pulgadas",bici.getPulgadas());
                b.putString("velocidades",bici.getVelocidades());
                b.putInt("imagePosition",bici.getImagePosition());
                in.putExtras(b);
                context.startActivity(in);

            }
        });

        return singleItem;
    }

}
