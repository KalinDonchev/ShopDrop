package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Product;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.ProductService;
import com.kalin.shopdrop.service.services.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductService productService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public void addProductToUser(String username, ProductServiceModel productServiceModel) throws NotFoundException {
        // CREATE CUSTOM EXCEPTIONS
        User user = this.userRepository.findByUsername(productServiceModel.getUser()).orElseThrow(() -> new NotFoundException("user not found"));
        this.productService.addProduct(productServiceModel);
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product.setUser(user);
        List<Product> products = user.getProducts();
        products.add(product);
        this.userRepository.save(user);
    }

    @Override
    public UserServiceModel getByUsername(String username) throws NotFoundException {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("No such user"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }
}
