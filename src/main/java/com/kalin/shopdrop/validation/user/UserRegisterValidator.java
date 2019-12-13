package com.kalin.shopdrop.validation.user;

import com.kalin.shopdrop.config.annotations.Validator;
import com.kalin.shopdrop.constants.ValidationConstants;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.web.models.RegisterUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Validator
public class UserRegisterValidator implements org.springframework.validation.Validator {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterUserModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegisterUserModel registerUserModel = (RegisterUserModel) o;

        if (registerUserModel.getUsername() == null){
            errors.rejectValue(
                    "username",
                    ValidationConstants.USERNAME_NULL,
                    ValidationConstants.USERNAME_NULL
            );
        }


        if (this.userRepository.findByUsername(registerUserModel.getUsername()).isPresent()) {
            errors.rejectValue(
                    "username",
                    String.format(ValidationConstants.USERNAME_EXISTS, registerUserModel.getUsername()),
                    String.format(ValidationConstants.USERNAME_EXISTS, registerUserModel.getUsername())
            );
        }

        if(!Character.isUpperCase(registerUserModel.getUsername().charAt(0))){
            errors.rejectValue(
                    "username",
                    ValidationConstants.USERNAME_CAPITAL,
                    ValidationConstants.USERNAME_CAPITAL
            );
        }

        if (registerUserModel.getUsername().length() < 3 || registerUserModel.getUsername().length() > 20) {
            errors.rejectValue(
                    "username",
                    ValidationConstants.USERNAME_LENGTH,
                    ValidationConstants.USERNAME_LENGTH
            );
        }

        if (registerUserModel.getPassword().length() < 6){
            errors.rejectValue(
                    "password",
                    ValidationConstants.PASSWORD_LENGTH,
                    ValidationConstants.PASSWORD_LENGTH
            );
        }

        if (!registerUserModel.getPassword().equals(registerUserModel.getConfirmPassword())) {
            errors.rejectValue(
                    "password",
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH,
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH
            );
        }

        if (!isEmailValid(registerUserModel.getEmail())){
            errors.rejectValue(
                    "email",
                    ValidationConstants.EMAIL_NOT_VALID,
                    ValidationConstants.EMAIL_NOT_VALID
            );
        }

        if (this.userRepository.findByEmail(registerUserModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(ValidationConstants.EMAIL_EXISTS, registerUserModel.getEmail()),
                    String.format(ValidationConstants.EMAIL_EXISTS, registerUserModel.getEmail())
            );
        }


    }

    private static boolean isEmailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
