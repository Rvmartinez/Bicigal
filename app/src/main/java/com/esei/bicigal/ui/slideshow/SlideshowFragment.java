package com.esei.bicigal.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.ViajeModel;
import com.esei.bicigal.ProgramAdapter;
import com.esei.bicigal.R;
import com.esei.bicigal.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    private ListView viajesLv;
    ArrayList<String> fechas;
    ArrayList<String> nombresBicis;
    ArrayList<Integer> imagePosition;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fechas = new ArrayList<>();
        nombresBicis = new ArrayList<>();
        imagePosition = new ArrayList<>();
        viajesLv = root.findViewById(R.id.viajesListView);
        consultDB();
        ProgramAdapter programAdapter = new ProgramAdapter(getContext(),fechas,imagePosition,nombresBicis);
        viajesLv.setAdapter(programAdapter);

        return root;
    }


    private void consultDB() {

        BicigalDB database = BicigalDB.getDB(getActivity().getApplicationContext());

        ArrayList<ViajeModel> viajes = database.getAllViajes();

        for (ViajeModel v:
             viajes) {
            fechas.add(v.getFecha());
            nombresBicis.add(v.getBiciName());
            imagePosition.add(v.getImagePosition());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}