package com.kalin.shopdrop.services;


import com.kalin.shopdrop.TestBase;
import com.kalin.shopdrop.data.models.Product;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.errors.UserNotFoundException;
import com.kalin.shopdrop.service.models.ProductServiceModel;
import com.kalin.shopdrop.service.models.UserServiceModel;
import com.kalin.shopdrop.service.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest extends TestBase {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void getUserByUsername_whenUserExists_shouldGetUserUsername() {
//        User user = new User();
//        user.setUsername("Pesho");
//
//        when(userRepository.findByUsername(user.getUsername()))
//                .thenReturn(java.util.Optional.of(user));
//
//        UserServiceModel userServiceModel = userService.getByUsername(user.getUsername());
//
//        assertEquals(userServiceModel.getUsername(), user.getUsername());
        User user = new User();
        user.setUsername("KalinDonchev");
        when(userRepository.findByUsername("KalinDonchev"))
                .thenReturn(Optional.of(user));


        UserServiceModel userServiceModel = userService.getByUsername("KalinDonchev");


        assertEquals("KalinDonchev", userServiceModel.getUsername());
    }

    @Test
    public void getUserByUsername_whenUserDoNotExists_shouldThrowException() {
        User user = new User();
        user.setUsername("Choko");
//        Mockito.when(userRepository.findByUsername(user.getUsername()))
//                .thenReturn(java.util.Optional.of(user));


        assertThrows(Exception.class, () ->
                userService.getByUsername(user.getUsername()), user.getUsername());
    }
//


    @Test
    public void getAllUsers_whenExist_shouldReturnAllUsersInDB() {

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll())
                .thenReturn(users);

        List<UserServiceModel> userServiceModels = userService.getAllUsers();

        assertEquals(2, userServiceModels.size());

    }

    @Test
    public void getAllUsers_whenEmpty_shouldReturnEmptyCollection() {
        when(userRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<UserServiceModel> userServiceModels = userService.getAllUsers();

        assertEquals(0, userServiceModels.size());

    }


    @Test
    public void getUserById_whenUserExists_shouldGetUser() {
        User user = new User();
        user.setId("1");
        when(userRepository.findById("1"))
                .thenReturn(Optional.of(user));


        UserServiceModel userServiceModel = userService.getById("1");


        assertEquals("1", userServiceModel.getId());
    }


    @Test
    public void getUserById_whenUserDoNotExists_shouldThrowException() {
        User user = new User();
        user.setId("1");
//        Mockito.when(userRepository.findByUsername(user.getUsername()))
//                .thenReturn(java.util.Optional.of(user));


        assertThrows(Exception.class, () ->
                userService.getById(user.getId()), user.getId());
    }





    //    @Test
//    public void editUserProfile_shouldEditAllFieldsOfTheUser_whenDataIsValid(){
//
//        User user = new User();
//        user.setUsername("KalinDonchev");
//
//
//        when(userRepository.findByUsername("KalinDonchev"))
//                .thenReturn(Optional.of(user));
//
//        user.setPassword("123123");
//
//        UserServiceModel editedServiceModel = new UserServiceModel();
//        editedServiceModel.setPassword("asdasd");
//        editedServiceModel.setEmail("kalindonchev@mail.bg");
//
//
//        UserServiceModel newUserModel = userService.editProfile(editedServiceModel, "123123");
//
//
//        assertEquals("kalindonchev@mail.bg", newUserModel.getEmail());
//        assertEquals("asdasd",newUserModel.getPassword());
//    }



//    @Test
//    public void addProductForUser_whenUserExists_shouldAddProductForUser() throws Exception {
//        User user = new User();
//        user.setUsername("Pesho");
//        String productName = "Car";
//        Mockito.when(userRepository.findByUsername(user.getUsername()))
//                .thenReturn(java.util.Optional.of(user));
//
//        ProductServiceModel productServiceModel = new ProductServiceModel();
//        productServiceModel.setUser(user.getUsername());
//        productServiceModel.setName(productName);
//
//
//        userService.addProductToUser(user.getUsername(), productServiceModel);
//
//        assertEquals(1, user.getProducts().size());
//
//        Product product = new Product();
//
//       when(userRepository.save(any()))
//               .thenReturn(new Quote());
//
//       service.createQuote(quote);
//       verify(mockQuoteRepository)
//               .save(any());
//
//    }
//
//
//    @Test
//    public void addProductForUser_createWithInvalidValues_ThrowError() {
//        userService.addProductToUser(null,null);
//        Mockito.when(userService.addProductToUser(null,null)
//                .thenReturn(null));
//        assertThrows(NullPointerException.class,  verify(userRepository)
//                .save(any()));
//    }
//
//    @Test()
//    public void setAdmin_whenUserExists_shouldSetAdmin() {
//        User user = new User();
//        user.setId("1");
//        Mockito.when(userRepository.findById(user.getId()))
//                .thenReturn(java.util.Optional.of(user));
//
//        userService.setAdmin(user.getId());
//
//
//        assertEquals("1","@");
//    }




}
