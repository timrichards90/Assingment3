package com.example.assingment3;

public class Skifield {
    private String name;
    private int logoId;
    private String url;

    public Skifield(String name, int logoId, String url) {
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
