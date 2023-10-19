package com.example.assingment3;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    RelativeLayout splashOverlay;
    ImageView splashLogo;
    String skiResortStatus;
    String skiAreaName;
    int skiAreaLogo;
    String weatherTemp;
    String weatherCondition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ski_area_activity);

        skiAreaName = getIntent().getStringExtra("skiAreaName");
        skiAreaLogo = getIntent().getIntExtra("skiAreaLogo", -1);

        splashOverlay = findViewById(R.id.splashOverlay);
        splashLogo = findViewById(R.id.splashLogo);
        if (skiAreaLogo != -1) {
            splashLogo.setImageResource(skiAreaLogo);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        SkifieldDataScraper skifieldDataScraper = new SkifieldDataScraper();
        skifieldDataScraper.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class SkifieldDataScraper extends AsyncTask<Void, Void, List<Facility>> {

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

            Element weatherElement = document.select("div.small-12.medium-6.info.info--large-img h5.title").first();
            if (weatherElement != null) {
                weatherTemp = weatherElement.ownText().trim();
                String weatherIconURL = weatherElement.select("img").attr("src");

                if (!weatherIconURL.isEmpty()) {
                    weatherCondition = weatherIconURL.substring(weatherIconURL.lastIndexOf('/') + 1, weatherIconURL.lastIndexOf('.'));
                }
            }

            skiResortStatus = Objects.requireNonNull(document.select("div.closed-state h5.title").first()).text().trim();

            Elements sections = document.select("div.cell.small-12.medium-6.accordion-block__item");

            for (Element section : sections) {
                currentFacilityName = section.select("h5").text();

                Elements accordionItems = section.select(".accordion-item");
                for (Element item : accordionItems) {
                    String facilityName = item.select("h6").text();
                    String facilityStatus = item.select("div.state span").text();
                    boolean isOpen = "Open".equals(facilityStatus);

                    Facility facility = new Facility(facilityName, isOpen);
                    facility.setCurrentFacilityName(currentFacilityName);
                    facilities.add(facility);
                }
            }

            return facilities;
        }

        @Override
        protected void onPostExecute(List<Facility> facilities) {
            super.onPostExecute(facilities);
            adapter = new FacilitiesStatusAdapter(facilities, skiAreaName, skiAreaLogo, skiResortStatus, weatherTemp, weatherCondition);
            recyclerView.setAdapter(adapter);
            splashOverlay.setVisibility(View.GONE);
        }

    }
}