package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.UserLoginServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.*;
import com.kalin.shopdrop.web.models.AddProductModel;
import com.kalin.shopdrop.web.models.SendEmailModel;
import com.kalin.shopdrop.web.models.view.ProductAllViewModel;
import com.kalin.shopdrop.web.models.view.ProductViewModel;
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
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, UserService userService, EmailService emailService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.emailService = emailService;
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

    @PostMapping("/details/{id}")
    public ModelAndView detailsProductConfirm(@PathVariable String id, @ModelAttribute SendEmailModel sendEmailModel, HttpSession session) throws NotFoundException {
        String fromUser = ((UserLoginServiceModel) session.getAttribute("user")).getUsername();
        ProductServiceModel productServiceModel = this.productService.getById(id);
        UserServiceModel user = this.userService.getByUsername(productServiceModel.getUser());
        this.emailService.sendEmail(user.getEmail(), fromUser, sendEmailModel.getMessage(), productServiceModel.getName());

        return super.redirect("/home");
    }


    @GetMapping("/all/{username}")
    public ModelAndView allProducts(@PathVariable String username, ModelAndView modelAndView) throws NotFoundException {
        List<ProductServiceModel> productServiceAll = this.productService.getAllByUserUsername(username);

        List<ProductAllViewModel> products = productServiceAll.stream().map(p -> this.modelMapper.map(p, ProductAllViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("products", products);

        return super.view("product/all-products", modelAndView);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) throws NotFoundException {
        ProductServiceModel productServiceModel = this.productService.getById(id);
        AddProductModel product = this.modelMapper.map(productServiceModel, AddProductModel.class);
        product.setCategory(productServiceModel.getCategory().getName());

        modelAndView.addObject("product", product);
        modelAndView.addObject("productId", id);

        return super.view("product/edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editProductConfirm(@PathVariable String id, @ModelAttribute AddProductModel addProductModel) throws NotFoundException {
        ProductServiceModel productServiceModel = this.modelMapper.map(addProductModel, ProductServiceModel.class);
        productServiceModel.setCategory(this.categoryService.getById(addProductModel.getCategory()));
        this.productService.editProduct(id, productServiceModel);
        return super.redirect("/home");

    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) throws NotFoundException {
        ProductServiceModel productServiceModel = this.productService.getById(id);
        AddProductModel product = this.modelMapper.map(productServiceModel, AddProductModel.class);
        product.setCategory(productServiceModel.getCategory().getName());

        modelAndView.addObject("product", product);
        modelAndView.addObject("productId", id);

        return super.view("product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteProductConfirm(@PathVariable String id) throws NotFoundException {
        this.productService.deleteProduct(id);
        return super.redirect("/home");
    }


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

        List<ProductViewModel> collect = this.productService.getAllByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
        return collect;
    }


}
