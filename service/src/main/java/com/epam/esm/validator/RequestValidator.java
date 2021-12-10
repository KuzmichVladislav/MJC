package com.epam.esm.validator;

import org.modelmapper.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {
    private static final String NAME_REGEX = "^[\\w_]{3,16}$";

    private static final String PRICE_REGEX = "^[0-9]{1,3}(\\.[0-9]{1,2})?$";


    public boolean checkName(String name) {
//        if (!name.trim().matches(NAME_REGEX)){
//            throw new ValidationException()
//        }
        return name.matches(NAME_REGEX);
    }

    public boolean checkDuration(int duration) {
        return duration > 0 && duration < 366;
    }

    public boolean checkPrice(String price) {
        return price.matches(PRICE_REGEX);
    }
}
