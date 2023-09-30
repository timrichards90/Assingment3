package com.example.assingment3;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SkiAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ski_area_activity);

        String skiAreaName = getIntent().getStringExtra("skiAreaName");
        int skiAreaLogo = getIntent().getIntExtra("skiAreaLogo", -1);



    }
}
