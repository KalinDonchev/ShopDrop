package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import javassist.NotFoundException;

public interface UserService {

    void addProductToUser(String username, ProductServiceModel productServiceModel) throws NotFoundException;

    void addReviewToUser(String username, ReviewServiceModel reviewServiceModel) throws NotFoundException;

    UserServiceModel getByUsername(String username) throws NotFoundException;

    UserServiceModel getById(String id) throws NotFoundException;
}
