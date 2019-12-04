package com.kalin.shopdrop.data.repositories;

import com.kalin.shopdrop.data.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
}
