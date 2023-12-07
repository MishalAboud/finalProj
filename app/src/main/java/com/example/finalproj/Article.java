package com.example.finalproj;

public class Article {
    private String title;
    private String url;
    private String hdUrl;
    private String explanation;
    private String date;
    private String sectionName;

    public Article(String title, String url, String hdUrl, String explanation, String date, String sectionName) {
        this.title = title;
        this.url = url;
        this.hdUrl = hdUrl;
        this.explanation = explanation;
        this.date = date;
        this.sectionName = sectionName;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getHdUrl() {
        return hdUrl;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getDate() {
        return date;
    }

    public String getSectionName() {
        return sectionName;
    }

}
