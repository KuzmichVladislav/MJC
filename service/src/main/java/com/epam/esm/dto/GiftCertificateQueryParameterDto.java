package com.epam.esm.dto;

import lombok.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.ExceptionKey.PAGE_MIGHT_NOT_BE_NEGATIVE;
import static com.epam.esm.exception.ExceptionKey.SIZE_MIGHT_NOT_BE_NEGATIVE;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class GiftCertificateQueryParameterDto extends QueryParameterDto {
    private Optional<String> name;
    private Optional<String> description;
    private Optional<List<String>> tagNames;
    private SortParameter sortParameter;


    @Builder(builderMethodName = "giftCertificateQueryParameterDtoBuilder")
    public GiftCertificateQueryParameterDto(@Min(value = 1, message = PAGE_MIGHT_NOT_BE_NEGATIVE) int page,
                                            @Min(value = 1, message = SIZE_MIGHT_NOT_BE_NEGATIVE) int size,
                                            int firstValue, SortingDirection sortingDirection, Optional<String> name,
                                            Optional<String> description, Optional<List<String>> tagNames,
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
