package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

import static com.epam.esm.exception.ExceptionKey.PAGE_MIGHT_NOT_BE_NEGATIVE;
import static com.epam.esm.exception.ExceptionKey.SIZE_MIGHT_NOT_BE_NEGATIVE;

/**
 * DTO Class GiftCertificateQueryParameterDto contains parameters for generation query
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryParameterDto {

    @Min(value = 1, message = PAGE_MIGHT_NOT_BE_NEGATIVE)
    private int page;
    @Min(value = 1, message = SIZE_MIGHT_NOT_BE_NEGATIVE)
    private int size;
    private int firstValue;
    private SortingDirection sortingDirection;

    public enum SortingDirection {

        ASC,
        DESC
    }
}
