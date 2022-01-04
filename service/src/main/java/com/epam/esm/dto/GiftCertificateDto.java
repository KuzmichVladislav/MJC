package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.exception.ExceptionKey.*;

/**
 * DTO Class GiftCertificate for gift certificate DTO object
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;
    @Size(min = 2, max = 16, message = CERTIFICATE_NAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w_]{2,16}$", message = CERTIFICATE_NAME_IS_NOT_VALID)
    private String name;
    @Size(min = 1, max = 250, message = CERTIFICATE_DESCRIPTION_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[A-Za-z0-9\\s!@.?,&%'-]{0,250}$", message = CERTIFICATE_DESCRIPTION_IS_NOT_VALID)
    private String description;
    @Min(value = 0, message = CERTIFICATE_PRICE_IS_NOT_VALID)
    @Max(value = 1_000_000, message = CERTIFICATE_PRICE_IS_NOT_VALID)
    private BigDecimal price;
    @Min(value = 1, message = CERTIFICATE_DURATION_IS_NOT_VALID)
    @Max(value = 366, message = CERTIFICATE_DURATION_IS_NOT_VALID)
    private Integer duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    @Valid
    private List<TagDto> tags;
}
