package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.ProductServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    void deleteProduct(String id);

    void deleteAllForCategory(String categoryId);

    List<ProductServiceModel> getAll();

    ProductServiceModel getById(String id);

    List<ProductServiceModel> getAllByCategory(String category);

    List<ProductServiceModel> getAllByUserUsername(String username);

}
