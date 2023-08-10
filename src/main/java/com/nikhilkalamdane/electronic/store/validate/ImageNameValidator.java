package com.nikhilkalamdane.electronic.store.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if(value.isBlank()){
            return false;
        }else{
            return true;
        }
    }
}
