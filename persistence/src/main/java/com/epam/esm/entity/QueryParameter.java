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
