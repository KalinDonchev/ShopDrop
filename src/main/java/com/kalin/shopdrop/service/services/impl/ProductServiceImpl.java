package com.kalin.shopdrop.service.services.impl;

import com.kalin.shopdrop.data.models.Category;
import com.kalin.shopdrop.data.models.Product;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.CategoryRepository;
import com.kalin.shopdrop.data.repositories.ProductRepository;
import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.CategoryService;
import com.kalin.shopdrop.service.services.ProductService;
import com.kalin.shopdrop.service.services.UserService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, @Lazy UserService userService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) throws NotFoundException {
        //VALIDATE

        Product product = this.modelMapper.map(productServiceModel, Product.class);
        UserServiceModel userServiceModel = this.userService.getByUsername(productServiceModel.getUser());
        product.setUser(this.modelMapper.map(userServiceModel, User.class));
        this.productRepository.saveAndFlush(product);

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) throws NotFoundException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
        CategoryServiceModel categoryServiceModel = this.categoryService.getByName(productServiceModel.getCategory().getName());
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);
        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setCategory(category);
        this.productRepository.save(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(String id) throws NotFoundException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new NotFoundException("No such product"));
        this.productRepository.delete(product);
    }

    @Override
    public List<ProductServiceModel> getAll() {
        return this.productRepository.findAll().stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel getById(String id) throws NotFoundException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        return this.modelMapper.map(product, ProductServiceModel.class);

    }

    @Override
    public List<ProductServiceModel> getAllByCategory(String categoryString) {
        CategoryServiceModel categoryModel = this.categoryService.getByName(categoryString);
        Category category = this.modelMapper.map(categoryModel, Category.class);
        return this.productRepository.findByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ProductServiceModel> getAllByUserUsername(String username) throws NotFoundException {
        UserServiceModel userServiceModel = this.userService.getByUsername(username);
        List<ProductServiceModel> products = userServiceModel.getProducts();
        return products;
    }
}
