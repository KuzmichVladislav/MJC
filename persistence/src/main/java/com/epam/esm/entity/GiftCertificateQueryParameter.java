package com.epam.esm.entity;

import lombok.*;

import java.util.List;
import java.util.Optional;

/**
 * Entity Class GiftCertificateQueryParameter for gift certificate query parameter entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateQueryParameter {

    private Optional<String> name;
    private Optional<String> description;
    private Optional<List<String>> tagNames;
    private SortParameter sortParameter;
    private int page;
    private int size;
    private int firstValue;
    private SortingDirection sortingDirection;

    public enum SortingDirection {

        ASC,
        DESC
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum SortParameter {

        NAME("name"),
        CREATE_DATE("createDate"),
        LAST_UPDATE_DATE("lastUpdateDate");

        String parameter;
    }
}
