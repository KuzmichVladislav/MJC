package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * Entity Class GiftCertificateQueryParameter for gift certificate query parameter entity
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GiftCertificateQueryParameter extends QueryParameter {

    private Optional<String> name;
    private Optional<String> description;
    private Optional<List<String>> tagNames;
    private SortParameter sortParameter;

    @Builder(builderMethodName = "giftCertificateQueryParameterBuilder")
    public GiftCertificateQueryParameter(int page,
                                         int size,
                                         int firstValue,
                                         SortingDirection sortingDirection,
                                         Optional<String> name,
                                         Optional<String> description,
                                         Optional<List<String>> tagNames,
                                         SortParameter sortParameter) {
        super(page, size, firstValue, sortingDirection);
        this.name = name;
        this.description = description;
        this.tagNames = tagNames;
        this.sortParameter = sortParameter;
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
