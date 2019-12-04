package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.UserService;
import com.kalin.shopdrop.web.models.view.UserProfileViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, HttpSession session) throws NotFoundException {
        String username = ((UserLoginServiceModel) session.getAttribute("user")).getUsername();
        UserServiceModel userServiceModel = this.userService.getByUsername(username);
        UserProfileViewModel userProfileViewModel = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);
        modelAndView.addObject("model", userProfileViewModel);
        return super.view("user/profile", modelAndView);
    }


}
