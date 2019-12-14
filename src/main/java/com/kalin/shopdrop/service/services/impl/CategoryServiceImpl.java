package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Category;
import com.kalin.shopdrop.data.repositories.CategoryRepository;
import com.kalin.shopdrop.errors.CategoryNotFoundException;
import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.models.LogServiceModel;
import com.kalin.shopdrop.service.services.CategoryService;
import com.kalin.shopdrop.service.services.LogService;
import com.kalin.shopdrop.service.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final LogService logService;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, @Lazy ProductService productService, ModelMapper modelMapper, LogService logService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.logService = logService;
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        // VALIDATE

        Category category = this.modelMapper.map(categoryServiceModel, Category.class);


        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setDescription("Category added");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.categoryRepository.saveAndFlush(category);
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public List<CategoryServiceModel> getAll() {
        return this.categoryRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel getById(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public CategoryServiceModel getByName(String name) {
        Category category = this.categoryRepository.findByName(name).orElseThrow(() -> new CategoryNotFoundException("No such category"));
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public void deleteCategory(String id) {
        this.productService.deleteAllForCategory(id);
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("No such category"));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setDescription("Category deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.categoryRepository.delete(category);
    }
}
