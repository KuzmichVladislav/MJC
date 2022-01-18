package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.epam.esm.exception.ExceptionKey.TAG_NAME_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.TAG_NAME_LENGTH_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.TAG_NAME_MIGHT_NOT_BE_NULL;

/**
 * DTO Class TagDto for tag DTO object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto extends RepresentationModel<TagDto> {

    private long id;
    @NotNull(message = TAG_NAME_MIGHT_NOT_BE_NULL)
    @Size(min = 2, max = 16, message = TAG_NAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w_]{2,16}$", message = TAG_NAME_IS_NOT_VALID)
    private String name;
}
