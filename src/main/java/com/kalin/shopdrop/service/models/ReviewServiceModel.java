package com.kalin.shopdrop.service.models;

import java.util.List;

public class ReviewServiceModel extends BaseServiceModel {


    private String title;
    private String text;
    private String user;

    public ReviewServiceModel() {
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
