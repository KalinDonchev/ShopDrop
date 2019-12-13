package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.AuthService;
import com.kalin.shopdrop.validation.user.UserRegisterValidator;
import com.kalin.shopdrop.web.models.LoginUserModel;
import com.kalin.shopdrop.web.models.RegisterUserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private final AuthService authService;
    private final ModelMapper modelMapper;
    private final UserRegisterValidator userRegisterValidator;


    @Autowired
    public AuthController(AuthService authService, ModelMapper modelMapper, UserRegisterValidator userRegisterValidator) {
        this.authService = authService;
        this.modelMapper = modelMapper;
        this.userRegisterValidator = userRegisterValidator;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute(name = "model") RegisterUserModel model) {
        modelAndView.addObject("model", model);
        return super.view("auth/register", modelAndView);
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") RegisterUserModel registerUserModel, BindingResult bindingResult) {
        this.userRegisterValidator.validate(registerUserModel, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", registerUserModel);
            return super.view("auth/register", modelAndView);
        }

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
            return super.redirect("/auth/login");
        }

    }


}
