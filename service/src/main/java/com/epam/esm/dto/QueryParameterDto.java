package com.epam.esm.dto;

import lombok.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.ExceptionKey.PAGE_MIGHT_NOT_BE_NEGATIVE;

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
