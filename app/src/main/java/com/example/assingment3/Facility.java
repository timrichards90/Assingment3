package com.example.assingment3;

public class Facility {
    private String name;
    private boolean isOpen;

    public Facility(String name, boolean isOpen) {
        this.name = name;
        this.isOpen = isOpen;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return isOpen;
    }

}
