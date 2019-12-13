package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Product;
import com.kalin.shopdrop.data.models.Review;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.errors.IncorrectPasswordException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductService productService;
    private final ReviewService reviewService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductService productService, ReviewService reviewService, RoleService roleService, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.productService = productService;
        this.reviewService = reviewService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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
    public List<UserServiceModel> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel editProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        if (userServiceModel.getPassword() != null) {
            user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));
        } else {
            user.setPassword(user.getPassword());
        }

        user.setEmail(userServiceModel.getEmail());

//        user.setPassword(userServiceModel.getPassword() != null ?
//                this.passwordEncoder.encode(userServiceModel.getPassword()) :
//                user.getPassword());
//        user.setEmail(userServiceModel.getEmail());

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void setAdmin(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No such user"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));

        User newUser = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.save(newUser);

    }

    @Override
    public void setUser(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No such user"));

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        userServiceModel.getAuthorities().clear();

        userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));

        User newUser = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.save(newUser);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
    }
}
