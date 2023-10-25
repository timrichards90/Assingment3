package com.example.assingment3;

public class SkiArea {
    // ski area name
    private String name;
    // ski area logoid
    private int logoId;
    // ski area url to scrape data from
    private String url;

    public SkiArea(String name, int logoId, String url) {
        this.name = name;
        this.logoId = logoId;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public int getLogoId() {
        return logoId;
    }

    public String getUrl() { return url; }
}
