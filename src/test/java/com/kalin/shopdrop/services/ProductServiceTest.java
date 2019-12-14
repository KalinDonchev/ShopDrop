package com.kalin.shopdrop.services;

import com.kalin.shopdrop.TestBase;
import com.kalin.shopdrop.data.models.Category;
import com.kalin.shopdrop.data.models.Product;
import com.kalin.shopdrop.data.repositories.ProductRepository;
import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest extends TestBase {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;


    @Test
    public void deleteProduct_shouldDeleteCorrectly_whenDataIsValid() {
        Product product = new Product();
        product.setId("1");
        when(productRepository.findById("1"))
                .thenReturn(Optional.of(product));

        productService.deleteProduct("1");

        verify(productRepository).delete(any());
    }


    @Test
    public void getAll_whenThereAreProducts_shouldReturnAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());

        when(productRepository.findAll())
                .thenReturn(products);

        List<ProductServiceModel> productServiceModels = productService.getAll();

        assertEquals(2, productServiceModels.size());
    }

    @Test
    public void getAll_whenThereAreNoProducts_shouldReturnEmpty() {
        when(productRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<ProductServiceModel> productServiceModels = productService.getAll();

        assertEquals(0, productServiceModels.size());
    }


    @Test
    public void getById_whenIdIsCorrect_shouldReturnProduct() {

        Product product = new Product();
        product.setId("1");

        when(productRepository.findById("1"))
                .thenReturn(Optional.of(product));

        ProductServiceModel productServiceModel = productService.getById("1");

        assertEquals("1", productServiceModel.getId());
    }

    @Test
    public void getById_whenIdIsNotCorrect_shouldThrowException() {
        when(productRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> productService.getById("1"));
    }


    @Test
    public void getAllByCategory_whenNoCategories_shouldReturnEmpty() {
        List<ProductServiceModel> productServiceModels = productService.getAllByCategory("Cars");

        assertEquals(0, productServiceModels.size());
    }




}
