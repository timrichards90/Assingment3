package com.example.assingment3;

public class FacilitiesStatus {
    String facilityName;
    String liftName;
    String serviceName;
    String roadName;
    boolean isOpen;

    public FacilitiesStatus(String facilityName, String liftName, String serviceName, String roadName, boolean isOpen) {
        this.facilityName = facilityName;
        this.liftName = liftName;
        this.serviceName = serviceName;
        this.roadName = roadName;
        this.isOpen = isOpen;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public String getLiftName() {
        return liftName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getRoadName() {
        return roadName;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
