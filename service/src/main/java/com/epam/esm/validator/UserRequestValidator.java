package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.RequestValidationException;
import org.springframework.stereotype.Component;

/**
 * Utility Class TagRequestValidator for validating the values received
 * from the request for the tag DTO object.
 */
@Component
public class UserRequestValidator {

    private static final String NAME_REGEX = "^[\\w_]{2,20}$";

    /**
     * Check id request.
     *
     * @param id the id request
     */
    public void checkId(Long id) {
        if (id < 1) {
            throw new RequestValidationException(ExceptionKey.TAG_ID_IS_NOT_VALID.getKey(), String.valueOf(id));
        }
    }

    /**
     * Check name request.
     *
     * @param name the name request
     */
    public void checkName(String name) {
        if (!name.trim().matches(NAME_REGEX)) {
            throw new RequestValidationException(ExceptionKey.TAG_NAME_IS_NOT_VALID.getKey(), name);
        }
    }
}
