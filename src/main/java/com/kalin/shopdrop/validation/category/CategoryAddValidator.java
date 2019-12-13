package com.kalin.shopdrop.validation.category;

import com.kalin.shopdrop.config.annotations.Validator;
import com.kalin.shopdrop.constants.ValidationConstants;
import com.kalin.shopdrop.data.repositories.CategoryRepository;
import com.kalin.shopdrop.web.models.AddCategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

@Validator
public class CategoryAddValidator implements org.springframework.validation.Validator {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryAddValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return AddCategoryModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        AddCategoryModel addCategoryModel = (AddCategoryModel) o;


        if (addCategoryModel.getName() == null) {
            errors.rejectValue(
                    "name",
                    ValidationConstants.CATEGORY_NULL,
                    ValidationConstants.CATEGORY_NULL
            );
        }

        if (this.categoryRepository.findByName(addCategoryModel.getName()).isPresent()) {
            errors.rejectValue(
                    "name",
                    String.format(ValidationConstants.CATEGORY_EXISTS, addCategoryModel.getName()),
                    String.format(ValidationConstants.CATEGORY_EXISTS, addCategoryModel.getName())
            );
        }

        if(!Character.isUpperCase(addCategoryModel.getName().charAt(0))){
            errors.rejectValue(
                    "name",
                    ValidationConstants.CATEGORY_CAPITAL,
                    ValidationConstants.CATEGORY_CAPITAL
            );
        }

        if (addCategoryModel.getName().length() < 2 || addCategoryModel.getName().length() > 20) {
            errors.rejectValue(
                    "name",
                    ValidationConstants.CATEGORY_LENGTH,
                    ValidationConstants.CATEGORY_LENGTH
            );
        }


    }
}
