package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Class ExceptionResult to display error result
 *
 * @author Vladislav Kuzmich
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResult {
    private String errorMessage;
    private int errorCode;
}
