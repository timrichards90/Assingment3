package com.example.assingment3;

import java.util.List;

// class to store ski resort details in LruCache
public class CacheEntry {
    // lifts, roads, accommodation/restaurants
    public List<Facility> facilities;
    // timestamp for when data is added to cache
    public long timestamp;
    // open or closed
    public String skiResortStatus;
    // weather information
    public String weatherTemp;
    // weather information
    public String weatherCondition;

    public CacheEntry(List<Facility> facilities, long timestamp, String skiResortStatus, String weatherTemp, String weatherCondition) {
        this.facilities = facilities;
        this.timestamp = timestamp;
        this.skiResortStatus = skiResortStatus;
        this.weatherTemp = weatherTemp;
        this.weatherCondition = weatherCondition;
    }
}
