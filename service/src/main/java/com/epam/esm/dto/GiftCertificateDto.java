package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO Class GiftCertificate for gift certificate DTO object
 */
@Data
@Builder
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;

    public GiftCertificateDto(@JsonProperty("id") long id,
                              @JsonProperty("name") String name,
                              @JsonProperty("description")  String description,
                              @JsonProperty("price") BigDecimal price,
                              @JsonProperty("duration")  Integer duration,
                              @JsonProperty("createDate")  LocalDateTime createDate,
                              @JsonProperty("lastUpdateDate")  LocalDateTime lastUpdateDate,
                              @JsonProperty("tags")  List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificateDto() {
    }
}
