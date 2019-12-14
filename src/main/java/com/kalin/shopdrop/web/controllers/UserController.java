package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.UserService;
import com.kalin.shopdrop.validation.user.UserEditValidator;
import com.kalin.shopdrop.web.models.UserEditModel;
import com.kalin.shopdrop.web.models.view.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserEditValidator userEditValidator;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserEditValidator userEditValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userEditValidator = userEditValidator;
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal) {
        //String username = ((UserLoginServiceModel) session.getAttribute("user")).getUsername();
        String username = principal.getName();
        UserServiceModel userServiceModel = this.userService.getByUsername(username);
        UserProfileViewModel userProfileViewModel = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);
        modelAndView.addObject("model", userProfileViewModel);
        return super.view("user/profile", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {

        List<UserServiceModel> users = this.userService.getAllUsers()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("users", users);

        return super.view("user/all-users", modelAndView);
    }


    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal, ModelAndView modelAndView, @ModelAttribute(name = "model") UserEditModel model) {
        UserServiceModel userServiceModel = this.userService.getByUsername(principal.getName());
        model = this.modelMapper.map(userServiceModel, UserEditModel.class);
        model.setPassword(null);
        modelAndView.addObject("model", model);

        return super.view("user/edit-profile", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfileConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") UserEditModel model, BindingResult bindingResult) {
        this.userEditValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            return super.view("user/edit-profile", modelAndView);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
        this.userService.editProfile(userServiceModel, model.getOldPassword());

        return super.redirect("/user/profile");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdminRole(@PathVariable String id) {
        this.userService.setAdmin(id);
        return super.redirect("/user/all");
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ModelAndView setUserRole(@PathVariable String id) {
        this.userService.setUser(id);
        return super.redirect("/user/all");
    }



}
