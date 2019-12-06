package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.services.CategoryService;
import com.kalin.shopdrop.web.models.AddCategoryModel;
import com.kalin.shopdrop.web.models.view.CategoryAllViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/all")
    public ModelAndView allCategories(ModelAndView modelAndView) {
        List<CategoryServiceModel> categoryServiceModels = this.categoryService.getAll();
        List<CategoryAllViewModel> categories = categoryServiceModels.stream().map(c -> this.modelMapper.map(c, CategoryAllViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        return super.view("category/all-categories", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCateogory(@PathVariable String id, ModelAndView modelAndView){
        CategoryServiceModel categoryServiceModel = this.categoryService.getById(id);
        AddCategoryModel category = this.modelMapper.map(categoryServiceModel, AddCategoryModel.class);
        category.setName(categoryServiceModel.getName());

        modelAndView.addObject("category", category);
        modelAndView.addObject("categoryId", id);

        return super.view("category/delete-category", modelAndView);

    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteProductConfirm(@PathVariable String id) throws NotFoundException {
        this.categoryService.deleteCategory(id);
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
