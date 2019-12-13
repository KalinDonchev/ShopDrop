package com.kalin.shopdrop.service.services;


import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.web.models.RegisterUserModel;

public interface AuthService {

    UserServiceModel register(UserServiceModel userServiceModel);


}
