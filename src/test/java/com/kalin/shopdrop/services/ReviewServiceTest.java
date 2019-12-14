package com.kalin.shopdrop.services;

import com.kalin.shopdrop.TestBase;
import com.kalin.shopdrop.data.models.Review;
import com.kalin.shopdrop.data.repositories.ReviewRepository;
import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReviewServiceTest extends TestBase {

    @MockBean
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @Test
    public void deleteReview_shouldDeleteCorrectly_whenDataIsValid() {
        Review review = new Review();
        review.setId("1");
        when(reviewRepository.findById("1"))
                .thenReturn(Optional.of(review));

        reviewService.deleteReview("1");

        verify(reviewRepository).delete(any());
    }

    @Test
    public void getAll_whenThereAreReviews_shouldReturnAllReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review());
        reviews.add(new Review());

        when(reviewRepository.findAll())
                .thenReturn(reviews);

        List<ReviewServiceModel> reviewServiceModels = reviewService.getAll();

        assertEquals(2, reviewServiceModels.size());
    }

    @Test
    public void getAll_whenThereAreNoReviews_shouldReturnEmpty() {
        when(reviewRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<ReviewServiceModel> reviewServiceModels = reviewService.getAll();

        assertEquals(0, reviewServiceModels.size());
    }

    @Test
    public void getById_whenIdIsCorrect_shouldReturnReview() {

        Review review = new Review();
        review.setId("1");

        when(reviewRepository.findById("1"))
                .thenReturn(Optional.of(review));

        ReviewServiceModel reviewServiceModel = reviewService.getById("1");

        assertEquals("1", reviewServiceModel.getId());
    }

    @Test
    public void getById_whenIdIsNotCorrect_shouldThrowException() {
        when(reviewRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> reviewService.getById("1"));
    }



}
