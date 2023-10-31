package com.example.bookingappteam17.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // try to hide getSupportActionBar
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        int SPLASH_TIME_OUT = 5000; // time to launch the another activity
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        findViewById(R.id.appName).setVisibility(android.view.View.VISIBLE);
        findViewById(R.id.appName).startAnimation(animFadeIn);
        new Timer().schedule((new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }), SPLASH_TIME_OUT);
    }
}
