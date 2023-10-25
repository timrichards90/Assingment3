package com.example.assingment3;

public class Facility {
    // such as lift, services and facilities, or road
    private String category;
    // if the facility is open or not
    private boolean isOpen;
    // such as the name of the lift, service or road
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
