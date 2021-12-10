package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionKey {

    GIFT_CERTIFICATE_NOT_FOUND("gift.certificate.not.found"),
    NAME_IS_NOT_VALID("name.is.not.valid"),
    DURATION_IS_NOT_VALID("duration.is.not.valid"),
    PRICE_IS_NOT_VALID("price.is.not.valid");
    private final String key;
}
