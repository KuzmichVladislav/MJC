//package com.epam.esm.validator;
//
//import com.epam.esm.exception.RequestValidationException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//class TagRequestValidatorTest {
//
//    private TagRequestValidator tagRequestValidator;
//
//    @BeforeEach
//    void setUp() {
//        tagRequestValidator = new TagRequestValidator();
//    }
//
//    @Test
//    void testCheckId_ValidId_SuccessfullyValidated() {
//        // Given
//        // When
//        // Then
//        tagRequestValidator.checkId(1L);
//    }
//
//    @Test
//    void testCheckId_IdLessThan1_ExceptionThrown() {
//        // Given
//        // When
//        // Then
//        Assertions.assertThrows(RequestValidationException.class,
//                () -> tagRequestValidator.checkId(-10L));
//    }
//
//    @Test
//    void testCheckName_ValidName_SuccessfullyValidated() {
//        // Given
//        // When
//        // Then
//        tagRequestValidator.checkName("name");
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {">name", "<name", "~name", "ab", "Name with space", "NameMoreThen16Char"})
//    void testCheckName_InvalidName_ExceptionThrown(String name) {
//        // Given
//        // When
//        // Then
//        Assertions.assertThrows(RequestValidationException.class,
//                () -> tagRequestValidator.checkName(name));
//    }
//}
