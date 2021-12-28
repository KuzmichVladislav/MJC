package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO Class GiftCertificate for gift certificate DTO object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;
    @NotEmpty(message = "certificate.name.isNotValid")
    @Size(min = 2, max = 16, message = "certificate.name.isNotValid")
    @Pattern(regexp = "^[\\w_]", message = "certificate.name.isNotValid")
    private String name;
    @NotEmpty
    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z0-9\\s!@.?,&%'-]")
    private String description;
    @NotNull
    @Min(value = 0)
    @Max(value = 1_000_000)
    private BigDecimal price;
    @NotNull(message = "hi")
    @Min(value = 1)
    @Max(value = 366)
    private Integer duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;
}
