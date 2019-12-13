package com.kalin.shopdrop.validation.product;

import com.kalin.shopdrop.config.annotations.Validator;
import com.kalin.shopdrop.constants.ValidationConstants;
import com.kalin.shopdrop.data.repositories.ProductRepository;
import com.kalin.shopdrop.web.models.AddProductModel;
import com.kalin.shopdrop.web.models.EditProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import static java.math.BigDecimal.ONE;

@Validator
public class ProductEditValidator implements org.springframework.validation.Validator {

    private final ProductRepository productRepository;

    @Autowired
    public ProductEditValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EditProductModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EditProductModel editProductModel = (EditProductModel) o;


        if (editProductModel.getName() == null) {
            errors.rejectValue(
                    "name",
                    ValidationConstants.PRODUCT_NULL,
                    ValidationConstants.PRODUCT_NULL
            );
        }

        if (this.productRepository.findByName(editProductModel.getName()).isPresent()) {
            errors.rejectValue(
                    "name",
                    String.format(ValidationConstants.PRODUCT_EXISTS, editProductModel.getName()),
                    String.format(ValidationConstants.PRODUCT_EXISTS, editProductModel.getName())
            );
        }

        if (editProductModel.getName().length() < 3 || editProductModel.getName().length() > 30) {
            errors.rejectValue(
                    "name",
                    ValidationConstants.PRODUCT_LENGTH,
                    ValidationConstants.PRODUCT_LENGTH
            );
        }

        if (!(editProductModel.getPrice().compareTo(ONE) > 0)) {
            errors.rejectValue(
                    "price",
                    ValidationConstants.PRICE_POSITIVE,
                    ValidationConstants.PRICE_POSITIVE
            );
        }


    }
}