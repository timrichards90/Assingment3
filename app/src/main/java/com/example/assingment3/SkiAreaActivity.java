package com.example.assingment3;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkiAreaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FacilitiesStatusAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ski_area_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FacilitiesStatusAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        SkifieldDataScraper skifieldDataScraper = new SkifieldDataScraper();
        skifieldDataScraper.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class SkifieldDataScraper extends AsyncTask<Void, Void, List<Facility>> {

        String skiAreaName = getIntent().getStringExtra("skiAreaName");
        int skiAreaLogo = getIntent().getIntExtra("skiAreaLogo", -1);
        String skiAreaUrl = getIntent().getStringExtra("skiAreaUrl");

        @Override
        protected List<Facility> doInBackground(Void... voids) {
            List<Facility> facilities = new ArrayList<>();
            Document document;

            String currentFacilityName = "";

            try {
                document = Jsoup.connect(skiAreaUrl).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Elements sections = document.select("div.cell.small-12.medium-6.accordion-block__item");

            for (Element section : sections) {
                currentFacilityName = section.select("h5").text();
                Log.d(TAG, "CURRENT FACILITY NAME: " + currentFacilityName);

                Elements accordionItems = section.select(".accordion-item");
                for (Element item : accordionItems) {
                    String facilityName = item.select("h6").text();
                    Log.d(TAG, "FACILITY NAME: " + facilityName);
                    String facilityStatus = item.select("div.state span").text();
                    boolean isOpen = "Open".equals(facilityStatus);

                    Facility facility = new Facility(facilityName, isOpen);
                    facility.setCurrentFacilityName(currentFacilityName);
                    facilities.add(facility);
//                    facilities.add(new Facility(facilityName, isOpen));
                }
            }

            return facilities;
        }

        @Override
        protected void onPostExecute(List<Facility> facilities) {
            super.onPostExecute(facilities);
            adapter.updateData(facilities);
        }
    }
}