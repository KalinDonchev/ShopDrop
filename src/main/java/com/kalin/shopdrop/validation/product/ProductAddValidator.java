package com.kalin.shopdrop.validation.product;

import com.kalin.shopdrop.config.annotations.Validator;
import com.kalin.shopdrop.constants.ValidationConstants;
import com.kalin.shopdrop.data.repositories.ProductRepository;
import com.kalin.shopdrop.web.models.AddProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;



import static java.math.BigDecimal.ONE;

@Validator
public class ProductAddValidator implements org.springframework.validation.Validator {

    private final ProductRepository productRepository;

    @Autowired
    public ProductAddValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AddProductModel.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddProductModel addProductModel = (AddProductModel) o;


        if (addProductModel.getName() == null){
            errors.rejectValue(
                    "name",
                    ValidationConstants.PRODUCT_NULL,
                    ValidationConstants.PRODUCT_NULL
            );
        }

        if (this.productRepository.findByName(addProductModel.getName()).isPresent()) {
            errors.rejectValue(
                    "name",
                    String.format(ValidationConstants.PRODUCT_EXISTS, addProductModel.getName()),
                    String.format(ValidationConstants.PRODUCT_EXISTS, addProductModel.getName())
            );
        }

        if (addProductModel.getName().length() < 3 || addProductModel.getName().length() > 30) {
            errors.rejectValue(
                    "name",
                    ValidationConstants.PRODUCT_LENGTH,
                    ValidationConstants.PRODUCT_LENGTH
            );
        }

        if (!(addProductModel.getPrice().compareTo(ONE) > 0)){
            errors.rejectValue(
                    "price",
                    ValidationConstants.PRICE_POSITIVE,
                    ValidationConstants.PRICE_POSITIVE
            );
        }



    }
}
