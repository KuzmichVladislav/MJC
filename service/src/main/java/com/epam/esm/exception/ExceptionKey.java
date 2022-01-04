package com.epam.esm.exception;

import lombok.Data;

/**
 * The class ExceptionKey to localize the error message.
 */
@Data
public final class ExceptionKey {

    public static final String CERTIFICATE_NOT_FOUND = "certificate.notFound";
    public static final String CERTIFICATE_NAME_IS_NOT_VALID = "certificate.name.isNotValid";
    public static final String CERTIFICATE_NAME_LENGTH_IS_NOT_VALID = "certificate.name.lengthIsNotValid";
    public static final String CERTIFICATE_DURATION_IS_NOT_VALID = "certificate.duration.isNotValid";
    public static final String CERTIFICATE_PRICE_IS_NOT_VALID = "certificate.price.isNotValid";
    public static final String CERTIFICATE_ID_IS_NOT_VALID = "certificate.id.isNotValid";
    public static final String CERTIFICATE_DESCRIPTION_IS_NOT_VALID = "certificate.description.isNotValid";
    public static final String CERTIFICATE_DESCRIPTION_LENGTH_IS_NOT_VALID = "certificate.description.lengthIsNotValid";
    public static final String TAG_NAME_IS_NOT_VALID = "tag.name.isNotValid";
    public static final String TAG_NAME_LENGTH_IS_NOT_VALID = "tag.name.LengthIsNotValid";
    public static final String TAG_ID_IS_NOT_VALID = "tag.id.isNotValid";
    public static final String TAG_NOT_FOUND = "tag.notFound";
    public static final String TAG_EXISTS = "tag.exists";
    public static final String USER_NOT_FOUND = "user.notFound";
    public static final String CERTIFICATE_NAME_MIGHT_NOT_BE_NULL = "certificate.name.mightNotBeNull";
    public static final String TAG_NAME_MIGHT_NOT_BE_NULL = "tag.name.mightNotBeNull";
    public static final String CERTIFICATE_DESCRIPTION_MIGHT_NOT_BE_NULL = "certificate.description.mightNotBeNull";
    public static final String CERTIFICATE_PRICE_MIGHT_NOT_BE_NULL = "certificate.price.mightNotBeNull";
    public static final String CERTIFICATE_DURATION_MIGHT_NOT_BE_NULL = "certificate.duration.mightNotBeNull";
    public static final String USER_ID_MIGHT_NOT_BE_NULL = "user.id.mightNotBeNull";
    public static final String USER_ID_IS_NOT_VALID = "user.id.isNotValid";
    public static final String SPECIFIED_PAGE_DOES_NOT_EXIST = "specified.page.does.not.exist";
    public static final String PAGE_MIGHT_NOT_BE_NEGATIVE = "page.mightNotBeNegative";
    public static final String SIZE_MIGHT_NOT_BE_NEGATIVE = "size.mightNotBeNegative";
    public static final String TAG_BELONGS_TO_CERTIFICATE = "tag.belongsToCertificate";

    private ExceptionKey() {
    }
}
