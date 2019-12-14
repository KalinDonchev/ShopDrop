package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Review;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.ReviewRepository;
import com.kalin.shopdrop.errors.ReviewNotFoundException;
import com.kalin.shopdrop.service.models.LogServiceModel;
import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.LogService;
import com.kalin.shopdrop.service.services.ReviewService;
import com.kalin.shopdrop.service.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final LogService logService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, @Lazy UserService userService, ModelMapper modelMapper, LogService logService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.logService = logService;
    }

    @Override
    public ReviewServiceModel addReview(ReviewServiceModel reviewServiceModel) {
        Review review = this.modelMapper.map(reviewServiceModel, Review.class);
        UserServiceModel userServiceModel = this.userService.getByUsername(reviewServiceModel.getUser());
        review.setUser(this.modelMapper.map(userServiceModel, User.class));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setDescription("Review added");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.reviewRepository.saveAndFlush(review);
        return this.modelMapper.map(review, ReviewServiceModel.class);
    }

    @Override
    public List<ReviewServiceModel> getAll() {
        List<Review> reviews = this.reviewRepository.findAll();
        return reviews.stream().map(r -> this.modelMapper.map(r, ReviewServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public ReviewServiceModel getById(String id) {
        Review review = this.reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("No such review"));
        return this.modelMapper.map(review, ReviewServiceModel.class);
    }

    @Override
    public void deleteReview(String id) {
        Review review = this.reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("No such review"));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setDescription("Review deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.reviewRepository.delete(review);
    }


}
