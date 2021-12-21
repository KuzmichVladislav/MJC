package com.epam.esm.validator;

import com.epam.esm.exception.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

class GiftCertificateRequestValidatorTest {

    private GiftCertificateRequestValidator giftCertificateRequestValidator;

    @BeforeEach
    void setUp() {
        giftCertificateRequestValidator = new GiftCertificateRequestValidator();
    }

    @Test
    void testCheckId_ValidId_SuccessfullyValidated() {
        // Given
        // When
        // Then
        giftCertificateRequestValidator.checkId(1L);
    }

    @Test
    void testCheckId_IdLessThan1_ExceptionThrown() {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkId(-10L));
    }

    @Test
    void testCheckName_ValidName_SuccessfullyValidated() {
        // Given
        // When
        // Then
        giftCertificateRequestValidator.checkName("name");
    }

    @ParameterizedTest
    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
    void testCheckName_InvalidName_ExceptionThrown(String name) {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkName(name));
    }

    @Test
    void testCheckDuration_ValidDuration_SuccessfullyValidated() {
        // Given
        // When
        // Then
        giftCertificateRequestValidator.checkDuration(100);
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 0, 367, 500})
    void testCheckDuration_InvalidDuration_ExceptionThrown(int duration) {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkDuration(duration));
    }

    @Test
    void testCheckPrice_ValidPrice_SuccessfullyValidated() {
        // Given
        // When
        // Then
        giftCertificateRequestValidator.checkPrice(new BigDecimal(100));
    }

    @ParameterizedTest
    @ValueSource(ints = {-500, -1, 1_000_001, 2_000_000})
    void testCheckPrice_InvalidPrice_ExceptionThrown(int price) {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkPrice(new BigDecimal(price)));
    }

    @Test
    void testCheckDescription_ValidDescription_SuccessfullyValidated() {
        // Given
        // When
        // Then
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
    void testCheckDescription_InvalidDescription_ExceptionThrown(String description) {
        // Given
        // When
        // Then
        Assertions.assertThrows(RequestValidationException.class,
                () -> giftCertificateRequestValidator.checkDescription(description));
    }
}
