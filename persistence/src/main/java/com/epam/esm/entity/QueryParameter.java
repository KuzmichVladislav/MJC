package com.epam.esm.entity;

import lombok.*;

import java.util.List;
import java.util.Optional;

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
