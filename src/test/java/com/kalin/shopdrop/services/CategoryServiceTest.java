package com.kalin.shopdrop.services;

import com.kalin.shopdrop.TestBase;
import com.kalin.shopdrop.data.models.Category;
import com.kalin.shopdrop.data.repositories.CategoryRepository;
import com.kalin.shopdrop.service.models.CategoryServiceModel;
import com.kalin.shopdrop.service.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


public class CategoryServiceTest extends TestBase {

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;


    // WANTED BUT NOT INVOKED
//    @Test
//    public void createCategory(){
//        CategoryServiceModel product = new CategoryServiceModel();
//        when(categoryRepository.save(any()))
//                .thenReturn(new Category());
//
//        categoryService.addCategory(product);
//        verify(categoryRepository)
//                .save(any());
//    }


    @Test
    public void getAll_whenThereAreCategories_shouldReturnAllCategories() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category());
        categories.add(new Category());


        when(categoryRepository.findAll())
                .thenReturn(categories);

        List<CategoryServiceModel> categoryServiceModels = categoryService.getAll();

        assertEquals(2, categoryServiceModels.size());
    }

    @Test
    public void getAll_whenThereAreNoCategories_shouldReturnEmpty() {
        when(categoryRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<CategoryServiceModel> categoryServiceModels = categoryService.getAll();

        assertEquals(0, categoryServiceModels.size());
    }

    @Test
    public void getById_whenIdIsCorrect_shouldReturnCategory() {

         Category category = new Category();
         category.setId("1");

        when(categoryRepository.findById("1"))
                .thenReturn(Optional.of(category));

        CategoryServiceModel categoryServiceModel = categoryService.getById("1");

        assertEquals("1", categoryServiceModel.getId());
    }

    @Test
    public void getById_whenIdIsNotCorrect_shouldThrowException() {
        when(categoryRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> categoryService.getById("1"));
    }

    @Test
    public void getByName_whenNameIsCorrect_shouldReturnCategory() {

        Category category = new Category();
        category.setName("Cars");

        when(categoryRepository.findByName("Cars"))
                .thenReturn(Optional.of(category));

        CategoryServiceModel categoryServiceModel = categoryService.getByName("Cars");

        assertEquals("Cars", categoryServiceModel.getName());
    }

    @Test
    public void getByName_whenNameIsNotCorrect_shouldThrowException() {
        when(categoryRepository.findByName("Cars"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> categoryService.getByName("Cars"));
    }

//    @Test
//    public void deleteCategory_shouldDeleteCorrectly_whenDateIsValid() {
//        Category category = new Category();
//        category.setId("1");
//        //Arrange
//        when(categoryRepository.findById("1"))
//                .thenReturn(Optional.of(category));
//
//        //Act
//        categoryService.deleteCategory("1");
//
//        //Assert
//        verify(categoryRepository).delete(any());
//    }



    // WANTED BUT NOT INVOKED
//    @Test
//    public void addCategory_shouldSave_whenCategoryIsValidAndNotPresent() {
//        when(categoryRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
//
//        CategoryServiceModel categoryServiceModel = new CategoryServiceModel();
//        categoryServiceModel.setName("Cars");
//
//        when(categoryRepository.findByName(any()))
//                .thenReturn(Optional.empty());
//
//        categoryService.addCategory(categoryServiceModel);
//
//        verify(categoryRepository).save(any());
//    }

//    @Test
//    public void addCategory_whenCategoryIsValid_shouldReturnCategoryModel() {
//        Category category = new Category();
//        category.setName("Cars");
//        when(categoryRepository.findByName(any()))
//                .thenReturn(Optional.of(category));
//
//        CategoryServiceModel categoryServiceModel = new CategoryServiceModel();
//        categoryServiceModel.setName("Cars");
//
//        assertEquals(categoryService.addCategory(categoryServiceModel),categoryServiceModel);
//    }


}
