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
    public static final String CERTIFICATE_ID_DOES_NOT_MATCH = "certificate.id.doesNotMatch";
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
    public static final String ID_MIGHT_NOT_BE_NEGATIVE = "id.mightNotBeNegative";
    public static final String ID_IS_NOT_VALID = "id.isNotValid";
    public static final String SIZE_MIGHT_NOT_BE_NEGATIVE = "size.mightNotBeNegative";
    public static final String TAG_BELONGS_TO_CERTIFICATE = "tag.belongsToCertificate";
    public static final String ORDER_NOT_FOUND = "order.notFound";
    public static final String JWT_TOKEN_IS_EXPIRED_OR_INVALID = "jwt.token.is.expired.or.invalid";
    public static final String USER_USERNAME_LENGTH_IS_NOT_VALID = "user.username.LengthIsNotValid";
    public static final String USER_USERNAME_IS_NOT_VALID = "user.username.isNotValid";
    public static final String USER_FIRST_NAME_LENGTH_IS_NOT_VALID = "user.first.name.LengthIsNotValid";
    public static final String USER_FIRST_NAME_IS_NOT_VALID = "user.first.name.isNotValid";
    public static final String USER_LAST_NAME_LENGTH_IS_NOT_VALID = "user.last.name.LengthIsNotValid";
    public static final String USER_LAST_NAME_IS_NOT_VALID = "user.last.name.isNotValid";
    public static final String USER_EXISTS = "user.exists";
    public static final String USER_NOT_EXISTS = "user.notExists";
    public static final String USERNAME_OR_PASSWORD_INCORRECT = "user.usernameOrPasswordIncorrect";
    public static final String USER_USERNAME_MIGHT_NOT_BE_NULL = "user.username.mightNotBeNull";
    public static final String USER_PASSWORD_MIGHT_NOT_BE_NULL = "user.password.mightNotBeNull";
    public static final String USER_FIRST_NAME_MIGHT_NOT_BE_NULL = "user.firstName.mightNotBeNull";
    public static final String USER_LAST_NAME_MIGHT_NOT_BE_NULL = "user.lastName.mightNotBeNull";
    public static final String USER_PASSWORD_LENGTH_IS_NOT_VALID = "user.password.LengthIsNotValid";
    public static final String USER_PASSWORD_IS_NOT_VALID = "user.password.isNotValid";
    public static final String ACCESS_FORBIDDEN = "access.forbidden";

    private ExceptionKey() {
    }
}
