package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.ProductServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel productServiceModel) throws NotFoundException;

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) throws NotFoundException;

    void deleteProduct(String id) throws NotFoundException;

    List<ProductServiceModel> getAll();

    ProductServiceModel getById(String id) throws NotFoundException;

    List<ProductServiceModel> getAllByCategory(String category);

    List<ProductServiceModel> getAllByUserUsername(String username) throws NotFoundException;

}
