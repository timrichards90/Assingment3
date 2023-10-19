package com.example.assingment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView skifieldsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skifieldsRecyclerView = findViewById(R.id.skifieldsRecyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.baseline_help_24); // Set the drawable to the FAB

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snackbarText = "Tap a ski resort for its status, weather, and facility details.";
                Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        List<Skifield> skifieldList = new ArrayList<>();
        skifieldList.add(new Skifield("Broken River", R.drawable.br_icon, "https://www.snow.nz/area/nz/canterbury/brokenriver/"));
        skifieldList.add(new Skifield("Craigieburn Valley", R.drawable.cv_icon, "https://www.snow.nz/area/nz/canterbury/craigieburn/"));
        skifieldList.add(new Skifield("Fox Peak", R.drawable.fp_icon, "https://www.snow.nz/area/nz/mackenzie/foxpeak/"));
        skifieldList.add(new Skifield("Hanmer Springs", R.drawable.hs_icon, "https://www.snow.nz/area/nz/canterbury/hanmer-springs/"));
        skifieldList.add(new Skifield("Mt Cheeseman", R.drawable.mc_icon, "https://www.snow.nz/area/nz/canterbury/mt-cheeseman/"));
        skifieldList.add(new Skifield("Mt Olympus", R.drawable.mo_icon, "https://www.snow.nz/area/nz/canterbury/mt-olympus/"));
        skifieldList.add(new Skifield("Rainbow Valley", R.drawable.rb_icon, "https://www.snow.nz/area/nz/canterbury/rainbow/"));
        skifieldList.add(new Skifield("Stratford Mountain Club", R.drawable.smc_icon, "https://www.snow.nz/area/nz/ruapehu/manganui/"));
        skifieldList.add(new Skifield("Temple Basin", R.drawable.tb_icon, "https://www.snow.nz/area/nz/canterbury/temple-basin/"));

        SkifieldAdapter skifieldAdapter = new SkifieldAdapter(skifieldList);
        skifieldsRecyclerView.setAdapter(skifieldAdapter);
        skifieldsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkiAreaActivity.clearFacilitiesCache();
    }
}