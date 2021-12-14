package com.epam.esm.validator;

import com.epam.esm.exception.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

class GiftCertificateRequestValidatorTest {
    GiftCertificateRequestValidator giftCertificateRequestValidator = new GiftCertificateRequestValidator();

    @Test
    void testCheckId() {
        giftCertificateRequestValidator.checkId(1L);
    }

    @Test
    void testCheckIdException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkId(-10L));
    }

    @Test
    void testCheckName() {
        giftCertificateRequestValidator.checkName("name");
    }

    @Test
    void testCheckNameException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkName("name>"));
    }

    @Test
    void testCheckDuration() {
        giftCertificateRequestValidator.checkDuration(100);
    }

    @Test
    void testCheckDurationException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkDuration(-1));
    }


    @Test
    void testCheckPrice() {
        giftCertificateRequestValidator.checkPrice(new BigDecimal(100));
    }

    @Test
    void testCheckPriceException() {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkPrice(new BigDecimal(-100)));
    }

    @Test
    void testCheckDescription() {
        giftCertificateRequestValidator.checkDescription("Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Nunc vehicula sed est et egestas. ");
    }

    @ParameterizedTest
    @ValueSource(strings = {">", "<", "~"})
    void testCheckIdException(String symbols) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkDescription(symbols));
    }
}
