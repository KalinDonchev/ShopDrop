package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Product;
import com.kalin.shopdrop.data.models.Review;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.errors.UserNotFoundException;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.ReviewServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.ProductService;
import com.kalin.shopdrop.service.services.ReviewService;
import com.kalin.shopdrop.service.services.RoleService;
import com.kalin.shopdrop.service.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductService productService, ReviewService reviewService, RoleService roleService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.productService = productService;
        this.reviewService = reviewService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }


    @Override
    @Transactional
    public void addProductToUser(String username, ProductServiceModel productServiceModel) {
        // CREATE CUSTOM EXCEPTIONS
        User user = this.userRepository.findByUsername(productServiceModel.getUser()).orElseThrow(() -> new UserNotFoundException("User not found"));
        this.productService.addProduct(productServiceModel);
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product.setUser(user);
        List<Product> products = user.getProducts();
        products.add(product);
        this.userRepository.save(user);
    }

    @Override
    public void addReviewToUser(String username, ReviewServiceModel reviewServiceModel) {
        User user = this.userRepository.findByUsername(reviewServiceModel.getUser()).orElseThrow(() -> new UserNotFoundException("No such user"));
        this.reviewService.addReview(reviewServiceModel);
        Review review = this.modelMapper.map(reviewServiceModel, Review.class);
        review.setUser(user);
        List<Review> reviews = user.getReviews();
        reviews.add(review);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public UserServiceModel getByUsername(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("No such user"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel getById(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void makeAdmin(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No such user"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public void makeUser(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No such user"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
    }
}
