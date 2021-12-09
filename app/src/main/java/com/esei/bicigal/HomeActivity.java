package com.esei.bicigal;

import android.app.Application;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.esei.bicigal.Database.BicigalDB;
import com.esei.bicigal.Models.BicicletaModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.esei.bicigal.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView tv = headerView.findViewById(R.id.nav_header_email);
        Bundle b = getIntent().getExtras();

        if(b != null){
            tv.setText(b.getString("email"));
            tv = headerView.findViewById(R.id.nav_header_name);
            tv.setText(b.getString("nombre"));
            ImageView img = headerView.findViewById(R.id.nav_header_image);
            img.setImageResource(R.drawable.avatar);

        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        BicigalDB db = BicigalDB.getDB(getBaseContext());
        db.addBicicleta(new BicicletaModel("Decathlon","Fibra de carbono","17","5","rojo","Flexible","xc12c",1));
        db.addBicicleta(new BicicletaModel("Honeywell","Fibra de carbono","17","5","rojo","Flexible","xc12c",1));
        db.addBicicleta(new BicicletaModel("Mountainbike","Fibra de carbono","17","5","rojo","Flexible","xc12c",1));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}