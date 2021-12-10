package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RequestValidator {
    private static final String NAME_REGEX = "^[\\w_]{3,16}$";

    public void checkName(String name) {
        if (!name.trim().matches(NAME_REGEX)) {
            throw new RequestValidationException(ExceptionKey.NAME_IS_NOT_VALID.getKey(), name);
        }
    }

    public void checkDuration(int duration) {
        if (duration < 0 || duration > 366) {
            throw new RequestValidationException(ExceptionKey.DURATION_IS_NOT_VALID.getKey(),
                    String.valueOf(duration));
        }
    }

    public void checkPrice(BigDecimal price) {
        if (price.compareTo(new BigDecimal(0)) < 0 || price.compareTo(new BigDecimal(1_000_000)) > 0) {
            throw new RequestValidationException(ExceptionKey.PRICE_IS_NOT_VALID.getKey(),
                    String.valueOf(price));
        }
    }
}
