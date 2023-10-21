package com.example.assingment3;

public class Facility {
    private String name;
    private boolean isOpen;
    private String currentFacilityName;
    private String lastUpdated;

    public Facility(String name, boolean isOpen) {
        this.name = name;
        this.isOpen = isOpen;
    }

    public void setCurrentFacilityName(String currentFacilityName) {
        this.currentFacilityName = currentFacilityName;
    }

    public String getCurrentFacilityName() {
        return currentFacilityName;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
