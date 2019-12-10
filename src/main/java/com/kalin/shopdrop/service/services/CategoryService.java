package com.kalin.shopdrop.service.services;

import com.kalin.shopdrop.service.models.CategoryServiceModel;
import javassist.NotFoundException;

import java.util.List;

public interface CategoryService {

    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> getAll();

    CategoryServiceModel getById(String id);

    CategoryServiceModel getByName(String name);

    void deleteCategory(String id);
}
