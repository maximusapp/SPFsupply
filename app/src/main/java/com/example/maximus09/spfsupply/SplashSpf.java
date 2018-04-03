package com.example.maximus09.spfsupply;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashSpf extends AppCompatActivity {

    private final int SPLASH_DALAY = 1200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(intent);
                finish();


            }
        }, SPLASH_DALAY);



    }
}
