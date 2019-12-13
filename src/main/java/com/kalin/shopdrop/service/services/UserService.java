package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void addProductToUser(String username, ProductServiceModel productServiceModel);

    void addReviewToUser(String username, ReviewServiceModel reviewServiceModel);

    UserServiceModel getByUsername(String username);

    UserServiceModel getById(String id);

    void makeAdmin(String id);

    void makeUser(String id);
}
