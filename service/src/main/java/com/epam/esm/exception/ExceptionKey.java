package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum ExceptionKey to localize the error message.
 */
@AllArgsConstructor
@Getter
public enum ExceptionKey {

    CERTIFICATE_NOT_FOUND(Values.CERTIFICATE_NOT_FOUND),
    CERTIFICATE_NAME_IS_NOT_VALID("certificate.name.isNotValid"),
    CERTIFICATE_DURATION_IS_NOT_VALID("certificate.duration.isNotValid"),
    CERTIFICATE_PRICE_IS_NOT_VALID("certificate.price.isNotValid"),
    CERTIFICATE_ID_IS_NOT_VALID("certificate.id.isNotValid"),
    CERTIFICATE_DESCRIPTION_IS_NOT_VALID("certificate.description.isNotValid"),
    TAG_NAME_IS_NOT_VALID("tag.name.isNotValid"),
    TAG_ID_IS_NOT_VALID("tag.id.isNotValid"),
    TAG_NOT_FOUND("tag.notFound"),
    TAG_EXISTS("tag.exists"),
    USER_NOT_FOUND("user.notFound");

    private final String key;

    public class Values {
        public static final String CERTIFICATE_NOT_FOUND = "certificate.notFound";
    }
}
