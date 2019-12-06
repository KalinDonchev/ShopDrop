package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.services.ReviewService;
import com.kalin.shopdrop.web.models.AddNewsModel;
import com.kalin.shopdrop.web.models.AddReviewModel;
import com.kalin.shopdrop.web.models.view.ReviewViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/review")
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

    @GetMapping("/allReviews")
    public ModelAndView allReviewsModerator(ModelAndView modelAndView) {
        List<ReviewServiceModel> reviews = this.reviewService.getAll();
        List<ReviewViewModel> models = reviews.stream().map(r -> this.modelMapper.map(r, ReviewViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("reviews", models);
        return super.view("review/all-reviews-moderator", modelAndView);
    }


    @GetMapping("/delete/{id}")
    public ModelAndView deleteReview(@PathVariable String id, ModelAndView modelAndView) throws NotFoundException {
        ReviewServiceModel reviewServiceModel = this.reviewService.getById(id);
        AddReviewModel review = this.modelMapper.map(reviewServiceModel, AddReviewModel.class);

        modelAndView.addObject("review", review);
        modelAndView.addObject("reviewId", id);

        return super.view("review/delete-review", modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteReviewConfirm(@PathVariable String id) throws NotFoundException {
        this.reviewService.deleteReview(id);
        return super.redirect("/home");
    }


}
