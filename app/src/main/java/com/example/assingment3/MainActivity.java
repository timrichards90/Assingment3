package com.example.assingment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //RecyclerView displays list of ski ares
    RecyclerView skiAreaRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skiAreaRecyclerView = findViewById(R.id.skiAreaRecyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        // set floating action button icon
        fab.setImageResource(R.drawable.baseline_help_24);

        //floating action bar displays instructions in a toast message when clicked
        fab.setOnClickListener(view -> {
            String snackbarText = "Tap on a ski area to view its report.";
            Snackbar.make(view, snackbarText, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        // create list of SkiArea objects
        List<SkiArea> skiAreaList = new ArrayList<>();
        skiAreaList.add(new SkiArea("Broken River", R.drawable.br_icon, "https://www.snow.nz/area/nz/canterbury/brokenriver/"));
        skiAreaList.add(new SkiArea("Craigieburn Valley", R.drawable.cv_icon, "https://www.snow.nz/area/nz/canterbury/craigieburn/"));
        skiAreaList.add(new SkiArea("Fox Peak", R.drawable.fp_icon, "https://www.snow.nz/area/nz/mackenzie/foxpeak/"));
        skiAreaList.add(new SkiArea("Hanmer Springs", R.drawable.hs_icon, "https://www.snow.nz/area/nz/canterbury/hanmer-springs/"));
        skiAreaList.add(new SkiArea("Mt Cheeseman", R.drawable.mc_icon, "https://www.snow.nz/area/nz/canterbury/mt-cheeseman/"));
        skiAreaList.add(new SkiArea("Mt Olympus", R.drawable.mo_icon, "https://www.snow.nz/area/nz/canterbury/mt-olympus/"));
        skiAreaList.add(new SkiArea("Rainbow Valley", R.drawable.rb_icon, "https://www.snow.nz/area/nz/canterbury/rainbow/"));
        skiAreaList.add(new SkiArea("Stratford Mountain Club", R.drawable.smc_icon, "https://www.snow.nz/area/nz/ruapehu/manganui/"));
        skiAreaList.add(new SkiArea("Temple Basin", R.drawable.tb_icon, "https://www.snow.nz/area/nz/canterbury/temple-basin/"));

        //add SkiArea list to the RecyclerView
        SkiAreaAdapter skiAreaAdapter = new SkiAreaAdapter(skiAreaList);
        skiAreaRecyclerView.setAdapter(skiAreaAdapter);
        skiAreaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // clear cached items on destroy
        SkiAreaActivity.clearFacilitiesCache();
    }
}