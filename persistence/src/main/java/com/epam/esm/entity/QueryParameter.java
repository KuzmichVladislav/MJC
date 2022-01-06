package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Class GiftCertificateQueryParameter contains parameters for generation query
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryParameter {
    private int page;
    private int size;
    private int firstValue;
    private SortingDirection sortingDirection;

    public enum SortingDirection {

        ASC,
        DESC
    }
}
