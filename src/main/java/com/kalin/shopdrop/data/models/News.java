package com.kalin.shopdrop.data.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class News extends BaseEntity{

    private String title;
    private String text;

    public News() {
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
