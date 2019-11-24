package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.services.CategoryService;
import com.kalin.shopdrop.service.services.CloudinaryService;
import com.kalin.shopdrop.service.services.ProductService;
import com.kalin.shopdrop.service.services.UserService;
import com.kalin.shopdrop.web.models.AddProductModel;
import com.kalin.shopdrop.web.models.ProductAllViewModel;
import com.kalin.shopdrop.web.models.ProductViewModel;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, UserService userService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView addProduct(ModelAndView modelAndView) {
        List<CategoryServiceModel> categoryServiceModels = this.categoryService.getAll();
        // CHECK IF NEEDED TO PASS CategoryViewModel to view
        modelAndView.addObject("categories", categoryServiceModels);
        return super.view("/product/add-product");
    }

    @PostMapping("/add")
    public ModelAndView addProductConfirm(@ModelAttribute AddProductModel addProductModel, HttpSession session) throws IOException, NotFoundException {
        ProductServiceModel productServiceModel = this.modelMapper.map(addProductModel, ProductServiceModel.class);
        //set user
        String username = ((UserLoginServiceModel) session.getAttribute("user")).getUsername();
        productServiceModel.setUser(username);
        //add category
        productServiceModel.setCategory(this.categoryService.getById(addProductModel.getCategory()));
        System.out.println();
        //add image
        productServiceModel.setImageUrl(this.cloudinaryService.uploadImage(addProductModel.getImage()));
        this.userService.addProductToUser(username, productServiceModel);
        return super.redirect("/home");
    }

    @GetMapping("/details/{id}")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) throws NotFoundException {
        ProductViewModel model = this.modelMapper.map(this.productService.getById(id), ProductViewModel.class);

        modelAndView.addObject("product", model);

        return super.view("product/details-product", modelAndView);
    }


//    @GetMapping("/all")
//    public ModelAndView allProducts(@PathVariable String id, ModelAndView modelAndView) {
//        List<ProductServiceModel> productServiceAll = this.productService.getAll();
//
//        List<ProductAllViewModel> products = productServiceAll.stream().map(p -> this.modelMapper.map(p, ProductAllViewModel.class)).collect(Collectors.toList());
//        modelAndView.addObject("products", products);
//
//        return super.view("product/all-products", modelAndView);
//    }


//    // LOAD ASYNC CATEGORIES
//    @GetMapping("/fetch")
//    @ResponseBody
//    public List<CategoryServiceModel> fetchCategories() {
//
//        // MAY NEED CATEGORYVIEWMODEL
//        return this.categoryService.getAll();
//    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<ProductViewModel> fetchByCategory(@PathVariable String category) {
        if (category.equals("all")) {
            return this.productService.getAll()
                    .stream()
                    .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                    .collect(Collectors.toList());
        }

        List<ProductViewModel> collect = this.productService.getAllByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
        return collect;
    }


}
