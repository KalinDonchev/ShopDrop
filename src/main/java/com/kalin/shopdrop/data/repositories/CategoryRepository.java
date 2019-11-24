package com.kalin.shopdrop.data.repositories;

import com.kalin.shopdrop.data.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findAll();

    //REFACTOR OPTIONAL
    Category findByName(String name);

}
