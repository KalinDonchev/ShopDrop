package com.kalin.shopdrop.web.controllers;

import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.services.ProductService;
import com.kalin.shopdrop.web.models.view.ProductViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView) {
        List<ProductServiceModel> productServiceModels = this.productService.getAll();
        List<ProductViewModel> viewModels = productServiceModels.stream().map(p -> this.modelMapper.map(p, ProductViewModel.class)).collect(Collectors.toList());
        //FIX USER -> SHOWING MODEL
        modelAndView.addObject("products", viewModels);
        return super.view("home", modelAndView);
    }


}
