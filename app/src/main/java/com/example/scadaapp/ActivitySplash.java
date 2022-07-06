package com.example.scadaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class ActivitySplash extends AppCompatActivity {

    ProgressBar splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash=findViewById(R.id.splashProgress);
        ObjectAnimator.ofInt(splash,"progress",100).setDuration(5000).start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ActivitySplash.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);


    }
}