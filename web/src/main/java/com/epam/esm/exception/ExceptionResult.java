package com.epam.esm.exception;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResult {
    String errorMessage;
    int errorCode;
}
