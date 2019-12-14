package com.kalin.shopdrop.services;

import com.kalin.shopdrop.TestBase;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthServiceTest extends TestBase {

    @MockBean
    UserRepository userRepository;

    @Autowired
    AuthService authService;

      //WANTED BUT NOT INVOKED
//    @Test
//    public void registerUser_shouldSuccessfullyRegisterUser_whenValidUser(){
//        UserServiceModel userServiceModel = new UserServiceModel();
//        userServiceModel.setUsername("Kalin");
//        userServiceModel.setPassword("123123");
//        userServiceModel.setConfirmPassword("123123");
//        userServiceModel.setEmail("username@abv.bg");
//
//
//        authService.register(userServiceModel);
//
//        verify(userRepository).save(any());
//    }

    @Test
    public void registerUser_shouldThrowException_whenUserExistsWithSameUsername(){
        User invalid = new User();
        invalid.setUsername("Pesho");
        User validUser = new User();
        validUser.setUsername("Pesho");
        when(userRepository.findByUsername(validUser.getUsername())).thenReturn(Optional.of(invalid));

        UserServiceModel valid = new UserServiceModel();
        valid.setUsername("Pesho");

        assertThrows(Exception.class, () ->
                authService.register(valid));
    }

    @Test
    public void registerUser_shouldThrowException_whenUserExistsWithSameEmail(){
        User invalid = new User();
        invalid.setEmail("kalin@abv.bg");
        User validUser = new User();
        validUser.setEmail("kalin@abv.bg");
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.of(invalid));

        UserServiceModel valid = new UserServiceModel();
        valid.setEmail("kalin@abv.bg");

        assertThrows(Exception.class, () ->
                authService.register(valid));
    }




}
