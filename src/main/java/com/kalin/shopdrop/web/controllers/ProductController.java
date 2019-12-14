package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.config.annotations.PageTitle;
import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.*;
import com.kalin.shopdrop.validation.product.ProductAddValidator;
import com.kalin.shopdrop.validation.product.ProductEditValidator;
import com.kalin.shopdrop.web.models.AddProductModel;
import com.kalin.shopdrop.web.models.EditProductModel;
import com.kalin.shopdrop.web.models.SendEmailModel;
import com.kalin.shopdrop.web.models.view.ProductAllViewModel;
import com.kalin.shopdrop.web.models.view.ProductViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
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
    private final ProductAddValidator productAddValidator;
    private final ProductEditValidator productEditValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, UserService userService, EmailService emailService, CloudinaryService cloudinaryService, ProductAddValidator productAddValidator, ProductEditValidator productEditValidator, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.emailService = emailService;
        this.cloudinaryService = cloudinaryService;
        this.productAddValidator = productAddValidator;
        this.productEditValidator = productEditValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Product")
    public ModelAndView addProduct(ModelAndView modelAndView, @ModelAttribute(name = "model") AddProductModel model) {
        List<CategoryServiceModel> categoryServiceModels = this.categoryService.getAll();
        // CHECK IF NEEDED TO PASS CategoryViewModel to view
        modelAndView.addObject("model", model);
        modelAndView.addObject("categories", categoryServiceModels);
        return super.view("/product/add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addProductConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") AddProductModel addProductModel, Principal principal, BindingResult bindingResult) throws IOException {
        this.productAddValidator.validate(addProductModel, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", addProductModel);
            return super.view("product/add-product", modelAndView);
        }

        ProductServiceModel productServiceModel = this.modelMapper.map(addProductModel, ProductServiceModel.class);
        //set user
        // String username = ((UserLoginServiceModel) session.getAttribute("user")).getUsername();
        String username = principal.getName();
        productServiceModel.setUser(username);
        //add category
        productServiceModel.setCategory(this.categoryService.getById(addProductModel.getCategory()));
        //add image
        productServiceModel.setImageUrl(this.cloudinaryService.uploadImage(addProductModel.getImage()));
        this.userService.addProductToUser(username, productServiceModel);
        return super.redirect("/home");
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Details Product")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductViewModel model = this.modelMapper.map(this.productService.getById(id), ProductViewModel.class);

        modelAndView.addObject("product", model);

        return super.view("product/details-product", modelAndView);
    }

    @PostMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsProductConfirm(@PathVariable String id, @ModelAttribute SendEmailModel sendEmailModel, Principal principal) {
        //String fromUser = ((UserLoginServiceModel) session.getAttribute("user")).getUsername();
        String fromUser = principal.getName();
        ProductServiceModel productServiceModel = this.productService.getById(id);
        UserServiceModel user = this.userService.getByUsername(productServiceModel.getUser());
        this.emailService.sendEmail(user.getEmail(), fromUser, sendEmailModel.getMessage(), productServiceModel.getName());

        return super.redirect("/home");
    }


    @GetMapping("/all/{username}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Products")
    public ModelAndView allProducts(@PathVariable String username, ModelAndView modelAndView) {
        List<ProductServiceModel> productServiceAll = this.productService.getAllByUserUsername(username);

        List<ProductAllViewModel> products = productServiceAll.stream().map(p -> this.modelMapper.map(p, ProductAllViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("products", products);

        return super.view("product/all-products", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit Product")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView, @ModelAttribute(name = "model") EditProductModel model) {
        ProductServiceModel productServiceModel = this.productService.getById(id);
        model = this.modelMapper.map(productServiceModel, EditProductModel.class);
        model.setCategory(productServiceModel.getCategory().getName());

        modelAndView.addObject("model", model);
        modelAndView.addObject("productId", id);

        return super.view("product/edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProductConfirm(@PathVariable String id, ModelAndView modelAndView, @ModelAttribute(name = "model") EditProductModel model, BindingResult bindingResult) {
        this.productEditValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("productId", id);
            modelAndView.addObject("model", model);

            return super.view("product/edit-product", modelAndView);
        }

        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategory(this.categoryService.getById(model.getCategory()));
        this.productService.editProduct(id, productServiceModel);
        return super.redirect("/home");

    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Delete Product")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.getById(id);
        AddProductModel product = this.modelMapper.map(productServiceModel, AddProductModel.class);
        product.setCategory(productServiceModel.getCategory().getName());

        modelAndView.addObject("product", product);
        modelAndView.addObject("productId", id);

        return super.view("product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteProductConfirm(@PathVariable String id) {
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
