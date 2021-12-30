package com.epam.esm.dto;

import lombok.*;

import java.util.List;
import java.util.Optional;

/**
 * DTO Class GiftCertificateQueryParameterDto contains parameters for generation query
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueryParameterDto {

    private int page;
    private int size;
    private int firstValue;
    private Optional<String> name;
    private Optional<String> description;
    private Optional<List<String>> tagNames;
    private SortParameter sortParameter;
    private SortingDirection sortingDirection;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum SortParameter {

        NAME("name"),
        CREATE_DATE("createDate"),
        LAST_UPDATE_DATE("lastUpdateDate");
        String parameter;
    }

    public enum SortingDirection {

        ASC,
        DESC
    }
}
