package com.kalin.shopdrop.service.models;

import java.time.LocalDateTime;

public class LogServiceModel extends BaseServiceModel {

    private Long productsCount;
    private String description;
    private LocalDateTime time;

    public LogServiceModel() {
    }

    public Long getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(Long productsCount) {
        this.productsCount = productsCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
