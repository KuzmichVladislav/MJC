package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Class ExceptionResult to display error result
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResult {

    private List<String> errorMessage;
    private int errorCode;
}
