package com.esei.bicigal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.esei.bicigal.Login.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashScreen();
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
    }

    private void splashScreen() {
        Completable.timer(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(SplashScreenActivity.this,"Bienvenido a bicigal",Toast.LENGTH_LONG);
                    }
                });
    }
}