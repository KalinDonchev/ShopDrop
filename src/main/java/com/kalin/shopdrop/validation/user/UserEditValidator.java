package com.kalin.shopdrop.validation.user;

import com.kalin.shopdrop.config.annotations.Validator;
import com.kalin.shopdrop.constants.ValidationConstants;
import com.kalin.shopdrop.data.models.User;
import com.kalin.shopdrop.data.repositories.UserRepository;
import com.kalin.shopdrop.errors.UserNotFoundException;
import com.kalin.shopdrop.web.models.UserEditModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Validator
public class UserEditValidator implements org.springframework.validation.Validator {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserEditValidator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEditModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserEditModel userEditModel = (UserEditModel) o;

        User user = this.userRepository.findByUsername(userEditModel.getUsername()).orElseThrow(() -> new UserNotFoundException("No such user"));

        if (!this.passwordEncoder.matches(userEditModel.getOldPassword(), user.getPassword())) {
            errors.rejectValue(
                    "oldPassword",
                    ValidationConstants.WRONG_PASSWORD,
                    ValidationConstants.WRONG_PASSWORD
            );
        }

        if (userEditModel.getPassword() != null && !userEditModel.getPassword().equals(userEditModel.getConfirmPassword())) {
            errors.rejectValue(
                    "password",
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH,
                    ValidationConstants.PASSWORDS_DO_NOT_MATCH
            );
        }

        if (userEditModel.getPassword().length() < 6){
            errors.rejectValue(
                    "password",
                    ValidationConstants.PASSWORD_LENGTH,
                    ValidationConstants.PASSWORD_LENGTH
            );
        }

        if (!isEmailValid(userEditModel.getEmail())){
            errors.rejectValue(
                    "email",
                    ValidationConstants.EMAIL_NOT_VALID,
                    ValidationConstants.EMAIL_NOT_VALID
            );
        }

        if (!user.getEmail().equals(userEditModel.getEmail()) && this.userRepository.findByEmail(userEditModel.getEmail()).isPresent()) {
            errors.rejectValue(
                    "email",
                    String.format(ValidationConstants.EMAIL_EXISTS, userEditModel.getEmail()),
                    String.format(ValidationConstants.EMAIL_EXISTS, userEditModel.getEmail())
            );
        }
    }


    private static boolean isEmailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
