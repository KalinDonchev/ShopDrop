package com.kalin.shopdrop.data.models;

import javax.persistence.*;


@Entity
@Table(name = "reviews")
public class Review extends BaseEntity{

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Review() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
