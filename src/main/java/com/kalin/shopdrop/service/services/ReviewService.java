package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.ReviewServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface ReviewService {

    ReviewServiceModel addReview(ReviewServiceModel reviewServiceModel) throws NotFoundException;

    List<ReviewServiceModel> getAll();

    ReviewServiceModel getById(String id) throws NotFoundException;

    void deleteReview(String id) throws NotFoundException;
}
