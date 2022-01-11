package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO Class QueryParameterDto contains parameters for generation query
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryParameterDto {

    private int page;
    private int size;
    private int firstValue;
    private SortingDirection sortingDirection;

    public enum SortingDirection {

        ASC,
        DESC
    }
}
