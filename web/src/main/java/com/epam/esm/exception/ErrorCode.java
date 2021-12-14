package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.SQLException;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorCode {
    RESOURCE_NOT_FOUND(40401),
    HANDLER_NOT_FOUND(40402),
    NOT_VALID_PARAM(40001),
    INTERNAL_ERROR(50001),
    METHOD_NOT_ALLOWED(40501),
    UNSUPPORTED_MEDIA_TYPE(41501);
    int errorCode;
}
