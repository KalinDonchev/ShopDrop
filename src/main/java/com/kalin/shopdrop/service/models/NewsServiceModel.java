package com.kalin.shopdrop.service.models;

public class NewsServiceModel extends BaseServiceModel {

    private String title;
    private String text;

    public NewsServiceModel() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
