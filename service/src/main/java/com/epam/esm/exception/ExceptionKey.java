package com.epam.esm.exception;

import lombok.Data;

/**
 * The class ExceptionKey to localize the error message.
 */
@Data
public final class ExceptionKey {

    public static final String CERTIFICATE_NOT_FOUND = "certificate.notFound";
    public static final String CERTIFICATE_NAME_IS_NOT_VALID = "certificate.name.isNotValid";
    public static final String CERTIFICATE_DURATION_IS_NOT_VALID = "certificate.duration.isNotValid";
    public static final String CERTIFICATE_PRICE_IS_NOT_VALID = "certificate.price.isNotValid";
    public static final String CERTIFICATE_ID_IS_NOT_VALID = "certificate.id.isNotValid";
    public static final String CERTIFICATE_DESCRIPTION_IS_NOT_VALID = "certificate.description.isNotValid";
    public static final String TAG_NAME_IS_NOT_VALID = "tag.name.isNotValid";
    public static final String TAG_ID_IS_NOT_VALID = "tag.id.isNotValid";
    public static final String TAG_NOT_FOUND = "tag.notFound";
    public static final String TAG_EXISTS = "tag.exists";
    public static final String USER_NOT_FOUND = "user.notFound";
    private ExceptionKey() {
    }
}
