package com.epam.esm.validator;

import com.epam.esm.exception.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TagRequestValidatorTest {
    TagRequestValidator tagRequestValidator = new TagRequestValidator();

    @Test
    void testCheckId() {
        tagRequestValidator.checkId(1L);
    }

    @Test
    void testCheckIdException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> tagRequestValidator.checkId(-10L));
    }

    @Test
    void testCheckName() {
        tagRequestValidator.checkName("name");
    }

    @Test
    void testCheckNameException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> tagRequestValidator.checkName("name>"));
    }
}
