package com.example.assingment3;

import java.util.List;

public class CacheEntry {
    public List<Facility> facilities;
    public long timestamp;
    public String skiResortStatus;
    public String weatherTemp;
    public String weatherCondition;

    public CacheEntry(List<Facility> facilities, long timestamp, String skiResortStatus, String weatherTemp, String weatherCondition) {
        this.facilities = facilities;
        this.timestamp = timestamp;
        this.skiResortStatus = skiResortStatus;
        this.weatherTemp = weatherTemp;
        this.weatherCondition = weatherCondition;
    }
}
