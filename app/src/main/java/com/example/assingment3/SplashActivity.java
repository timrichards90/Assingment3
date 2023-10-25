package com.example.assingment3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

// TODO: overlay text on photo
// TODO: When transitioning from the splash screen to the main activity, consider adding a smooth transition or animation to enhance the user experience.
// display a splash screen when opening unopened app, it doesn't serve any purpose other than aesthetics
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000); // display for 2 seconds
    }
}