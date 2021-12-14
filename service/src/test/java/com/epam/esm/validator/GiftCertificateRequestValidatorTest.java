package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
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

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testCheckNameException(String name) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkName(name));
    }

    @Test
    void testCheckDuration() {
        giftCertificateRequestValidator.checkDuration(100);
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 0, 367, 500})
    void testCheckDurationException(int duration) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkDuration(duration));
    }

    @Test
    void testCheckPrice() {
        giftCertificateRequestValidator.checkPrice(new BigDecimal(100));
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 1_000_001, 2_000_000})
    void testCheckPriceException(int price) {
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkPrice(new BigDecimal(price)));
    }

    @Test
    void testCheckDescription() {
        giftCertificateRequestValidator.checkDescription("Lorem ipsum dolor sit amet, " +
                "consectetur adipiscing elit. Nunc vehicula sed est et egestas. ");
    }

    @ParameterizedTest
    @ValueSource(strings = {">description", "<description", "~description", "Description more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters " +
            "more then 250 characters more then 250 characters more then 250 characters more then 250 characters"})
    void testCheckDescriptionException(String description) {
        Assertions.assertThrows(RequestValidationException.class,
                () ->  giftCertificateRequestValidator.checkDescription(description));
    }
}
