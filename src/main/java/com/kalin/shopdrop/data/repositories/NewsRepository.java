package com.kalin.shopdrop.data.repositories;

import com.kalin.shopdrop.data.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, String> {
}
