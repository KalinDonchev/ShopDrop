package com.kalin.shopdrop.data.repositories;

import com.kalin.shopdrop.data.models.Category;
import com.kalin.shopdrop.data.models.Product;
import com.kalin.shopdrop.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByName(String name);

    List<Product> findByCategory(Category category);

    List<Product> findByUser(User user);

}
