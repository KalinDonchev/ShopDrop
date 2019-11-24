package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.UserServiceModel;

public interface AuthValidationService {

    boolean isValid(UserServiceModel userServiceModel);

}
