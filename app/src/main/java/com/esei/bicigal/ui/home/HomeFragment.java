package com.esei.bicigal.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.ViajeModel;
import com.esei.bicigal.R;
import com.esei.bicigal.SummaryActivity;
import com.esei.bicigal.databinding.FragmentHomeBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private GoogleMap mMap;

    //Location
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallBack;
    private LocationRequest locationRequest;
    private List<Polygon> polygonList;
    private boolean par = true;
    private LatLng startMarker;
    private LatLng endMarker;


    SupportMapFragment mapFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        init();

        return root;
    }

    LatLng currentLocation;

    @SuppressLint("MissingPermission")
    private void init() {
        polygonList = new ArrayList<>();
        currentLocation = new LatLng(0, 0);
        locationRequest = new LocationRequest();
        locationRequest.setSmallestDisplacement(10f);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                LatLng newPosition = new LatLng(locationResult.getLastLocation().getLatitude(),
                        locationResult.getLastLocation().getLongitude());
                currentLocation = newPosition;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 17f));
            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.myLooper());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        mMap.getUiSettings().setMapToolbarEnabled(true);
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mMap.setMyLocationEnabled(true);
                        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                            @Override
                            public boolean onMyLocationButtonClick() {
                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return false;
                                }
                                fusedLocationProviderClient.getLastLocation()
                                        .addOnFailureListener(e -> Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG).show())
                                        .addOnSuccessListener(location -> {
                                            LatLng userLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                                            Snackbar.make(getView(),"lat:"+userLatLng.latitude+"lng:"+userLatLng,Snackbar.LENGTH_LONG).show();
                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,17f));
                                        });
                                return true;
                            }
                        });
                        FloatingActionButton upButton = (FloatingActionButton) getView().findViewById(R.id.floatingButton);
                        upButton.setOnClickListener(v -> {
                            if(par)
                                addPoligonos();
                            else
                                removePoligonos();
                            par = !par;
                        });
                        Chronometer chronometer = (Chronometer) getView().findViewById(R.id.chronometer);
                        FloatingActionButton startRide = (FloatingActionButton) getView().findViewById(R.id.startRide);
                        FloatingActionButton endRide = (FloatingActionButton) getView().findViewById(R.id.endRide);

                        startRide.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                chronometer.setBase(SystemClock.elapsedRealtime());
                                chronometer.start();
                                chronometer.setVisibility(View.VISIBLE);
                                startRide.setVisibility(View.GONE);
                                endRide.setVisibility(View.VISIBLE);
                                startMarker = currentLocation;
                            }
                        });
                        endRide.setOnClickListener(v -> {
                            chronometer.stop();
                            chronometer.setVisibility(View.GONE);
                            endRide.setVisibility(View.GONE);
                            startRide.setVisibility(View.VISIBLE);
                            long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                            long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis);
                            endMarker = currentLocation;
                            BicigalDB database = BicigalDB.getDB(getActivity().getApplicationContext());
                            String fecha = String.valueOf(Calendar.getInstance().getTime());
                            database.addViaje(new ViajeModel(fecha,"XC12C",new Random().nextInt(3)));
                            float[] distancia = new float[1];
                            Location.distanceBetween(startMarker.latitude,startMarker.longitude,endMarker.latitude,endMarker.longitude,distancia);
                            Intent in = new Intent(getContext(), SummaryActivity.class);
                            Bundle b = new Bundle();
                            b.putFloat("distancia",distancia[0]);
                            b.putLong("time",seconds);
                            in.putExtras(b);
                            startActivity(in);
                        });



                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();
        // Add a marker in Sydney and move the camera

    }
    @Override
    public void onDestroyView() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
        binding = null;
        super.onDestroyView();

    }
    private void removePoligonos(){
        for(Polygon polygon:polygonList){
            polygon.remove();
        }
    }
    private void addPoligonos() {
        Polygon amarillo = mMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .fillColor(Color.argb(100,255,255,0))
                .add(

                        new LatLng(42.344462,-7.852867),
                        new LatLng(42.342860, -7.851685),
                        new LatLng(42.343749, -7.849495),
                        new LatLng(42.345192, -7.852068)));

        Polygon verde = mMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .fillColor(Color.argb(100,0,255,0))
                .add(
                        new LatLng(42.345081, -7.856149),
                        new LatLng(42.344002, -7.858637),
                        new LatLng(42.343558, -7.858122),
                        new LatLng(42.343511, -7.857114),
                        new LatLng(42.344066, -7.856020)));

        Polygon rojo = mMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .fillColor(Color.argb(100,255,0,0))
                .add(
                        new LatLng(42.336635,-7.860290),
                        new LatLng(42.336254, -7.860290),
                        new LatLng(42.336032,-7.859797),
                        new LatLng(42.335636,-7.859668),
                        new LatLng(42.335826,-7.857330),
                        new LatLng(42.336175,-7.857074),
                        new LatLng(42.336365, -7.859369)));

        polygonList.add(amarillo);
        polygonList.add(verde);
        polygonList.add(rojo);
    }

    @Override
    public void onClick(View v) {

    }
}