package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import javassist.NotFoundException;

public interface UserService {

    void addProductToUser(String username, ProductServiceModel productServiceModel);

    void addReviewToUser(String username, ReviewServiceModel reviewServiceModel);

    UserServiceModel getByUsername(String username);

    UserServiceModel getById(String id);
}
