package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionKey {

    GIFT_CERTIFICATE_NOT_FOUND("gift.certificate.not.found");

    private String key;
}
