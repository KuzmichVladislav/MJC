package com.epam.esm.validator;

import com.epam.esm.exception.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

class RequestValidatorTest {
    RequestValidator requestValidator = new RequestValidator();

    @Test
    void testCheckId() {
        requestValidator.checkId(1L);
    }

    @Test
    void testCheckIdException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> requestValidator.checkId(-10L));
    }

    @Test
    void testCheckName() {
        requestValidator.checkName("name");
    }

    @Test
    void testCheckNameException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> requestValidator.checkName("name>"));
    }

    @Test
    void testCheckDuration() {
        requestValidator.checkDuration(100);
    }

    @Test
    void testCheckDurationException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> requestValidator.checkDuration(-1));
    }


    @Test
    void testCheckPrice() {
        requestValidator.checkPrice(new BigDecimal(100));
    }

    @Test
    void testCheckPriceException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> requestValidator.checkPrice(new BigDecimal(-100)));
    }

    @Test
    void testCheckDescription() {
        requestValidator.checkDescription("Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Nunc vehicula sed est et egestas. ");
    }

    @ParameterizedTest
    @ValueSource(strings = {">", "<", "~"})
    void testCheckIdException(String symbols) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> requestValidator.checkDescription(symbols));
    }
}
