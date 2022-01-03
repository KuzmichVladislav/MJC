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

/**
 * DTO Class GiftCertificate for tag DTO object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto extends RepresentationModel<TagDto> {

    private long id;
    @NotNull(message = TAG_NAME_IS_NOT_VALID) // TODO: 12/29/2021
    @Size(min = 2, max = 16, message = TAG_NAME_IS_NOT_VALID) // TODO: 12/29/2021
    @Pattern(regexp = "^[\\w_]", message = TAG_NAME_IS_NOT_VALID) // TODO: 12/29/2021
    private String name;
}
