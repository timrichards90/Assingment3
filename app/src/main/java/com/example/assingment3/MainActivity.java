package com.example.assingment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView skifieldsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skifieldsRecyclerView = findViewById(R.id.skifieldsRecyclerView);

        List<Skifield> skifieldList = new ArrayList<>();
        skifieldList.add(new Skifield("Broken River Ski Area", R.drawable.br_icon));
        skifieldList.add(new Skifield("Craigieburn Valley Ski Area", R.drawable.cv_icon));
        skifieldList.add(new Skifield("Hanmer Springs Ski Area", R.drawable.hs_icon));
        skifieldList.add(new Skifield("Mt Cheeseman Ski Area", R.drawable.mc_icon));
        skifieldList.add(new Skifield("Mt Olympus Ski Area", R.drawable.mo_icon));
        skifieldList.add(new Skifield("Temple Basin Ski Area", R.drawable.tb_icon));
        skifieldList.add(new Skifield("Rainbow Ski Area", R.drawable.rb_icon));

        SkifieldAdapter skifieldAdapter = new SkifieldAdapter(skifieldList);
        skifieldsRecyclerView.setAdapter(skifieldAdapter);
        skifieldsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}