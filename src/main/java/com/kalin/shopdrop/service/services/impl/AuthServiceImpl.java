package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.AuthService;
import com.kalin.shopdrop.service.services.AuthValidationService;
import com.kalin.shopdrop.service.services.HashingService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final AuthValidationService authValidationService;

    @Autowired
    public AuthServiceImpl(ModelMapper modelMapper, UserRepository userRepository, HashingService hashingService, AuthValidationService authValidationService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.authValidationService = authValidationService;
    }


    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        if (!authValidationService.isValid(userServiceModel)) {
            // do something

        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.hashingService.hash(user.getPassword()));
        this.userRepository.saveAndFlush(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserLoginServiceModel login(UserLoginServiceModel userLoginServiceModel) throws Exception {
        String passwordHash = this.hashingService.hash(userLoginServiceModel.getPassword());

            User user = this.userRepository.findByUsernameAndPassword(userLoginServiceModel.getUsername(), passwordHash).orElseThrow(() -> new NotFoundException("No such user"));
            UserLoginServiceModel mapped = this.modelMapper.map(user, UserLoginServiceModel.class);
            mapped.setUsername(user.getUsername());
            mapped.setPassword(user.getPassword());

            return mapped;

    }


}
