package com.esei.bicigal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class ProgramAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<Integer> images;
    ArrayList<String> fechas;
    ArrayList<String> nombresBici;
    int[] imageSource;
    public ProgramAdapter(@NonNull Context context, ArrayList<String> fechas, ArrayList<Integer> images, ArrayList<String> nombresBici) {
        super(context, R.layout.custom_list_view,R.id.fechaViajeTV,fechas);
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

        holder.itemImage.setImageResource(R.drawable.decathlon_basica);
        holder.fechaTv.setText(fechas.get(position));
        holder.nombreBiciTv.setText(nombresBici.get(position));

        singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "aqui va la vista principal de "+fechas.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        return singleItem;
    }
}
