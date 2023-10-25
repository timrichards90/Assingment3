package com.example.assingment3;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    ReportGeneratorAdapter adapter;
    RelativeLayout splashOverlay;
    ImageView splashLogo;
    String skiAreaStatus;
    String skiAreaName;
    int skiAreaLogo;
    String weatherTemp;
    String weatherCondition;
    String skiAreaUrl;
    private static final int CACHE_SIZE = 20; // maximum number of cache entries
    private static LruCache<String, CacheEntry> facilitiesCache = new LruCache<>(CACHE_SIZE);
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ski_area_activity);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            SkiAreaDataScraper skiAreaDataScraper = new SkiAreaDataScraper();
            skiAreaDataScraper.execute();
        });

        // get data passed from ski area adapter
        retrieveDataFromIntent();

        // set up splash screen that is displayed while app is scraping website
        splashOverlay = findViewById(R.id.splashOverlay);
        splashLogo = findViewById(R.id.splashLogo);
        if (skiAreaLogo != -1) {
            splashLogo.setImageResource(skiAreaLogo);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CacheEntry cachedEntry = facilitiesCache.get(skiAreaUrl);

        // if cached data exists use it
        if (cachedEntry != null) {
            // for the time stamp displayed at bottom of report
            long timeDiff = System.currentTimeMillis() - cachedEntry.timestamp;
            String timeString = getTimeString(timeDiff);
            skiAreaStatus = cachedEntry.skiResortStatus;
            weatherTemp = cachedEntry.weatherTemp;
            weatherCondition = cachedEntry.weatherCondition;

            adapter = new ReportGeneratorAdapter(cachedEntry.facilities, skiAreaName, skiAreaLogo, skiAreaStatus, weatherTemp, weatherCondition, timeString);
            recyclerView.setAdapter(adapter);
            splashOverlay.setVisibility(View.GONE);
        // else scrape the data
        } else {
            SkiAreaDataScraper skiAreaDataScraper = new SkiAreaDataScraper();
            skiAreaDataScraper.execute();
        }
    }
    // scrape ski area data from the web
    @SuppressLint("StaticFieldLeak")
    private class SkiAreaDataScraper extends AsyncTask<Void, Void, List<Facility>> {

        @Override
        protected List<Facility> doInBackground(Void... voids) {
            List<Facility> facilities = new ArrayList<>();
            Document document;

            String currentFacilityName;

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

            skiAreaStatus = Objects.requireNonNull(document.select("div.closed-state h5.title").first()).text().trim();

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
            facilitiesCache.put(skiAreaUrl, new CacheEntry(facilities, currentTime, skiAreaStatus, weatherTemp, weatherCondition));
            adapter = new ReportGeneratorAdapter(facilities, skiAreaName, skiAreaLogo, skiAreaStatus, weatherTemp, weatherCondition, "Last Updated: Just Now");
            recyclerView.setAdapter(adapter);
            splashOverlay.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    // format the time difference
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

    // clear the cached data
    public static void clearFacilitiesCache() {
        if (facilitiesCache != null) {
            facilitiesCache.evictAll();
        }
    }

    // get intent data passed from ski area adapter
    private void retrieveDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            skiAreaName = bundle.getString("skiAreaName");
            skiAreaLogo = bundle.getInt("skiAreaLogo", -1);
            skiAreaUrl = bundle.getString("skiAreaUrl");
        }
    }
}