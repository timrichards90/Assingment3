package com.example.assingment3;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    RelativeLayout splashOverlay;
    ImageView splashLogo;
    String skiResortStatus;
    String skiAreaName;
    int skiAreaLogo;
    String weatherTemp;
    String weatherCondition;
    String skiAreaUrl;
    private static final int CACHE_SIZE = 50; // Adjust the size based on your needs.
    private static LruCache<String, CacheEntry> facilitiesCache = new LruCache<>(CACHE_SIZE);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ski_area_activity);

        skiAreaName = getIntent().getStringExtra("skiAreaName");
        skiAreaLogo = getIntent().getIntExtra("skiAreaLogo", -1);
        skiAreaUrl = getIntent().getStringExtra("skiAreaUrl");

        splashOverlay = findViewById(R.id.splashOverlay);
        splashLogo = findViewById(R.id.splashLogo);
        if (skiAreaLogo != -1) {
            splashLogo.setImageResource(skiAreaLogo);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CacheEntry cachedEntry = facilitiesCache.get(skiAreaUrl);

        if (cachedEntry != null) {
            long timeDiff = System.currentTimeMillis() - cachedEntry.timestamp;
            String timeString = getTimeString(timeDiff);
            skiResortStatus = cachedEntry.skiResortStatus;
            weatherTemp = cachedEntry.weatherTemp;
            weatherCondition = cachedEntry.weatherCondition;

            adapter = new FacilitiesStatusAdapter(cachedEntry.facilities, skiAreaName, skiAreaLogo, skiResortStatus, weatherTemp, weatherCondition, timeString);
            recyclerView.setAdapter(adapter);
            splashOverlay.setVisibility(View.GONE);
        } else {
            SkifieldDataScraper skifieldDataScraper = new SkifieldDataScraper();
            skifieldDataScraper.execute();
        }
    }

    private String getTimeString(long timeDiff) {
        long diffInSeconds = timeDiff / 1000;
        if (diffInSeconds < 60) {
            return diffInSeconds + " seconds ago";
        }
        long diffInMinutes = diffInSeconds / 60;
        if (diffInMinutes < 60) {
            return diffInMinutes + " minutes ago";
        }
        long diffInHours = diffInMinutes / 60;
        if (diffInHours < 24) {
            return diffInHours + " hours ago";
        }
        long diffInDays = diffInHours / 24;
        return diffInDays + " days ago";
    }
    @SuppressLint("StaticFieldLeak")
    private class SkifieldDataScraper extends AsyncTask<Void, Void, List<Facility>> {

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

            long currentTime = System.currentTimeMillis();
            facilitiesCache.put(skiAreaUrl, new CacheEntry(facilities, currentTime, skiResortStatus, weatherTemp, weatherCondition));
            adapter = new FacilitiesStatusAdapter(facilities, skiAreaName, skiAreaLogo, skiResortStatus, weatherTemp, weatherCondition, "Last Updated: Just Now");
            recyclerView.setAdapter(adapter);
            splashOverlay.setVisibility(View.GONE);
        }

    }
    public static void clearFacilitiesCache() {
        if (facilitiesCache != null) {
            facilitiesCache.evictAll();
        }
    }
}