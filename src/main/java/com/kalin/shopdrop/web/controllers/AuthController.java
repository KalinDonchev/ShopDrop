package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.AuthService;
import com.kalin.shopdrop.web.models.LoginUserModel;
import com.kalin.shopdrop.web.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class AuthController extends BaseController {

    private final AuthService authService;
    private final ModelMapper modelMapper;


    @Autowired
    public AuthController(AuthService authService, ModelMapper modelMapper) {
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return super.view("auth/register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute RegisterUserModel registerUserModel) {
        UserServiceModel userServiceModel = this.modelMapper.map(registerUserModel, UserServiceModel.class);
        this.authService.register(userServiceModel);
        return super.redirect("/home");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return super.view("auth/login");
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@ModelAttribute LoginUserModel loginUserModel, HttpSession session) {
        UserLoginServiceModel userLoginServiceModel = this.modelMapper.map(loginUserModel, UserLoginServiceModel.class);
        try {
            UserLoginServiceModel login = this.authService.login(userLoginServiceModel);
            session.setAttribute("user", login);
            return super.redirect("/home");
        } catch (Exception e) {
            return super.redirect("/user/login");
        }

    }


}
