package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorCode {
    RESOURCE_NOT_FOUND(40401),
    NOT_VALID_PARAM(41201);

    int errorCode;
}
