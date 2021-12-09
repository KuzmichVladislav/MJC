package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorCode {
    resourceNotFound(40401);

    int errorCode;
}
