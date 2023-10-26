package com.example.assingment3;

public class Facility {
    private String category;
    private boolean isOpen;
    private String currentFacilityName;

    public Facility(String category, boolean isOpen) {
        this.category = category;
        this.isOpen = isOpen;
    }

    public void setCurrentFacilityName(String currentFacilityName) {
        this.currentFacilityName = currentFacilityName;
    }

    public String getCurrentFacilityName() {
        return currentFacilityName;
    }

    public String getCategory() {
        return category;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
