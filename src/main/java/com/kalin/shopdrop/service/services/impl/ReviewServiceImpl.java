package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Review;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.ReviewRepository;
import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.ReviewService;
import com.kalin.shopdrop.service.services.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, @Lazy UserService userService, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewServiceModel addReview(ReviewServiceModel reviewServiceModel) throws NotFoundException {
        Review review = this.modelMapper.map(reviewServiceModel, Review.class);
        UserServiceModel userServiceModel = this.userService.getByUsername(reviewServiceModel.getUser());
        review.setUser(this.modelMapper.map(userServiceModel, User.class));
        this.reviewRepository.saveAndFlush(review);
        return this.modelMapper.map(review, ReviewServiceModel.class);
    }

    @Override
    public List<ReviewServiceModel> getAll() {
        List<Review> reviews = this.reviewRepository.findAll();
        return reviews.stream().map(r -> this.modelMapper.map(r, ReviewServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public ReviewServiceModel getById(String id) throws NotFoundException {
        Review review = this.reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        return this.modelMapper.map(review, ReviewServiceModel.class);
    }

    @Override
    public void deleteReview(String id) throws NotFoundException {
        Review review = this.reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("No such review"));
        this.reviewRepository.delete(review);
    }


}
