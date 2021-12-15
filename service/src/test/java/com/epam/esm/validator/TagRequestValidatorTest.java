package com.epam.esm.validator;

import com.epam.esm.exception.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testAddExceptionName(String name) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> tagRequestValidator.checkName(name));
    }
}
