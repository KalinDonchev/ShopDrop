package com.kalin.shopdrop.web.models;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class AddProductModel {

    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private String category;

    public AddProductModel() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
