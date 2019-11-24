package com.kalin.shopdrop.data.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> product;

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
