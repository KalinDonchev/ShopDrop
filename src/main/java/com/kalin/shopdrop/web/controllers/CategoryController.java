package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.services.CategoryService;
import com.kalin.shopdrop.web.models.AddCategoryModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addCategory() {
        return super.view("category/add-category");
    }

    @PostMapping("/add")
    public ModelAndView addCategoryConfirm(@ModelAttribute AddCategoryModel addCategoryModel) {
        CategoryServiceModel categoryServiceModel = this.modelMapper.map(addCategoryModel, CategoryServiceModel.class);
        this.categoryService.addCategory(categoryServiceModel);
        return super.redirect("/home");
    }

    // LOAD ASYNC CATEGORIES
    @GetMapping("/fetch")
    @ResponseBody
    public List<CategoryServiceModel> fetchCategories() {

        // MAY NEED CATEGORYVIEWMODEL
        return this.categoryService.getAll();
    }


}
