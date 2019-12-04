package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.services.ReviewService;
import com.kalin.shopdrop.web.models.AddReviewModel;
import com.kalin.shopdrop.web.models.view.ReviewViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("review")
public class ReviewController extends BaseController {

    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addReview() {
        return super.view("review/add-review");
    }

    @PostMapping("/add")
    public ModelAndView addReviewConfirm(@ModelAttribute AddReviewModel addReviewModel, HttpSession session) throws NotFoundException {
        ReviewServiceModel reviewServiceModel = this.modelMapper.map(addReviewModel, ReviewServiceModel.class);
        String username = ((UserLoginServiceModel) session.getAttribute("user")).getUsername();
        reviewServiceModel.setUser(username);
        this.reviewService.addReview(reviewServiceModel);
        return super.redirect("/review/all");
    }

    @GetMapping("/all")
    public ModelAndView allReviews(ModelAndView modelAndView) {
        List<ReviewServiceModel> reviews = this.reviewService.getAll();
        List<ReviewViewModel> models = reviews.stream().map(r -> this.modelMapper.map(r, ReviewViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("reviews", models);
        return super.view("review/all-reviews", modelAndView);
    }




}
