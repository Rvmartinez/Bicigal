package com.esei.bicigal.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.esei.bicigal.BicicletasAdapter;
import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.BicicletaModel;
import com.esei.bicigal.Models.ViajeModel;
import com.esei.bicigal.R;
import com.esei.bicigal.ViajesAdapter;
import com.esei.bicigal.databinding.FragmentGalleryBinding;
import com.esei.bicigal.databinding.FragmentSlideshowBinding;
import com.esei.bicigal.ui.slideshow.AddBicicletaActivity;
import com.esei.bicigal.ui.slideshow.SlideshowViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;


    private ListView bicicletasLv;
    ArrayList<String> nombresBicis;
    ArrayList<String> modeloBicis;
    ArrayList<Integer> imagePosition;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nombresBicis = new ArrayList<>();
        imagePosition = new ArrayList<>();
        modeloBicis = new ArrayList<>();
        bicicletasLv = root.findViewById(R.id.bicicletasListView);


        BicicletasAdapter bicicletasAdapter = new BicicletasAdapter(getContext(),modeloBicis,imagePosition,nombresBicis);
        bicicletasLv.setAdapter(bicicletasAdapter);
        FloatingActionButton fab = root.findViewById(R.id.addBiciFAB);
        fab.setOnClickListener(view -> {
            Intent in = new Intent(getContext(), AddBicicletaActivity.class);
            startActivity(in);
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        consultDB();
    }

    private void consultDB() {

        BicigalDB database = BicigalDB.getDB(getActivity().getApplicationContext());

        ArrayList<BicicletaModel> bicis = database.getAllBicis();

        for (BicicletaModel v:
                bicis) {
            modeloBicis.add(v.getName());
            nombresBicis.add(v.getModelo());
            imagePosition.add(v.getImagePosition());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        consultDB();
        super.onResume();
    }

}

