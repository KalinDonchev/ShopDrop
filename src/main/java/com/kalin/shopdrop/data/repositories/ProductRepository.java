package com.kalin.shopdrop.data.repositories;

import com.kalin.shopdrop.data.models.Category;
import com.kalin.shopdrop.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByCategory(Category category);

}
